package example.urlshortener.jc.generators;

public interface IUrlGenerator {
    int MIN_SHORT_URL_SIZE = 4;
    int MAX_SHORT_URL_SIZE = 10;

    public enum GenerationMethod {
        DEFAULT, BASIC_RANDOM
    }

    public String generate(int desiredLength);
}
