package example.urlshortener.jc.generators;

import java.security.SecureRandom;

public class BasicRandomGenerator implements IUrlGenerator {
    private SecureRandom randomGen = new SecureRandom();

    @Override
    public String generate(int desiredLength) {

        if (desiredLength < MIN_SHORT_URL_SIZE || desiredLength > MAX_SHORT_URL_SIZE)
            throw new IllegalArgumentException(
                    String.format("desired length must be between >= %d and <= %d", MIN_SHORT_URL_SIZE, MAX_SHORT_URL_SIZE));

        final String alphabet = "abcdefghkmnpqrstwxyzABCDEFGHKMNPQRSTWXYZ";
        final int alphabetLen = alphabet.length();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < desiredLength; i++) {
            int idx = randomGen.nextInt(alphabetLen);
            sb.append(alphabet.charAt(idx));
        }

        return sb.toString();
    }
}
