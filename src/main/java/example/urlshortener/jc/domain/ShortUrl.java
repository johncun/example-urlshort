package example.urlshortener.jc.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class ShortUrl {

    @Id
    private String id;

    private String url;

    @Indexed(unique = true)
    private String shortVersion;

    public ShortUrl() {
    }

    public String getId() {
        return id;
    }

    public ShortUrl(String url, String shortVersion) {
        this.url = url;
        this.shortVersion = shortVersion;


    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortVersion() {
        return shortVersion;
    }

    public void setShortVersion(String shortVersion) {
        this.shortVersion = shortVersion;
    }

    @Override
    public String toString() {
        return "ShortUrl{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", shortVersion='" + shortVersion + '\'' +
                '}';
    }
}
