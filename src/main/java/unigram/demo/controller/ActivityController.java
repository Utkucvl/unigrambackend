package unigram.demo.controller;

import unigram.demo.dao.entity.Activity;
import unigram.demo.dto.*;
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

    @GetMapping("/past")
    public ResponseEntity<List<ActivityDto>> getAllPastActivites() {
        List<ActivityDto> data= new ArrayList<>();
        data = activityServiceImpl.getPastActivities();
        return ResponseEntity.ok(data);
    }
    @GetMapping("/byclub/{clubid}")
    public ResponseEntity<List<ActivityDto>> getActivitiesByClubId(@PathVariable(value = "clubid", required = true) Long clubid) {
        List<ActivityDto> data= new ArrayList<>();
        data = activityServiceImpl.getActivitiesByClubId(clubid);
        return ResponseEntity.ok(data);
    }
    @GetMapping("/all")
    public ResponseEntity<List<ActivityDto>> getAllActivites() {
        List<ActivityDto> data= new ArrayList<>();
        data = activityServiceImpl.getAllActivities();
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

    @PostMapping("/add")
    public ResponseEntity<?> addUserToActivity(@RequestBody UserActivityDto userActivityDto) {
        try {
            if(userActivityDto.getActivityId()!=null || userActivityDto.getUserId() != null)
            {

                activityServiceImpl.addUserToActivity(userActivityDto.getUserId(), userActivityDto.getActivityId());
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
    @PostMapping("/remove")
    public ResponseEntity<?> removeUserFromActivity(@RequestBody UserActivityDto userActivityDto) {
        try {
            if(userActivityDto.getActivityId()!=null || userActivityDto.getUserId() != null)
            {

                activityServiceImpl.removeUserFromActivity(userActivityDto.getUserId(), userActivityDto.getActivityId());
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
    @GetMapping("/filter/{userid}")
    public ResponseEntity<List<ActivityDto>> getAllByUserId(@PathVariable(value = "userid", required = true) Long userid) {
        List<ActivityDto> data= new ArrayList<>();
        if(userid != null)
        data = activityServiceImpl.getFiltered(userid);
        return ResponseEntity.ok(data);
    }
    @GetMapping("/filterpast/{userid}")
    public ResponseEntity<List<ActivityDto>> getAllPastJoinedActivityByUserId(@PathVariable(value = "userid", required = true) Long userid) {
        List<ActivityDto> data= new ArrayList<>();
        if(userid != null)
            data = activityServiceImpl.getJoinedPastActivites(userid);
        return ResponseEntity.ok(data);
    }

}
