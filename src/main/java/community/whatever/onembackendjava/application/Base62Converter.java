package community.whatever.onembackendjava.application;

import org.springframework.stereotype.Component;

@Component
public class Base62Converter {
    private static final String BASE62_CHARACTERS = "jZi7gR1q8WBpKyDXA5m0fhL9xC2szNUoVldE6wtkG3O4bTnMeHPvaIFcSYQJru";
    private static final int BASE62 = 62;

    public String encode(long value) {
        StringBuffer sb = new StringBuffer();

        while (value > 0) {
            sb.append(BASE62_CHARACTERS.charAt((int) (value % BASE62)));
            value /= BASE62;
        }

        return sb.reverse().toString();
    }

    public long decode(String base62String) {
        long value = 0;

        for (char c : base62String.toCharArray()) {
            value = value * BASE62 + BASE62_CHARACTERS.indexOf(c);
        }

        return value;
    }
}
