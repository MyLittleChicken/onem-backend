package community.whatever.onembackendjava.presentation;

import community.whatever.onembackendjava.application.UrlShortenService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlShortenController {

    private final UrlShortenService urlShortenService;

    public UrlShortenController(final UrlShortenService urlShortenService) {
        this.urlShortenService = urlShortenService;
    }

    @Deprecated(forRemoval = true)
    @PostMapping("/shorten-url/search")
    public String shortenUrlSearch(@RequestBody final String key) {
        return urlShortenService.getOriginalUrl(key);
    }

    @Deprecated(forRemoval = true)
    @PostMapping("/shorten-url/create")
    public String shortenUrlCreate(@RequestBody final String originUrl) {
        return urlShortenService.createShortUrl(originUrl);
    }

    @GetMapping(value = "/api/v1/shorten-url/{key}", produces = "application/json")
    public Responsible getShortenUrl(@PathVariable(name = "key") final String key) {
        String originUrl = urlShortenService.getOriginalUrl(key);
        return ResponseDto.GetOriginUrl.from(originUrl);
    }

    @PostMapping(value = "/api/v1/shorten-url", produces = "application/json", consumes = "application/json")
    public Responsible createShortUrl(@RequestBody final RequestDto.CreateShortenUrl request) {
        String shortUrl = urlShortenService.createShortUrl(request.getOriginUrl());
        return ResponseDto.CreateShotenUrl.from(shortUrl);
    }
}
