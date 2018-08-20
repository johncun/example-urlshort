package example.urlshortener.jc.jsonobj;

public class UrlShortenRequest {

	public static final int DEFAULT_SHORTEN_LENGTH = 5;

	private int version;
	private String url;
	private int desiredLength = DEFAULT_SHORTEN_LENGTH;

	public String getUrl() {
		return url;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getDesiredLength() {
		return desiredLength;
	}

	public void setDesiredLength(int desiredLength) {
		this.desiredLength = desiredLength;
	}
}
