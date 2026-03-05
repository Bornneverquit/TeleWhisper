package com.telewhisper.repository;

import com.telewhisper.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Find all events by telegram chat ID
    List<Event> findByTelegramChatId(String telegramChatId);

    // Find all events by status
    List<Event> findByStatus(Event.EventStatus status);

    // Find all pending events before a certain time (for scheduler)
    List<Event> findByStatusAndEventDateTimeBefore(
            Event.EventStatus status,
            LocalDateTime dateTime
    );

    // Find all yearly repeating events
    List<Event> findByIsRepeatYearlyTrue();

    // Find events by type
    List<Event> findByEventType(String eventType);
}