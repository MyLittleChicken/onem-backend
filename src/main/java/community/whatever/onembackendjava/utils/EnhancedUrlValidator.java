package community.whatever.onembackendjava.utils;

import org.apache.commons.validator.routines.RegexValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

@Component
public class EnhancedUrlValidator {

    private static final String[] ALLOW_SCHEMES = { "http", "https", "ftp" };
    private static final String[] DISALLOW_REGEX = { "^[^@]*$" };

    private final RegexValidator regexValidator = new RegexValidator(DISALLOW_REGEX);
    private final UrlValidator urlValidator = new UrlValidator(ALLOW_SCHEMES);

    public boolean isValid(final String url) {
        return regexValidator.isValid(url)
        && urlValidator.isValid(url);
    }

    public boolean isNotValid(final String url) {
        return !isValid(url);
    }

}
