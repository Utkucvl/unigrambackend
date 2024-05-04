package unigram.demo.service;

import unigram.demo.dao.entity.Image;
import unigram.demo.dto.ClubDto;
import unigram.demo.dto.ImageDto;

public interface BaseImageService {
    Image saveImage(ImageDto imageDTO);
    Image getImageById(Long id);
    String saveImageToAnnouncement(ImageDto imageDto, Long announcementId);
    String saveImageToActivity(ImageDto imageDto, Long activityId);
    String saveImageToClub(ImageDto imageDto, Long clubId);

    Boolean delete(Long id);

    ImageDto update(Long id, ImageDto imageDto);
    ImageDto downloadClubImage(Long clubId);
    ImageDto downloadAnnouncementImage(Long announcementId);
    ImageDto downloadActivityImage(Long activityId);






}
