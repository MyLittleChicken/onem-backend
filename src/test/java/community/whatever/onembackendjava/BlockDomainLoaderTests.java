package community.whatever.onembackendjava;

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
    private BlockDomains blockDomains;

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

        assertTrue(blockDomains.isBlocked(url));
        assertTrue(blockDomains.isBlocked(uri));
        assertTrue(blockDomains.isBlocked(url.getAuthority()));
        assertTrue(blockDomains.isBlocked(uri.getAuthority()));
        assertTrue(blockDomains.isBlocked(uri.getRawAuthority()));
    }

}
