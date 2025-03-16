package community.whatever.onembackendjava.infrastructure;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BlockDomainLoaderImpl implements BlockDomainLoader {

    @Value("${block-domain.loader.file.name}")
    private String fileName;

    @Value("${block-domain.loader.source.url}")
    private String sourceUrl;

    private Path filePath;

    private final String SPACE_REGEX = "\\s+";
    private final String COMMENT_SYMBOL = "#";

    public Set<String> load() {

        if (!Files.exists(filePath)) {
            return new HashSet<>();
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            return reader.lines()
                    .filter(line -> !line.isEmpty() && !line.startsWith(COMMENT_SYMBOL))
                    .map(line -> line.trim().split(SPACE_REGEX)[1])
                    .collect(Collectors.toCollection(HashSet::new));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void init() throws IOException {
        filePath = Path.of(System.getProperty("user.dir"), fileName);

        if (!Files.exists(filePath)) {
            downloadFile();
        }
    }

    private void downloadFile() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String content = restTemplate.getForObject(sourceUrl, String.class);

        if (StringUtils.isBlank(content)) {
            return;
        }

        Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
