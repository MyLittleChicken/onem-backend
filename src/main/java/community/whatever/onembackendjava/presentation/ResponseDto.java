package community.whatever.onembackendjava.presentation;

public class ResponseDto {

    public static class CreateShotenUrl implements Responsible {
        private String shotenUrl;

        public CreateShotenUrl() {

        }

        public CreateShotenUrl(String shotenUrl) {
            this.shotenUrl = shotenUrl;
        }

        public String getShotenUrl() {
            return shotenUrl;
        }

        public static CreateShotenUrl from(String shotenUrl) {
            return new CreateShotenUrl(shotenUrl);
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
