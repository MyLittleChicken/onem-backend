package community.whatever.onembackendjava.entity;

import community.whatever.onembackendjava.infrastructure.ShortenUrlRecord;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShortenUrlEntity {
    private String shortenUrl;
    private String originUrl;
    private boolean isDeleted;
    private Instant createdAt;
    private Instant expiredAt; // if null, it means never expired

    // constructor for creating new entity
    private ShortenUrlEntity(
            final String shortenUrl,
            final String originUrl
    ) {
        this.shortenUrl = shortenUrl;
        this.originUrl = originUrl;
        this.isDeleted = false;
        this.createdAt = Instant.now();
        this.expiredAt = null;
    }

    public void markAsDeleted() {
        this.isDeleted = true;
    }

    public void updateExpiredAt(final Long expirationMinutes) {
        this.expiredAt = Optional.ofNullable(expirationMinutes)
                .filter(minutes -> minutes > 0)
                .map(minutes -> this.createdAt.plus(Duration.ofMinutes(minutes)))
                .orElse(null);
    }

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
                originUrl
        );
    }

    public static ShortenUrlEntity fromRecord(final ShortenUrlRecord record) {
        return new ShortenUrlEntity(
                record.shortenUrl(),
                record.originUrl(),
                record.isDeleted(),
                record.createdAt(),
                record.expiredAt()
        );
    }
}
