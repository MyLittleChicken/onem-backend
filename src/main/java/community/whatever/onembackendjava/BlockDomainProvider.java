package community.whatever.onembackendjava;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URL;
import java.util.Set;

@Component
public class BlockDomainProvider {
    private final Set<String> domains;

    public BlockDomainProvider(final BlockDomainLoader blockDomainLoader) {
        this.domains = blockDomainLoader.load();
    }

    public boolean isBlocked(final String authority) {
        if (authority == null || authority.isBlank()) {
            return true;
        }

        return this.domains.contains(authority);
    }

    public boolean isBlocked(final URI uri) {
        if (uri == null || uri.getAuthority().isBlank()) {
            return true;
        }

        return this.domains.contains(uri.getAuthority());
    }

    public boolean isBlocked(final URL url) {
        if (url == null || url.getAuthority().isBlank()) {
            return true;
        }

        return this.domains.contains(url.getAuthority());
    }

}
