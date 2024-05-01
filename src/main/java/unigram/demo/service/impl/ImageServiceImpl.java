package unigram.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unigram.demo.dao.entity.Activity;
import unigram.demo.dao.entity.Announcement;
import unigram.demo.dao.entity.Club;
import unigram.demo.dao.entity.ImageData;
import unigram.demo.repository.ActivityRepository;
import unigram.demo.repository.AnnouncementRepository;
import unigram.demo.repository.ClubRepository;
import unigram.demo.repository.ImageRepository;
import unigram.demo.service.ImageService;
import unigram.demo.util.ImageUtils;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Override
    public String uploadImage(MultipartFile file, Long clubId) throws IOException {
        System.out.println(file);
        // Resmi veritabanına kaydet
        ImageData imageData = imageRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());

        // Eğer resim başarıyla kaydedilirse devam et
        if (imageData != null) {
            // Veritabanında kulübü bul
            Optional<Club> optionalClub = clubRepository.findById(clubId);

            // Kulübü bulamazsanız hata işle
            if (!optionalClub.isPresent()) {
                return "Club not found!";
            }

            // Kulübü alın
            Club club = optionalClub.get();

            // Kulübün imageId'sini kaydedilen resmin ID'si ile güncelle
            club.setImageId(imageData.getId());

            // Kulübü veritabanına kaydet
            clubRepository.save(club);

            return "File uploaded successfully: " + file.getOriginalFilename() + ", and club image updated.";
        }

        return null;
    }
    @Override
    public byte[] downloadClubImage(Long clubId) {
        // Club ID'sine göre kulübü bul
        Optional<Club> optionalClub = clubRepository.findById(clubId);

        // Kulübü bulamazsanız veya kulübün image_id'si null ise hata işle
        if (!optionalClub.isPresent() || optionalClub.get().getImageId() == null) {
            throw new NoSuchElementException("Image not found for club with ID: " + clubId);
        }

        // Kulübü alın
        Club club = optionalClub.get();

        // Kulübün image_id'sini kullanarak resmi bul
        Optional<ImageData> optionalImageData = imageRepository.findById(club.getImageId());

        // Resmi bulamazsanız veya resim verisi null ise hata işle
        if (!optionalImageData.isPresent() || optionalImageData.get().getImageData() == null) {
            throw new NoSuchElementException("Image not found for club with ID: " + clubId);
        }

        // Resmi veri olarak dön
        return ImageUtils.decompressImage(optionalImageData.get().getImageData());
    }

    @Override
    public String uploadImageAnnouncement(MultipartFile file, Long announcementId) throws IOException {
        // Resmi veritabanına kaydet
        ImageData imageData = imageRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());

        // Eğer resim başarıyla kaydedilirse devam et
        if (imageData != null) {
            // Veritabanında aktiviteyi bul
            Optional<Announcement> optionalAnnouncement = announcementRepository.findById(announcementId);

            // Aktiviteyi bulamazsanız hata işle
            if (!optionalAnnouncement.isPresent()) {
                return "Announcement not found!";
            }

            // Aktiviteyi alın
            Announcement announcement = optionalAnnouncement.get();

            // Aktivitenin imageId'sini kaydedilen resmin ID'si ile güncelle
            announcement.setImageId(imageData.getId());

            // Aktiviteyi veritabanına kaydet
            announcementRepository.save(announcement);

            return "File uploaded successfully: " + file.getOriginalFilename() + ", and announcement image updated.";
        }

        return null;
    }

    @Override
    public byte[] downloadAnnouncementImage(Long announcementId) {
        // Club ID'sine göre kulübü bul
        Optional<Announcement> optionalAnnouncement = announcementRepository.findById(announcementId);

        // Kulübü bulamazsanız veya kulübün image_id'si null ise hata işle
        if (!optionalAnnouncement.isPresent() || optionalAnnouncement.get().getImageId() == null) {
            throw new NoSuchElementException("Image not found for announcement with ID: " + announcementId);
        }

        // Kulübü alın
        Announcement announcement = optionalAnnouncement.get();

        // Kulübün image_id'sini kullanarak resmi bul
        Optional<ImageData> optionalImageData = imageRepository.findById(announcement.getImageId());

        // Resmi bulamazsanız veya resim verisi null ise hata işle
        if (!optionalImageData.isPresent() || optionalImageData.get().getImageData() == null) {
            throw new NoSuchElementException("Image not found for announcement with ID: " + announcementId);
        }

        // Resmi veri olarak dön
        return ImageUtils.decompressImage(optionalImageData.get().getImageData());
    }
    @Override
    public String uploadImageActivity(MultipartFile file, Long activityId) throws IOException {
        // Resmi veritabanına kaydet
        ImageData imageData = imageRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());

        // Eğer resim başarıyla kaydedilirse devam et
        if (imageData != null) {
            // Veritabanında aktiviteyi bul
            Optional<Activity> optionalActivity = activityRepository.findById(activityId);

            // Aktiviteyi bulamazsanız hata işle
            if (!optionalActivity.isPresent()) {
                return "Activity not found!";
            }

            // Aktiviteyi alın
            Activity activity = optionalActivity.get();

            // Aktivitenin imageId'sini kaydedilen resmin ID'si ile güncelle
            activity.setImageId(imageData.getId());

            // Aktiviteyi veritabanına kaydet
            activityRepository.save(activity);

            return "File uploaded successfully: " + file.getOriginalFilename() + ", and activity image updated.";
        }

        return null;
    }

    @Override
    public byte[] downloadActivityImage(Long activityId) {
        // Club ID'sine göre kulübü bul
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);

        // Kulübü bulamazsanız veya kulübün image_id'si null ise hata işle
        if (!optionalActivity.isPresent() || optionalActivity.get().getImageId() == null) {
            throw new NoSuchElementException("Image not found for activity with ID: " + activityId);
        }

        // Kulübü alın
        Activity activity = optionalActivity.get();

        // Kulübün image_id'sini kullanarak resmi bul
        Optional<ImageData> optionalImageData = imageRepository.findById(activity.getImageId());

        // Resmi bulamazsanız veya resim verisi null ise hata işle
        if (!optionalImageData.isPresent() || optionalImageData.get().getImageData() == null) {
            throw new NoSuchElementException("Image not found for activity with ID: " + activityId);
        }

        // Resmi veri olarak dön
        return ImageUtils.decompressImage(optionalImageData.get().getImageData());
    }

}
