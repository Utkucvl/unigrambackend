package unigram.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import unigram.demo.dao.entity.*;
import unigram.demo.dto.ClubDto;
import unigram.demo.dto.ImageDto;
import unigram.demo.repository.ActivityRepository;
import unigram.demo.repository.AnnouncementRepository;
import unigram.demo.repository.BaseImageRepository;
import unigram.demo.repository.ClubRepository;
import unigram.demo.service.BaseImageService;
import unigram.demo.util.ImageUtils;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BaseImageServiceImpl implements BaseImageService {
    @Autowired
    private BaseImageRepository imageRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Override
    public Image saveImage(ImageDto imageDTO) {
        Image image = new Image();
        image.setName(imageDTO.getName());
        image.setBase64Image(imageDTO.getBase64Image());
        return imageRepository.save(image);
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public String saveImageToAnnouncement(ImageDto imageDto,Long announcementId) {
        Image image = new Image();
        image.setName(imageDto.getName());
        image.setBase64Image(imageDto.getBase64Image());
        imageRepository.save(image);
        if (image != null) {
            // Veritabanında aktiviteyi bul
            Optional<Announcement> optionalAnnouncement = announcementRepository.findById(announcementId);

            // Aktiviteyi bulamazsanız hata işle
            if (!optionalAnnouncement.isPresent()) {
                return "Announcement Not Found";
            }

            // Aktiviteyi alın
            Announcement announcement = optionalAnnouncement.get();

            // Aktivitenin imageId'sini kaydedilen resmin ID'si ile güncelle
            announcement.setBaseId(image.getId());

            // Aktiviteyi veritabanına kaydet
            announcementRepository.save(announcement);


        }
        return "File uploaded successfully: " + image.getName() + ", and announcement image updated.";
    }

    @Override
    public String saveImageToActivity(ImageDto imageDto, Long activityId) {
        Image image = new Image();
        image.setName(imageDto.getName());
        image.setBase64Image(imageDto.getBase64Image());
        imageRepository.save(image);
        if (image != null) {
            // Veritabanında aktiviteyi bul
            Optional<Activity> optionalActivity = activityRepository.findById(activityId);

            // Aktiviteyi bulamazsanız hata işle
            if (!optionalActivity.isPresent()) {
                return "Activity Not Found";
            }

            // Aktiviteyi alın
            Activity activity = optionalActivity.get();

            // Aktivitenin imageId'sini kaydedilen resmin ID'si ile güncelle
            activity.setBaseId(image.getId());

            // Aktiviteyi veritabanına kaydet
            activityRepository.save(activity);


        }
        return "File uploaded successfully: " + image.getName() + ", and activity image updated.";
    }

    @Override
    public String saveImageToClub(ImageDto imageDto, Long clubId) {
        Image image = new Image();
        image.setName(imageDto.getName());
        image.setBase64Image(imageDto.getBase64Image());
        imageRepository.save(image);
        if (image != null) {
            // Veritabanında aktiviteyi bul
            Optional<Club> optionalClub = clubRepository.findById(clubId);

            // Aktiviteyi bulamazsanız hata işle
            if (!optionalClub.isPresent()) {
                return "Club Not Found";
            }

            // Aktiviteyi alın
            Club club = optionalClub.get();

            // Aktivitenin imageId'sini kaydedilen resmin ID'si ile güncelle
            club.setBaseId(image.getId());

            // Aktiviteyi veritabanına kaydet
            clubRepository.save(club);


        }
        return "File uploaded successfully: " + image.getName() + ", and club image updated.";
    }

    @Override
    public Boolean delete(Long id) {
        imageRepository.deleteById(id);
        return true;
    }

    @Override
    public ImageDto update(Long id, ImageDto imageDto) {
        Image imagedb = imageRepository.getOne(id);
        if (imagedb == null)
            throw new IllegalArgumentException("Image Does Not Exist ID:" + id);
        imagedb.setName(imageDto.getName());
        imagedb.setBase64Image(imageDto.getBase64Image());
        imageRepository.save(imagedb);
        ImageDto updated = new ImageDto();
        updated.setName(imagedb.getName());
        updated.setBase64Image(imagedb.getBase64Image());
        return updated;
    }
    @Override
    public ImageDto downloadAnnouncementImage(Long announcementId) {
        // Club ID'sine göre kulübü bul
        Optional<Announcement> optionalAnnouncement = announcementRepository.findById(announcementId);
        // Kulübü bulamazsanız veya kulübün image_id'si null ise hata işle
        if (!optionalAnnouncement.isPresent() || optionalAnnouncement.get().getBaseId() == null) {
            throw new NoSuchElementException("Image not found for announcement with ID: " + announcementId);
        }

        // Kulübü alın
        Announcement announcement = optionalAnnouncement.get();

        // Kulübün image_id'sini kullanarak resmi bul
        Optional<Image> optionalImage = imageRepository.findById(announcement.getBaseId());

        // Resmi bulamazsanız veya resim verisi null ise hata işle
        if (!optionalImage.isPresent() || optionalImage.get() == null) {
            throw new NoSuchElementException("Image not found for announcement with ID: " + announcementId);
        }
        ImageDto dto = new ImageDto();
        dto.setName(optionalImage.get().getName());
        dto.setBase64Image(optionalImage.get().getBase64Image());
        return dto;
    }
    @Override
    public ImageDto downloadActivityImage(Long activityId) {
        // Club ID'sine göre kulübü bul
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);

        // Kulübü bulamazsanız veya kulübün image_id'si null ise hata işle
        if (!optionalActivity.isPresent() || optionalActivity.get().getBaseId() == null) {
            throw new NoSuchElementException("Image not found for announcement with ID: " + activityId);
        }

        // Kulübü alın
        Activity activity = optionalActivity.get();

        // Kulübün image_id'sini kullanarak resmi bul
        Optional<Image> optionalImage = imageRepository.findById(activity.getBaseId());

        // Resmi bulamazsanız veya resim verisi null ise hata işle
        if (!optionalImage.isPresent() || optionalImage.get() == null) {
            throw new NoSuchElementException("Image not found for announcement with ID: " + activityId);
        }
        ImageDto dto = new ImageDto();
        dto.setName(optionalImage.get().getName());
        dto.setBase64Image(optionalImage.get().getBase64Image());
        return dto;
    }
    @Override
    public ImageDto downloadClubImage(Long clubId) {
        // Club ID'sine göre kulübü bul
        Optional<Club> optionalClub = clubRepository.findById(clubId);

        // Kulübü bulamazsanız veya kulübün image_id'si null ise hata işle
        if (!optionalClub.isPresent() || optionalClub.get().getBaseId() == null) {
            throw new NoSuchElementException("Image not found for announcement with ID: " + clubId);
        }

        // Kulübü alın
        Club club = optionalClub.get();

        // Kulübün image_id'sini kullanarak resmi bul
        Optional<Image> optionalImage = imageRepository.findById(club.getBaseId());

        // Resmi bulamazsanız veya resim verisi null ise hata işle
        if (!optionalImage.isPresent() || optionalImage.get() == null) {
            throw new NoSuchElementException("Image not found for announcement with ID: " + clubId);
        }
        ImageDto dto = new ImageDto();
        dto.setName(optionalImage.get().getName());
        dto.setBase64Image(optionalImage.get().getBase64Image());
        return dto;
    }
}
