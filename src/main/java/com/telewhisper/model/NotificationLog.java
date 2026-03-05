package com.telewhisper.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_logs")
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column
    private String errorMessage;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    @Column
    private Integer retryCount;

    // ─── Constructors ──────────────────────────
    public NotificationLog() {}

    // ─── Getters ───────────────────────────────
    public Long getId() { return id; }
    public Event getEvent() { return event; }
    public User getUser() { return user; }
    public NotificationType getNotificationType() { return notificationType; }
    public NotificationStatus getStatus() { return status; }
    public String getMessage() { return message; }
    public String getErrorMessage() { return errorMessage; }
    public LocalDateTime getSentAt() { return sentAt; }
    public Integer getRetryCount() { return retryCount; }

    // ─── Setters ───────────────────────────────
    public void setId(Long id) { this.id = id; }
    public void setEvent(Event event) { this.event = event; }
    public void setUser(User user) { this.user = user; }
    public void setNotificationType(NotificationType notificationType) { this.notificationType = notificationType; }
    public void setStatus(NotificationStatus status) { this.status = status; }
    public void setMessage(String message) { this.message = message; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
    public void setRetryCount(Integer retryCount) { this.retryCount = retryCount; }

    // ─── Lifecycle ─────────────────────────────
    @PrePersist
    protected void onCreate() {
        sentAt = LocalDateTime.now();
        if (retryCount == null) retryCount = 0;
        if (status == null) status = NotificationStatus.PENDING;
    }

    // ─── Enums ─────────────────────────────────
    public enum NotificationType {
        EMAIL,
        TELEGRAM
    }

    public enum NotificationStatus {
        PENDING,
        SENT,
        FAILED,
        RETRYING
    }
}