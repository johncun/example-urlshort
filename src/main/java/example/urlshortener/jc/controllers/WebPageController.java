package example.urlshortener.jc.controllers;

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
public class WebPageController {

    @RequestMapping("/")
    public String homePage() {
        return "index.html";
    }

}

