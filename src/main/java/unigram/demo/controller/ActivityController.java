package unigram.demo.controller;

import unigram.demo.dto.ActivityDto;
import unigram.demo.dto.ActivityEditDto;
import unigram.demo.repository.ActivityRepository;
import unigram.demo.service.impl.ActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    ActivityServiceImpl activityServiceImpl;

    @GetMapping()
    public ResponseEntity<List<ActivityDto>> getAll() {
        List<ActivityDto> data= new ArrayList<>();
        data = activityServiceImpl.getActivities();
        return ResponseEntity.ok(data);
    }


    @GetMapping("/{activityid}")
    public ResponseEntity<ActivityDto> getById(@PathVariable(value = "activityid", required = true) Long activityid) {
        try {

            ActivityDto activityDto = activityServiceImpl.getById(activityid);
            return ResponseEntity.ok(activityDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); //return 404, with null body
        }
    }
    @PostMapping
    public ResponseEntity<?> createActivity(@RequestBody ActivityEditDto activityDto) {
        try {
            ActivityDto newTestplan = activityServiceImpl.save(activityDto);

            return ResponseEntity.created(new URI("activity/"+newTestplan.getId())).body(newTestplan);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{activityid}")
    public ResponseEntity<ActivityDto> updateActivity(@PathVariable(value = "activityid", required = true) Long activityid,
                                                      @RequestBody ActivityEditDto activityDto) {
        try {
            activityServiceImpl.update(activityid, activityDto);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{activityid}")
    public ResponseEntity<?> delete(@PathVariable(value = "activityid", required = true) Long activityid) {
        try {
            if(activityid!=null)
            {

                activityServiceImpl.delete(activityid);
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
