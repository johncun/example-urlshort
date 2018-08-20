package example.urlshortener.jc.generators;

import java.security.SecureRandom;

/**
 * The single and default mode is IUrlGenerator.GenerationMethod.DEFAULT which is mapped to
 * IUrlGenerator.GenerationMethod.BASIC_RANDOM. If this is extended then this method could be set
 * via an application configuration setting, or even an extra parameter to the /shorten API.
 *
 */
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
