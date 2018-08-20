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

@Controller
public class RedirectController {

    @Autowired
    private ShortUrlRepository repository;

    private ModelAndView errorForward(String description) {

        Map<String, String> hm = new HashMap<>();
        hm.put("errorDescription", description);

        ModelAndView mav = new ModelAndView("error", hm);
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }

    @ExceptionHandler(WebException.class)
    protected ModelAndView handleWebError(WebException ex) {

        return errorForward(ex.getMessage());
    }

    @GetMapping("/{shortUrl:^[^\\.]+$}")
    @ResponseBody
    public ResponseEntity<String> getRedirect(@PathVariable final String shortUrl) throws WebException {

        ShortUrl rec = repository.findByShortVersion(shortUrl);
        if (rec == null) throw new WebException(
                String.format("Short URL fragment '%s'  was not recognized.", shortUrl));

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

//    @GetMapping("/{shortUrl:^[^\\.]+$}")
//    public ModelAndView getRedirect(@PathVariable final String shortUrl) throws WebException {
//
//        ShortUrl rec = repository.findByShortVersion(shortUrl);
//        if (rec == null) throw new WebException(
//                String.format("Oops!  That short URL - '%s', was not recogized.", shortUrl));
//
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//                .getRequest();
//
//        rec.updateStats(
//                request.getRemoteAddr(),
//                LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond());
//        repository.save(rec);
//
////        Map<String, String> hm = new HashMap<>();
////        hm.put("errorDescription", "Hi");
//        return new ModelAndView("redirect:" + rec.getUrl());
//    }

}

