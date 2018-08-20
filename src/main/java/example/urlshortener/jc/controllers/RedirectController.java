package example.urlshortener.jc.controllers;

import example.urlshortener.jc.ShortUrlRepository;
import example.urlshortener.jc.domain.ShortUrl;
import example.urlshortener.jc.generators.IUrlGenerator;
import example.urlshortener.jc.generators.UrlShortener;
import example.urlshortener.jc.jsonobj.UrlShortenRequest;
import example.urlshortener.jc.jsonobj.UrlShortenedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RedirectController {

    @Autowired
    private ShortUrlRepository repository;


    @GetMapping("/{shortUrl:^[^\\.]+$}")
    @ResponseBody
    public ResponseEntity<String> getRedirect(@PathVariable final String shortUrl) {

        ShortUrl rec = repository.findByShortVersion(shortUrl);
        if (rec == null)
            return ResponseEntity
                    .badRequest()
                    .body(String.format("Oops!  That short URL - '%s', was not recogized.", shortUrl));

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("X-XSS-Protection", "1; mode=block")
                .header(HttpHeaders.LOCATION, rec.getUrl())
                .build();

    }

}

