package community.whatever.onembackendjava.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RandomKeyGeneratorImpl implements RandomKeyGenerator {

    private final Base62Converter base62Converter;

    @Override
    public String getRandomKey() {
        return base62Converter.encode(System.currentTimeMillis());
    }
}
