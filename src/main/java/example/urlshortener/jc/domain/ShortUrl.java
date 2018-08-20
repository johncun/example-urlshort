package example.urlshortener.jc.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.web.SortDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * The main entity representing the mapping between a unique token and a URL.
 *
 *
 */
public class ShortUrl {

    @Id
    private String id;

    private String url;

    private int accessCount;

    @Indexed
    private long createdOn;

    @Indexed(unique = true)
    private String shortVersion;

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    private List<AccessInstance> accessHistory = new ArrayList<>();

    public ShortUrl() {
    }

    public int getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(int accessCount) {
        this.accessCount = accessCount;
    }

    public String getId() {
        return id;
    }

    public ShortUrl(String url, String shortVersion) {
        this.url = url;
        this.shortVersion = shortVersion;
        this.createdOn = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<AccessInstance> getAccessHistory() {
        return accessHistory;
    }

    public void setAccessHistory(List<AccessInstance> accessHistory) {
        this.accessHistory = accessHistory;
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
                ", accessCount=" + accessCount +
                ", createdOn=" + createdOn +
                ", shortVersion='" + shortVersion + '\'' +
                ", accessHistory=" + accessHistory +
                '}';
    }

    public void updateStats(String ip, long epochSecondsUtc) {
        accessCount++;
        accessHistory.add(new AccessInstance(ip, epochSecondsUtc));
    }
}
