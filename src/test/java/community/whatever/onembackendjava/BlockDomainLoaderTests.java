package community.whatever.onembackendjava;

import community.whatever.onembackendjava.infrastructure.BlockDomainLoader;
import community.whatever.onembackendjava.utils.BlockDomainProvider;
import community.whatever.onembackendjava.utils.EnhancedUrlValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BlockDomainLoaderTests {

    @Autowired
    private BlockDomainLoader blockDomainLoader;
    @Autowired
    private BlockDomainProvider blockDomainProvider;
    @Autowired
    private EnhancedUrlValidator enhancedUrlValidator;

    @Test
    void testBlockDomainLoader() {
        assertNotNull(blockDomainLoader.load());
        assertInstanceOf(Collection.class, blockDomainLoader.load());
    }

    @Test
    void testDomainBlock() throws URISyntaxException, MalformedURLException {
        String localhost = "http://localhost";
        URL url = new URL(localhost);
        URI uri = new URI(localhost);

        assertTrue(blockDomainProvider.isBlocked(url));
        assertTrue(blockDomainProvider.isBlocked(uri));
        assertTrue(blockDomainProvider.isBlocked(url.getAuthority()));
        assertTrue(blockDomainProvider.isBlocked(uri.getAuthority()));
        assertTrue(blockDomainProvider.isBlocked(uri.getRawAuthority()));
    }

    @Test
    void testUrlValidator() throws MalformedURLException, URISyntaxException {
        String localhost = "http://localhost";
        String fragment = "http://localhost#";
        String userinfo = "http://google.com@domain.com";
        String spoofing = "https://naver.com                                                                              @phishing.xyz";

        assertFalse(enhancedUrlValidator.isValid(localhost));
        assertFalse(enhancedUrlValidator.isValid(fragment));
        assertFalse(enhancedUrlValidator.isValid(userinfo));
        assertFalse(enhancedUrlValidator.isValid(spoofing));

        assertTrue(enhancedUrlValidator.isNotValid(localhost));
        assertTrue(enhancedUrlValidator.isNotValid(fragment));
        assertTrue(enhancedUrlValidator.isNotValid(userinfo));
        assertTrue(enhancedUrlValidator.isNotValid(spoofing));
    }

}
