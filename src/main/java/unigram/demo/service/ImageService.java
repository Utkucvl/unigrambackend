package unigram.demo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String uploadImage(MultipartFile file,Long clubId) throws IOException;

    byte[] downloadClubImage(Long clubId);
    String uploadImageActivity(MultipartFile file, Long clubId) throws IOException;
    byte[] downloadActivityImage(Long clubId);

    String uploadImageAnnouncement(MultipartFile file, Long clubId) throws IOException;
    byte[] downloadAnnouncementImage(Long clubId);
}
