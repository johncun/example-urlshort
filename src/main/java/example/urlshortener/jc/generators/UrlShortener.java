package example.urlshortener.jc.generators;

import java.security.SecureRandom;

public class UrlShortener {
    private final String url;

    private IUrlGenerator.GenerationMethod generationMethod = IUrlGenerator.GenerationMethod.DEFAULT;

    public UrlShortener(String url) {
        this.url = url;
    }

    public UrlShortener setMode(IUrlGenerator.GenerationMethod gm) {
        this.generationMethod = gm;
        return this;

    }

    public String generate(int desiredLength) {
        switch (generationMethod) {
            case DEFAULT:
            case BASIC_RANDOM:
                return new BasicRandomGenerator().generate(desiredLength);

            default:
                throw new RuntimeException("an illegal value for the generation method was found");
        }
    }
}
