package unigram.demo.service.impl;

import unigram.demo.dao.entity.Activity;
import unigram.demo.dao.entity.Club;
import unigram.demo.dto.ActivityDto;
import unigram.demo.dto.ActivityEditDto;
import unigram.demo.repository.ActivityRepository;
import unigram.demo.repository.ClubRepository;
import unigram.demo.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ClubRepository clubRepository;



    @Override
    public ActivityDto save(ActivityEditDto activityDto) {

        Club club = clubRepository.getReferenceById(activityDto.getClubid());
        Activity activity = new Activity();
        activity.setName(activityDto.getName());
        activity.setContent(activityDto.getContent());
        activity.setPlace(activityDto.getPlace());
        activity.setDate(activityDto.getDate());
        activity.setClub(club);
        activity.setPhotoUrl(activityDto.getPhotoUrl());
        activity= activityRepository.save(activity);
        activityDto.setId(activity.getId());
        addClubActivities((long) activity.getClub().getId(),activity);
        ActivityDto dto = new ActivityDto();
        dto.setId(activity.getId());
        dto.setName(activity.getName());
        dto.setClub(club);
        dto.setContent(activity.getContent());
        dto.setPlace(activity.getPlace());
        dto.setDate(activity.getDate());
        return dto;
    }

    @Override
    public ActivityDto getById(Long id) {
        Activity n = activityRepository.getOne(id);
        String clubName = clubRepository.getReferenceById(Long.valueOf(n.getClub().getId())).getName();
        ActivityDto dto = new ActivityDto();
        dto.setClub(n.getClub());
        dto.setDate(n.getDate());
        dto.setPlace(n.getPlace());
        dto.setContent(n.getContent());
        dto.setPhotoUrl(n.getPhotoUrl());
        dto.setName(n.getName());
        dto.setClubName(clubName);
        dto.setId(n.getId());
        return dto;
    }

    @Override
    public List<ActivityDto> getActivities() {
        List<Activity> data = activityRepository.findAll();
        return activitiesToDtoList(data);
    }

    @Override
    public Boolean delete(Long id) {
        activityRepository.deleteById(id);
        return true;
    }

    @Override
    public ActivityDto update(Long id, ActivityEditDto activityDto) {
        Activity activityDb = activityRepository.getOne(id);
        if (activityDb == null)
            throw new IllegalArgumentException("Activity Does Not Exist ID:" + id);

        Club clubDb= clubRepository.getOne(activityDto.getClubid());
        if (clubDb == null)
            throw new IllegalArgumentException("Club Does Not Exist ID:" + id);

        activityDb.setName(activityDto.getName());
        activityDb.setDate(activityDto.getDate());
        activityDb.setContent(activityDto.getContent());
        activityDb.setPlace(activityDto.getPlace());
        activityDb.setClub(clubDb);
        activityDb.setPhotoUrl(activityDto.getPhotoUrl());
        Activity n = activityRepository.save(activityDb);
        addClubActivities((long)n.getClub().getId(),n);
        ActivityDto dto = new ActivityDto();
        dto.setId(activityDb.getId());
        dto.setClub(activityDb.getClub());
        dto.setDate(activityDb.getDate());
        dto.setContent(activityDb.getContent());
        dto.setPlace(activityDb.getPlace());
        dto.setName(activityDb.getName());
        dto.setPhotoUrl(activityDb.getPhotoUrl());
        return dto;
    }
    @Override
    public List<ActivityDto> findByClubid(Long id) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Club Does Not Exist ID:" + id));

        List<Activity> activities = activityRepository.findByClubId(id);

        return activities.stream().map((activity) -> activityToDtoList(activity))
                .collect(Collectors.toList());
    }
    private void addClubActivities(Long clubid,Activity activity){
        Club t = clubRepository.getReferenceById(clubid);
        List<Activity> activityList = t.getActivities();
        activityList.add(activity);
        clubRepository.save(t);
    }

    public List<ActivityDto> activitiesToDtoList(List<Activity> activities) {
        List<ActivityDto> dtos = new ArrayList<>();

        for (Activity activity : activities) {
            String clubName = clubRepository.getReferenceById(Long.valueOf(activity.getClub().getId())).getName();
            ActivityDto dto = new ActivityDto();
            dto.setId(activity.getId());
            dto.setName(activity.getName());
            dto.setPlace(activity.getPlace());
            dto.setDate(activity.getDate());
            dto.setPhotoUrl(activity.getPhotoUrl());
            dto.setContent(activity.getContent());
            dto.setClub(activity.getClub());
            dto.setClubName(clubName);
            dtos.add(dto);
        }

        return dtos;
    }
    public ActivityDto activityToDtoList(Activity activity) {

        ActivityDto dto = new ActivityDto();
        dto.setId(activity.getId());
        dto.setName(activity.getName());
        dto.setPlace(activity.getPlace());
        dto.setDate(activity.getDate());
        dto.setPhotoUrl(activity.getPhotoUrl());
        dto.setContent(activity.getContent());
        dto.setClub(activity.getClub());

        return dto;

    }


}
