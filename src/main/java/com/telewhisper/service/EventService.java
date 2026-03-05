package com.telewhisper.service;

import com.telewhisper.model.Event;
import com.telewhisper.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public Event createEvent(Event event) {
        log.info("Creating new event: {}", event.getTitle());
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public List<Event> getEventsByTelegramChatId(String chatId) {
        return eventRepository.findByTelegramChatId(chatId);
    }

    public List<Event> getEventsByStatus(Event.EventStatus status) {
        return eventRepository.findByStatus(status);
    }

    @Transactional
    public Event updateEvent(Long id, Event updatedEvent) {
        log.info("Updating event with id: {}", id);
        return eventRepository.findById(id)
                .map(existingEvent -> {
                    existingEvent.setTitle(updatedEvent.getTitle());
                    existingEvent.setEventType(updatedEvent.getEventType());
                    existingEvent.setEventDateTime(updatedEvent.getEventDateTime());
                    existingEvent.setDescription(updatedEvent.getDescription());
                    existingEvent.setReminderMinutesBefore(updatedEvent.getReminderMinutesBefore());
                    existingEvent.setIsRepeatYearly(updatedEvent.getIsRepeatYearly());
                    existingEvent.setStatus(updatedEvent.getStatus());
                    return eventRepository.save(existingEvent);
                })
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    }

    @Transactional
    public void deleteEvent(Long id) {
        log.info("Deleting event with id: {}", id);
        eventRepository.deleteById(id);
    }

    public List<Event> getPendingEventsBeforeTime(LocalDateTime dateTime) {
        return eventRepository.findByStatusAndEventDateTimeBefore(
                Event.EventStatus.PENDING, dateTime
        );
    }
}