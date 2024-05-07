package unigram.demo.service.impl;

import org.checkerframework.checker.units.qual.A;
import unigram.demo.dao.entity.Activity;
import unigram.demo.dao.entity.Club;
import unigram.demo.dao.entity.User;
import unigram.demo.dto.ActivityDto;
import unigram.demo.dto.ActivityEditDto;
import unigram.demo.dto.ClubDto;
import unigram.demo.repository.ActivityRepository;
import unigram.demo.repository.ClubRepository;
import unigram.demo.repository.UserRepository;
import unigram.demo.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private UserRepository userRepository;



    @Override
    public ActivityDto save(ActivityEditDto activityDto) {

        Club club = clubRepository.getReferenceById(activityDto.getClubid());
        Activity activity = new Activity();
        activity.setName(activityDto.getName());
        activity.setContent(activityDto.getContent());
        activity.setPlace(activityDto.getPlace());
        activity.setDate(activityDto.getDate());
        activity.setClub(club);
        activity.setUsersId(new ArrayList<>());
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
        dto.setUsersId(new ArrayList<>());
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
        dto.setUsersId(n.getUsersId());
        return dto;
    }



    @Override
    public List<ActivityDto> getPastActivities() {
        LocalDate today = LocalDate.now();

        // Tüm aktiviteleri al
        List<Activity> allActivities = activityRepository.findAll();

        // Bugünün tarihinden önceki aktiviteleri filtrele
        List<Activity> pastActivities = allActivities.stream()
                .filter(activity -> activity.getDate().isBefore(today))
                .collect(Collectors.toList());

        // Dto'ya dönüştür ve listeyi döndür
        return activitiesToDtoList(pastActivities);
    }

    @Override
    public List<ActivityDto> getActivities() {
        LocalDate today = LocalDate.now();
        List<Activity> data = activityRepository.findAll();
        List<Activity> availableActivities = data.stream()
                .filter(activity -> activity.getDate().isAfter(today) || activity.getDate().isEqual(today))
                .collect(Collectors.toList());
        return activitiesToDtoList(availableActivities);
    }
    @Override
    public List<ActivityDto> getAllActivities() {
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

        Club clubDb = clubRepository.getOne(activityDto.getClubid());
        if (clubDb == null)
            throw new IllegalArgumentException("Club Does Not Exist ID:" + id);

        activityDb.setName(activityDto.getName());
        activityDb.setDate(activityDto.getDate());
        activityDb.setContent(activityDto.getContent());
        activityDb.setPlace(activityDto.getPlace());
        activityDb.setClub(clubDb);
        activityDb.setPhotoUrl(activityDto.getPhotoUrl());
        Activity n = activityRepository.save(activityDb);
        addClubActivities((long) n.getClub().getId(), n);
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
    private void addClubActivities(Long clubid,Activity activity){
        Club t = clubRepository.getReferenceById(clubid);
        List<Activity> activityList = t.getActivities();
        activityList.add(activity);
        clubRepository.save(t);
    }


    @Override
    public ActivityDto addUserToActivity(Long userId , Long activityId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            // Kullanıcı bulunamadı, hata fırlat veya uygun bir işlem yap
            // Burada bir hata fırlatmak yerine uygun bir işlem yapabilirsiniz.
            // Örneğin, uygun bir hata durumu oluşturabilir ve bu hata durumunu döndürebilirsiniz.
            throw new IllegalArgumentException("Kullanıcı bulunamadı: " + userId);
        }
        Activity activity = activityRepository.getReferenceById(activityId);
        activity.getUsersId().add(userId);
        Activity d = activityRepository.save(activity);
        ActivityDto activityDto = new ActivityDto();
        activityDto.setName(d.getName());
        activityDto.setId(d.getId());
        activityDto.setClub(d.getClub());
        activityDto.setContent(d.getContent());
        activityDto.setUsersId(d.getUsersId());
        activityDto.setDate(d.getDate());
        activityDto.setPlace(d.getPlace());
        activityDto.setPhotoUrl(d.getPhotoUrl());
        return activityDto;
    }


    @Override
    public ActivityDto removeUserFromActivity(Long userId , Long activityId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            // Kullanıcı bulunamadı, hata fırlat veya uygun bir işlem yap
            // Burada bir hata fırlatmak yerine uygun bir işlem yapabilirsiniz.
            // Örneğin, uygun bir hata durumu oluşturabilir ve bu hata durumunu döndürebilirsiniz.
            throw new IllegalArgumentException("Kullanıcı bulunamadı: " + userId);
        }
        Activity activity = activityRepository.getReferenceById(activityId);
        activity.getUsersId().remove(userId);
        Activity d = activityRepository.save(activity);
        ActivityDto activityDto = new ActivityDto();
        activityDto.setName(d.getName());
        activityDto.setId(d.getId());
        activityDto.setClub(d.getClub());
        activityDto.setContent(d.getContent());
        activityDto.setUsersId(d.getUsersId());
        activityDto.setDate(d.getDate());
        activityDto.setPlace(d.getPlace());
        activityDto.setPhotoUrl(d.getPhotoUrl());
        return activityDto;

    }

    @Override
    public List<ActivityDto> getFiltered(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            // Kullanıcı bulunamadı, hata fırlat veya uygun bir işlem yap
            // Burada bir hata fırlatmak yerine uygun bir işlem yapabilirsiniz.
            // Örneğin, uygun bir hata durumu oluşturabilir ve bu hata durumunu döndürebilirsiniz.
            throw new IllegalArgumentException("Kullanıcı bulunamadı: " + userId);
        }
        List<Activity> data = activityRepository.findAll();

        List<Activity> filtered = data.stream()
                .filter(activity -> activity.getUsersId().contains(userId))
                .collect(Collectors.toList());


        List<ActivityDto> activityDtos = activitiesToDtoList(filtered);

        return activityDtos;
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
            dto.setUsersId(activity.getUsersId());
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
        dto.setUsersId(activity.getUsersId());
        return dto;

    }


}
