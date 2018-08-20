package example.urlshortener.jc.controllers;

import example.urlshortener.jc.ShortUrlRepository;
import example.urlshortener.jc.domain.ShortUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebPageController {
    @Autowired
    private ShortUrlRepository repository;

    @RequestMapping("/")
    public String homePage() {
        return "index.html";
    }

    @RequestMapping("/error")
    public String errorPage() {
        return "error.html";
    }

}

