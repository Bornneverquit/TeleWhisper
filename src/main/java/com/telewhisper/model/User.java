package com.telewhisper.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long telegramId;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String username;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Event> events;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NotificationLog> notificationLogs;

    // ─── Constructors ──────────────────────────
    public User() {}

    // ─── Getters ───────────────────────────────
    public Long getId() { return id; }
    public Long getTelegramId() { return telegramId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public Boolean getIsActive() { return isActive; }
    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public List<Event> getEvents() { return events; }
    public List<NotificationLog> getNotificationLogs() { return notificationLogs; }

    // ─── Setters ───────────────────────────────
    public void setId(Long id) { this.id = id; }
    public void setTelegramId(Long telegramId) { this.telegramId = telegramId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }
    public void setEvents(List<Event> events) { this.events = events; }
    public void setNotificationLogs(List<NotificationLog> notificationLogs) { this.notificationLogs = notificationLogs; }

    // ─── Lifecycle ─────────────────────────────
    @PrePersist
    protected void onCreate() {
        registeredAt = LocalDateTime.now();
        if (isActive == null) isActive = true;
    }
}