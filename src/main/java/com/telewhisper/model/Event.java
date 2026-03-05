package com.telewhisper.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    private LocalDateTime eventDateTime;

    @Column
    private String description;

    @Column(nullable = false)
    private String telegramChatId;

    @Column
    private String emailAddress;

    @Column(nullable = false)
    private Integer reminderMinutesBefore;

    @Column(nullable = false)
    private Boolean isRepeatYearly;

    @Column
    private LocalDateTime nextOccurrence;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ─── Constructors ──────────────────────────
    public Event() {}

    // ─── Getters ───────────────────────────────
    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getTitle() { return title; }
    public String getEventType() { return eventType; }
    public LocalDateTime getEventDateTime() { return eventDateTime; }
    public String getDescription() { return description; }
    public String getTelegramChatId() { return telegramChatId; }
    public String getEmailAddress() { return emailAddress; }
    public Integer getReminderMinutesBefore() { return reminderMinutesBefore; }
    public Boolean getIsRepeatYearly() { return isRepeatYearly; }
    public LocalDateTime getNextOccurrence() { return nextOccurrence; }
    public EventStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ─── Setters ───────────────────────────────
    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setTitle(String title) { this.title = title; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public void setEventDateTime(LocalDateTime eventDateTime) { this.eventDateTime = eventDateTime; }
    public void setDescription(String description) { this.description = description; }
    public void setTelegramChatId(String telegramChatId) { this.telegramChatId = telegramChatId; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public void setReminderMinutesBefore(Integer reminderMinutesBefore) { this.reminderMinutesBefore = reminderMinutesBefore; }
    public void setIsRepeatYearly(Boolean isRepeatYearly) { this.isRepeatYearly = isRepeatYearly; }
    public void setNextOccurrence(LocalDateTime nextOccurrence) { this.nextOccurrence = nextOccurrence; }
    public void setStatus(EventStatus status) { this.status = status; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // ─── Lifecycle Hooks ───────────────────────
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = EventStatus.PENDING;
        if (isRepeatYearly == null) isRepeatYearly = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ─── Enum ──────────────────────────────────
    public enum EventStatus {
        PENDING,
        NOTIFIED,
        COMPLETED,
        CANCELLED
    }
}