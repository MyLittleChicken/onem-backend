package community.whatever.onembackendjava.utils;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URL;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class BlockDomainProvider {
    private final Set<String> domains;
    private final EnhancedUrlValidator enhancedUrlValidator;

    public boolean isBlocked(final String url) {
        return StringUtils.isBlank(url)
        || this.enhancedUrlValidator.isNotValid(url)
        || this.domains.contains(URI.create(url).getAuthority());
    }

    public boolean isBlocked(final URI uri) {
        return StringUtils.isBlank(uri.toString())
        || this.enhancedUrlValidator.isNotValid(uri.toString())
        || this.domains.contains(uri.getAuthority());
    }

    public boolean isBlocked(final URL url) {
        return StringUtils.isBlank(url.toString())
        || this.enhancedUrlValidator.isNotValid(url.toString())
        || this.domains.contains(url.getAuthority());
    }

}
