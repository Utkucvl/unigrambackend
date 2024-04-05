package unigram.demo.service.impl;

import unigram.demo.dao.entity.Activity;
import unigram.demo.dao.entity.Club;
import unigram.demo.dao.entity.User;
import unigram.demo.dto.ActivityDto;
import unigram.demo.dto.ClubDto;
import unigram.demo.repository.ClubRepository;
import unigram.demo.repository.UserRepository;
import unigram.demo.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubServiceImpl implements ClubService {
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public ClubDto save(ClubDto clubDto) {
        User user = userRepository.findByUserName("utku");
        Club d = new Club();
        d.setName(clubDto.getName());
        d.setContent(clubDto.getContent());
        d.setCommunication(clubDto.getCommunication());
        d.setUsersId(new ArrayList<>());
        d.setActivities(new ArrayList<>());
        d.setPhotoUrl(clubDto.getPhotoUrl());

        d = clubRepository.save(d);

        clubDto.setId(d.getId());
        return clubDto;
    }
    @Override
    public ClubDto getById(Long id) {
        Club p = clubRepository.getReferenceById(id);
        ClubDto clubDto = new ClubDto();
        clubDto.setName(p.getName());
        clubDto.setId(p.getId());
        clubDto.setContent(p.getContent());
        clubDto.setCommunication((p.getCommunication()));
        clubDto.setUsersId(p.getUsersId());
        clubDto.setActivities(activitiesToDtoList(p.getActivities()));
        clubDto.setPhotoUrl(p.getPhotoUrl());
        return clubDto;
    }
    @Override
    public List<ClubDto> getClubs() {
        List<Club> data = clubRepository.findAll();
        List<ClubDto> clubDtos = data.stream()
                .map(club -> {
                    ClubDto clubDto = new ClubDto();
                    clubDto.setId(club.getId());
                    clubDto.setName(club.getName());
                    clubDto.setContent(club.getContent());
                    clubDto.setCommunication(club.getCommunication());
                    clubDto.setUsersId(club.getUsersId());
                    clubDto.setActivities(activitiesToDtoList(club.getActivities()));
                    clubDto.setPhotoUrl(club.getPhotoUrl());
                    return clubDto;
                })
                .collect(Collectors.toList());

        return clubDtos;
    }
    @Override
    public Boolean delete(Long id) {
        clubRepository.deleteById(id);
        return true;
    }
    @Override
    public ClubDto update(Long id, ClubDto clubDto) {
        Club clubdb = clubRepository.getOne(id);
        if (clubdb == null)
            throw new IllegalArgumentException("Testplan Does Not Exist ID:" + id);
        clubdb.setName(clubDto.getName());
        clubdb.setContent(clubDto.getContent());
        clubdb.setCommunication(clubDto.getCommunication());
        clubdb.setActivities(dtosToActivity(clubDto.getActivities()));
        clubdb.setPhotoUrl(clubDto.getPhotoUrl());
        clubRepository.save(clubdb);
        ClubDto updated = new ClubDto();
        updated.setId(clubDto.getId());
        updated.setCommunication(clubDto.getCommunication());
        updated.setContent(clubDto.getContent());
        updated.setName(clubDto.getName());
        updated.setActivities(clubDto.getActivities());
        updated.setPhotoUrl(clubDto.getPhotoUrl());
        return updated;

    }
    @Override
    public ClubDto addUserToClub(Long userId , Long clubId){
        Club club = clubRepository.getReferenceById(clubId);
        club.getUsersId().add(userId);
        Club d = clubRepository.save(club);
        ClubDto clubDto = new ClubDto();
        clubDto.setName(d.getName());
        clubDto.setId(d.getId());
        clubDto.setCommunication(d.getCommunication());
        clubDto.setContent(d.getContent());
        clubDto.setUsersId(d.getUsersId());
        clubDto.setActivities(activitiesToDtoList(d.getActivities()));
        clubDto.setPhotoUrl(d.getPhotoUrl());
        return clubDto;

    }
    @Override
    public ClubDto removeUserFromClub(Long userId , Long clubId){
        Club club = clubRepository.getReferenceById(clubId);
        club.getUsersId().remove(userId);
        Club d = clubRepository.save(club);
        ClubDto clubDto = new ClubDto();
        clubDto.setName(d.getName());
        clubDto.setId(d.getId());
        clubDto.setCommunication(d.getCommunication());
        clubDto.setContent(d.getContent());
        clubDto.setUsersId(d.getUsersId());
        clubDto.setActivities(activitiesToDtoList(d.getActivities()));
        clubDto.setPhotoUrl(d.getPhotoUrl());
        return clubDto;

    }
    @Override
    public List<ClubDto> getFiltered(Long userId) {
        List<Club> data = clubRepository.findAll();

        List<Club> filtered = data.stream()
                .filter(club -> club.getUsersId().contains(userId))
                .collect(Collectors.toList());


        List<ClubDto> clubDtos = filtered.stream()
                .map(club -> {
                    ClubDto clubDto = new ClubDto();
                    clubDto.setId(club.getId());
                    clubDto.setName(club.getName());
                    clubDto.setContent(club.getContent());
                    clubDto.setCommunication(club.getCommunication());
                    clubDto.setUsersId(club.getUsersId());
                    clubDto.setActivities(activitiesToDtoList(club.getActivities()));
                    clubDto.setPhotoUrl(club.getPhotoUrl());
                    return clubDto;
                })
                .collect(Collectors.toList());

        return clubDtos;
    }

    public List<ActivityDto> activitiesToDtoList(List<Activity> activities) {
        List<ActivityDto> dtos = new ArrayList<>();

        for (Activity activity : activities) {
            ActivityDto dto = new ActivityDto();
            dto.setId(activity.getId());
            dto.setName(activity.getName());
            dto.setPlace(activity.getPlace());
            dto.setDate(activity.getDate());
            dto.setContent(activity.getContent());
            dto.setClub(activity.getClub());
            dtos.add(dto);
        }

        return dtos;
    }

    public List<Activity> dtosToActivity(List<ActivityDto> dtos) {
        List<Activity> acts = new ArrayList<>();

        for (ActivityDto dto : dtos) {
            Activity activity = new Activity();
            activity.setId(dto.getId());
            activity.setName(dto.getName());
            activity.setPlace(dto.getPlace());
            activity.setDate(dto.getDate());
            activity.setContent(dto.getContent());
            activity.setClub(dto.getClub());
            acts.add(activity);
        }

        return acts;
    }


}
