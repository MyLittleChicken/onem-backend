package community.whatever.onembackendjava.presentation;

public class ResponseDto {

    public static class CreateShortenUrl implements Responsible {
        private String shotenUrl;

        public CreateShortenUrl() {

        }

        public CreateShortenUrl(String shotenUrl) {
            this.shotenUrl = shotenUrl;
        }

        public String getShotenUrl() {
            return shotenUrl;
        }

        public static CreateShortenUrl from(String shotenUrl) {
            return new CreateShortenUrl(shotenUrl);
        }
    }

    public static class GetOriginUrl implements Responsible {
        private String originUrl;

        public GetOriginUrl() {

        }

        public GetOriginUrl(String originUrl) {
            this.originUrl = originUrl;
        }

        public String getOriginUrl() {
            return originUrl;
        }

        public static GetOriginUrl from(String originUrl) {
            return new GetOriginUrl(originUrl);
        }
    }

}
