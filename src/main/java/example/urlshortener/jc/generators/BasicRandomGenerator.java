package example.urlshortener.jc.generators;

import java.security.SecureRandom;

/**
 * The generation method can be extended with any class implementing the IUrlGenerator interface.
 * The initial and default method is this one, which just generates a random string based on a restricted
 * alphabet (no zero and 'O') to reduce possible user errors.
 *
 * Another method could use a different alphabet, or indeed a different way of generating the token, for example
 * using a method that could be guaranteed to be unique without a database lookup.
 */
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
