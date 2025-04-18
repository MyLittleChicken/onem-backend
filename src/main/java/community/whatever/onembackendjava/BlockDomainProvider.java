package community.whatever.onembackendjava;

import community.whatever.onembackendjava.infrastructure.BlockDomainLoader;
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

    public boolean isBlocked(final String authority) {
        return StringUtils.isBlank(authority)
        || this.domains.contains(authority);
    }

    public boolean isBlocked(final URI uri) {
        return StringUtils.isBlank(uri.getAuthority())
        || this.domains.contains(uri.getAuthority());
    }

    public boolean isBlocked(final URL url) {
        return StringUtils.isBlank(url.getAuthority())
        || this.domains.contains(url.getAuthority());
    }

}
