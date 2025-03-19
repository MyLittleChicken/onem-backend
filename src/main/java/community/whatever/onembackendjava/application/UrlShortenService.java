package community.whatever.onembackendjava.application;

import community.whatever.onembackendjava.presentation.RequestDto;

public interface UrlShortenService {

    /**
     * 주어진 단축 url 에 해당하는 원본 url 을 반환합니다.
     *
     * @param shortUrl 단축 url, 사용자가 입력한 짧은 주소를 뜻합니다.
     * @return 조회된 원본 url, 문자열로 반환됩니다. 만약 존재하지 않는 단축 url 이라면 예외가 발생할 수 있습니다.
     *
     */
    String getOriginalUrl(final String shortUrl);

    /**
     * 주어진 원본 url 에 해당하는 단축 url 을 생성, 반환합니다.
     *
     * @param requestDto 원본 url, 만료 시간을 포함하는 요청 dto
     * @return 생성된 단축 url, 문자열로 반환됩니다.
     */
    String createShortUrl(final RequestDto.CreateShortenUrl requestDto);

}
