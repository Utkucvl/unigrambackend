package unigram.demo.service;

import unigram.demo.dto.ActivityDto;
import unigram.demo.dto.ActivityEditDto;
import unigram.demo.dto.EventDto;
import unigram.demo.dto.EventEditDto;

import java.util.List;

public interface EventService {

    EventDto save(EventEditDto eventDto);

    EventDto getById(Long id);

    List<EventDto> getEvents();

    Boolean delete(Long id);

    EventDto update(Long id, EventEditDto eventDto);


}
