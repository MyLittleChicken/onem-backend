package community.whatever.onembackendjava.application;

import org.springframework.stereotype.Service;

@Service
public class RandomKeyGeneratorImpl implements RandomKeyGenerator {

    private final Base62Converter base62Converter;

    public RandomKeyGeneratorImpl(Base62Converter base62Converter) {
        this.base62Converter = base62Converter;
    }

    @Override
    public String getRandomKey() {
        return base62Converter.encode(System.currentTimeMillis());
    }
}
