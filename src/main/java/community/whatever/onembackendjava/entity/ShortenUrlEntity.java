package community.whatever.onembackendjava.entity;

import java.time.Instant;

public record ShortenUrlEntity(
        String shortenUrl,
        String originUrl,
        boolean isDeleted,
        Instant createdAt,
        Instant expiredAt
) {

    public boolean isExpired() {
        return this.expiredAt != null
        && Instant.now().isAfter(this.expiredAt);
    }

    public static ShortenUrlEntity createEntity(
            final String shortenUrl,
            final String originUrl
    ) {
        return new ShortenUrlEntity(
                shortenUrl,
                originUrl,
                false,
                Instant.now(),
                null
        );
    }
}
