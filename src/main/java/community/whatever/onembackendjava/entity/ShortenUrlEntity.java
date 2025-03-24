package community.whatever.onembackendjava.entity;

import java.time.LocalDateTime;

public record ShortenUrlEntity(
        String shortenUrl,
        String originUrl,
        boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime expiredAt
) {

    public boolean isExpired() {
        return this.expiredAt != null
        && LocalDateTime.now().isAfter(this.expiredAt);
    }

    public static ShortenUrlEntity createEntity(
            final String shortenUrl,
            final String originUrl
    ) {
        return new ShortenUrlEntity(
                shortenUrl,
                originUrl,
                false,
                LocalDateTime.now(),
                null
        );
    }
}
