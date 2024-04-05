package unigram.demo.service;

import unigram.demo.dto.ActivityDto;
import unigram.demo.dto.ActivityEditDto;
import unigram.demo.dto.ClubDto;

import java.util.List;

public interface ActivityService {
    ActivityDto save(ActivityEditDto activityDto);

    ActivityDto getById(Long id);

    List<ActivityDto> getActivities();

    List<ActivityDto> getPastActivities();

    Boolean delete(Long id);

    ActivityDto update(Long id, ActivityEditDto activityDto);

    ActivityDto addUserToActivity(Long userId, Long activityId);

    ActivityDto removeUserFromActivity(Long userId, Long activityId);

    List<ActivityDto> getFiltered(Long userId);

}
