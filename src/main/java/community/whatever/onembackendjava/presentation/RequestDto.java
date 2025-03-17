package community.whatever.onembackendjava.presentation;

public class RequestDto {

    public record CreateShortenUrl(
            String originalUrl,
            Long expirationMinutes
    ) {
        public CreateShortenUrl(String originalUrl) {
            this(originalUrl, 0L);
        }
    }

}
