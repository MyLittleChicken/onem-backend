package community.whatever.onembackendjava.presentation;

import community.whatever.onembackendjava.application.UrlShortenService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlShortenController {

    private final UrlShortenService urlShortenService;

    public UrlShortenController(final UrlShortenService urlShortenService) {
        this.urlShortenService = urlShortenService;
    }

    @GetMapping(value = "/api/v1/shorten-url/{key}", produces = "application/json")
    public Responsible getShortenUrl(@PathVariable(name = "key") final String key) {
        String originalUrl = urlShortenService.getOriginalUrl(key);
        return new ResponseDto.GetOriginUrl(originalUrl);
    }

    @PostMapping(value = "/api/v1/shorten-url", produces = "application/json", consumes = "application/json")
    public Responsible createShortUrl(@RequestBody final RequestDto.CreateShortenUrl request) {
        String shortUrl = urlShortenService.createShortUrl(request);
        return new ResponseDto.CreateShortenUrl(shortUrl);
    }
}
