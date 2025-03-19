package community.whatever.onembackendjava.presentation;

public class ResponseDto {

    public record CreateShortenUrl(String shortenUrl) implements Responsible {
    }

    public record GetOriginUrl(String originalUrl) implements Responsible {
    }

}
