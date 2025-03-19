package community.whatever.onembackendjava.entity;

import community.whatever.onembackendjava.infrastructure.ShortenUrlRecord;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class ShortenUrlEntity {
    private String shortenUrl;
    private String originUrl;
    private boolean isDeleted;
    private Instant createdAt;
    private Instant expiredAt; // if null, it means never expired

    public ShortenUrlEntity() {}

    // constructor for creating new entity
    public ShortenUrlEntity(
            final String shortenUrl,
            final String originUrl
    ) {
        this.shortenUrl = shortenUrl;
        this.originUrl = originUrl;
        this.isDeleted = false;
        this.createdAt = Instant.now();
        this.expiredAt = null;
    }

    // constructor for creating entity from record
    public ShortenUrlEntity(
            final String shortenUrl,
            final String originUrl,
            final boolean isDeleted,
            final Instant createdAt,
            final Instant expiredAt
    ) {
        this.shortenUrl = shortenUrl;
        this.originUrl = originUrl;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    public String getShortenUrl() {
        return this.shortenUrl;
    }

    public String getOriginUrl() {
        return this.originUrl;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getExpiredAt() {
        return this.expiredAt;
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
