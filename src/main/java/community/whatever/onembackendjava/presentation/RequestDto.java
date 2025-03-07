package community.whatever.onembackendjava.presentation;

public class RequestDto {

    public static class CreateShortenUrl {
        private String originUrl;

        public CreateShortenUrl() {

        }

        public CreateShortenUrl(String originUrl) {
            this.originUrl = originUrl;
        }

        public String getOriginUrl() {
            return originUrl;
        }
    }

}
