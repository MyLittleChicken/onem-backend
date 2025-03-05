package community.whatever.onembackendjava.application;

public interface UrlShortenService {

    /**
     * 주어진 단축 url 에 해당하는 원본 url 을 반환합니다.
     *
     * @param shortUrl 단축 url, 사용자가 입력한 짧은 주소를 뜻합니다.
     * @return 조회된 원본 url, 문자열로 반환됩니다. 만약 존재하지 않는 단축 url 이라면 예외가 발생할 수 있습니다.
     * @throws IllegalArgumentException 존재하지 않는 단축 url 일 경우 발생합니다.
     *
     */
    String getOriginalUrl(final String shortUrl);

    /**
     * 주어진 원본 url 에 해당하는 단축 url 을 생성, 반환합니다.
     *
     * @param originUrl 원본 url, 사용자가 입력한 긴 주소를 뜻합니다.
     * @return 생성된 단축 url, 문자열로 반환됩니다.
     *
     */
    String createShortUrl(final String originUrl);

}
