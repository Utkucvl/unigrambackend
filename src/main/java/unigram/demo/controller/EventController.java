package unigram.demo.controller;

import unigram.demo.dto.ActivityDto;
import unigram.demo.dto.ActivityEditDto;
import unigram.demo.dto.EventDto;
import unigram.demo.dto.EventEditDto;
import unigram.demo.repository.ActivityRepository;
import unigram.demo.repository.EventRepository;
import unigram.demo.service.impl.ActivityServiceImpl;
import unigram.demo.service.impl.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventServiceImpl eventServiceImpl;

    @GetMapping()
    public ResponseEntity<List<EventDto>> getAll() {
        List<EventDto> data= new ArrayList<>();
        data = eventServiceImpl.getEvents();
        return ResponseEntity.ok(data);
    }
    @GetMapping("/{eventid}")
    public ResponseEntity<EventDto> getById(@PathVariable(value = "eventid", required = true) Long eventid) {
        try {

            EventDto eventDto = eventServiceImpl.getById(eventid);
            return ResponseEntity.ok(eventDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); //return 404, with null body
        }
    }

    @PostMapping
    public ResponseEntity<?> createActivity(@RequestBody EventEditDto eventDto) {
        try {
            EventDto newEvent = eventServiceImpl.save(eventDto);

            return ResponseEntity.created(new URI("activity/"+newEvent.getId())).body(newEvent);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PutMapping("/{eventid}")
    public ResponseEntity<EventDto> updateActivity(@PathVariable(value = "eventid", required = true) Long eventid,
                                                      @RequestBody EventEditDto eventDto) {
        try {
            eventServiceImpl.update(eventid, eventDto);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }
    @DeleteMapping("/{eventid}")
    public ResponseEntity<?> delete(@PathVariable(value = "eventid", required = true) Long eventid) {
        try {
            if(eventid!=null)
            {

                eventServiceImpl.delete(eventid);
                return ResponseEntity.noContent().build();
            }
            else
            {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

}
