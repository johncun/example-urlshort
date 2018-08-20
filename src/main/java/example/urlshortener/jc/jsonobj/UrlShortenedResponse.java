package example.urlshortener.jc.jsonobj;

import example.urlshortener.jc.common.Constants;

public class UrlShortenedResponse {

    private int version;
    private String id;
    private String url;
    private String shortUrl;

    public UrlShortenedResponse() {
    }

    public UrlShortenedResponse(String id, String url, String shortUrl) {
        this.version = Constants.VERSION;
        this.id = id;
        this.url = url;
        this.shortUrl = shortUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public int getVersion() {
        return version;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
