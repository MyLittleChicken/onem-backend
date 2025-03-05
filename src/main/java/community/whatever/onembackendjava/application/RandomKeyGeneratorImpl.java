package community.whatever.onembackendjava.application;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomKeyGeneratorImpl implements RandomKeyGenerator {

    private final Random random = new Random();

    @Override
    public String getRandomKey() {
        return String.valueOf(random.nextInt(10000));
    }
}
