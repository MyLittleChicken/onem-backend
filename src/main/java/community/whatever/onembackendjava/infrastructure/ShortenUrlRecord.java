package community.whatever.onembackendjava.infrastructure;

import community.whatever.onembackendjava.entity.ShortenUrlEntity;

import java.time.Instant;

public record ShortenUrlRecord(
        String shortenUrl,
        String originUrl,
        boolean isDeleted,
        Instant createdAt,
        Instant expiredAt
) {

    public static ShortenUrlRecord fromEntity(final ShortenUrlEntity entity) {
        return new ShortenUrlRecord(
                entity.getShortenUrl(),
                entity.getOriginUrl(),
                entity.isDeleted(),
                entity.getCreatedAt(),
                entity.getExpiredAt()
        );
    }
}
