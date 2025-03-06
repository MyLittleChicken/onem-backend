package community.whatever.onembackendjava.presentation;

public class ResponseDto {

    public static class CreateShotenUrl {
        private String shotenUrl;

        public CreateShotenUrl() {

        }

        public CreateShotenUrl(String shotenUrl) {
            this.shotenUrl = shotenUrl;
        }

        public String getShotenUrl() {
            return shotenUrl;
        }
    }

    public static class GetOriginUrl {
        private String originUrl;

        public GetOriginUrl() {

        }

        public GetOriginUrl(String originUrl) {
            this.originUrl = originUrl;
        }

        public String getOriginUrl() {
            return originUrl;
        }
    }

}
