package unigram.demo.service.impl;

import unigram.demo.dao.entity.Activity;
import unigram.demo.dao.entity.Club;
import unigram.demo.dao.entity.Event;
import unigram.demo.dto.ActivityDto;
import unigram.demo.dto.ActivityEditDto;
import unigram.demo.dto.EventDto;
import unigram.demo.dto.EventEditDto;
import unigram.demo.repository.ActivityRepository;
import unigram.demo.repository.ClubRepository;
import unigram.demo.repository.EventRepository;
import unigram.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;

    @Override
    public EventDto save(EventEditDto eventDto) {
        System.out.println(eventDto);
        Event event = new Event();
        event.setTitle(eventDto.getTitle());
        event.setEnd(eventDto.getEnd());
        event.setStart(eventDto.getStart());
        event= eventRepository.save(event);
        eventDto.setId(event.getId());
        EventDto dto = new EventDto();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setStart(event.getStart());
        dto.setEnd(event.getEnd());
        return dto;
    }

    @Override
    public EventDto getById(Long id) {
        Event n = eventRepository.getOne(id);
        EventDto dto = new EventDto();
        dto.setEnd(n.getEnd());
        dto.setTitle(n.getTitle());
        dto.setStart(n.getStart());
        dto.setId(n.getId());
        return dto;
    }
    @Override
    public List<EventDto> getEvents() {
        List<Event> data = eventRepository.findAll();
        return eventsToDtoList(data);
    }
    @Override
    public Boolean delete(Long id) {
        eventRepository.deleteById(id);
        return true;
    }
    @Override
    public EventDto update(Long id, EventEditDto eventDto) {
        Event eventDb = eventRepository.getOne(id);
        if (eventDb == null)
            throw new IllegalArgumentException("Activity Does Not Exist ID:" + id);

        eventDb.setTitle(eventDto.getTitle());
        eventDb.setEnd(eventDto.getEnd());
        eventDb.setStart(eventDto.getStart());
        Event n = eventRepository.save(eventDb);
        EventDto dto = new EventDto();
        dto.setId(eventDb.getId());
        dto.setStart(eventDb.getStart());
        dto.setEnd(eventDb.getEnd());
        dto.setTitle(eventDb.getTitle());
        return dto;
    }
    public List<EventDto> eventsToDtoList(List<Event> events) {
        List<EventDto> dtos = new ArrayList<>();

        for (Event event : events) {
            EventDto dto = new EventDto();
            dto.setId(event.getId());
            dto.setTitle(event.getTitle());
            dto.setEnd(event.getEnd());
            dto.setStart(event.getStart());
            dtos.add(dto);
        }

        return dtos;
    }
}
