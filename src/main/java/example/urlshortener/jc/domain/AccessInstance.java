package example.urlshortener.jc.domain;

/**
 * Represents a redirect request instance and records the source IP and time (UTC)
 */
public class AccessInstance {

    private long utcUnixTime;
    private String requestIp;

    public AccessInstance() {
    }

    public AccessInstance(String requestIp, long utcUnixTime) {
        this.requestIp = requestIp;
        this.utcUnixTime = utcUnixTime;
    }

    @Override
    public String toString() {
        return "AccessInstance{" +
                "utcUnixTime=" + utcUnixTime +
                ", requestIp='" + requestIp + '\'' +
                '}';
    }

    public long getUtcUnixTime() {
        return utcUnixTime;
    }

    public void setUtcUnixTime(long utcUnixTime) {
        this.utcUnixTime = utcUnixTime;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }
}
