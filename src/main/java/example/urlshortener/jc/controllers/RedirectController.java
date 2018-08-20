package example.urlshortener.jc.controllers;

import example.urlshortener.jc.ShortUrlRepository;
import example.urlshortener.jc.domain.ShortUrl;
import example.urlshortener.jc.exceptions.ApiException;
import example.urlshortener.jc.exceptions.WebException;
import example.urlshortener.jc.generators.IUrlGenerator;
import example.urlshortener.jc.generators.UrlShortener;
import example.urlshortener.jc.jsonobj.UrlShortenRequest;
import example.urlshortener.jc.jsonobj.UrlShortenedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller to handle the mapping of a arbitrary text request to redirect to the expanded URL
 * This should return as fast as possible. In the initial implementation the statistics are updated directly,
 * if this needs to scale then this can be offloaded to a queue, so that the only computation time will be
 * in reitrieving the URL.
 */
@Controller
public class RedirectController {

    @Autowired
    private ShortUrlRepository repository;

    @GetMapping("/{shortUrl:^[^\\.]+$}")
    @ResponseBody
    public ResponseEntity<String> getRedirect(@PathVariable final String shortUrl) throws WebException {

        ShortUrl rec = repository.findByShortVersion(shortUrl);
        if (rec == null) throw new WebException(
                String.format("Short URL fragment '%s' was not recognized.", shortUrl));

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        rec.updateStats(
                request.getRemoteAddr(),
                LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond());
        repository.save(rec);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("X-XSS-Protection", "1; mode=block")
                .header(HttpHeaders.LOCATION, rec.getUrl())
                .build();
    }

    /**
     * Utility function to build a model to contain error information to be passed to the error view template
     * @param description
     * @return
     */
    private ModelAndView errorForward(String description) {

        Map<String, String> hm = new HashMap<>();
        hm.put("errorDescription", description);

        ModelAndView mav = new ModelAndView("error", hm);
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }

    /**
     * Handles any WebException from this controller
     * @param ex
     * @return
     */
    @ExceptionHandler(WebException.class)
    protected ModelAndView handleWebError(WebException ex) {

        return errorForward(ex.getMessage());
    }

}

