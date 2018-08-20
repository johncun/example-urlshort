package example.urlshortener.jc.controllers;

import example.urlshortener.jc.ShortUrlRepository;
import example.urlshortener.jc.common.Constants;
import example.urlshortener.jc.domain.ShortUrl;
import example.urlshortener.jc.exceptions.ApiException;
import example.urlshortener.jc.jsonobj.UrlShortenRequest;
import example.urlshortener.jc.jsonobj.UrlShortenedResponse;
import example.urlshortener.jc.generators.IUrlGenerator;
import example.urlshortener.jc.generators.UrlShortener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@Controller
public class ApiController {

    private static final int SUPPORTED_VERSION_LEVEL = Constants.VERSION;

    @Autowired
    private ShortUrlRepository repository;

    @Value("${custom-settings.max-retries-for-random-generation}")
    @Min(1)
    private int MAX_TRIES;

    @PostMapping("/shorten")
    @ResponseBody
    public UrlShortenedResponse requestShortenedUrl(@RequestBody UrlShortenRequest urlRequest) throws ApiException {

        // Only support version 1 of API currently and only one method for generation
        if (urlRequest.getVersion() <= 0 || urlRequest.getVersion() > SUPPORTED_VERSION_LEVEL)
            throw new ApiException(String.format("Version %d of the API is not supported", urlRequest.getVersion()));

        // Different methods for generating could be updated in the back end at some point in the future but
        UrlShortener urlS = new UrlShortener(urlRequest.getUrl()).setMode(IUrlGenerator.GenerationMethod.BASIC_RANDOM);

        String shortUrl = "";

        // If there is difficulty in finding a new random token
        // then ensure we don't continue forever, try at most MAX_TRIES
        int sanityCount = MAX_TRIES;

        while (--sanityCount > 0) {
            shortUrl = urlS.generate(urlRequest.getDesiredLength());

            // If this does not already exist then fall out
            if (repository.findByShortVersion(shortUrl) == null)
                break;
        }

        if (sanityCount == 0)
            throw new ApiException("Internal error, not able to get a unique URL");

        ShortUrl dataObj = new ShortUrl(urlRequest.getUrl(), shortUrl);
        repository.save(dataObj);

        return new UrlShortenedResponse(dataObj.getId(), dataObj.getUrl(), dataObj.getShortVersion());
    }

    @GetMapping("/stats.latest")
    @ResponseBody
    public List<ShortUrl> latestStats() {

        // Simple return of last 10 urls and access records. This would need a lot more
        // work to scale, including creating a more granular API

        List<ShortUrl> latest = repository.findTop10ByOrderByCreatedOnDesc();

        return latest;
    }


}

