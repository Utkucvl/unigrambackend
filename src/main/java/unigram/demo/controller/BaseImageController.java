package unigram.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unigram.demo.dao.entity.Image;
import unigram.demo.dto.ClubDto;
import unigram.demo.dto.ImageDto;
import unigram.demo.repository.BaseImageRepository;
import unigram.demo.repository.ClubRepository;
import unigram.demo.service.impl.BaseImageServiceImpl;
import unigram.demo.service.impl.ClubServiceImpl;

import java.net.URI;

@RestController
@RequestMapping("/baseimage")
public class BaseImageController {
    @Autowired
    BaseImageRepository imageRepository;
    @Autowired
    BaseImageServiceImpl imageService;
    @PostMapping
    public ResponseEntity<?> createImage(@RequestBody ImageDto imageDto) {

        try {
            Image image = imageService.saveImage(imageDto);

            return ResponseEntity.created(new URI("club/"+image.getId())).body(image);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PostMapping("/announcement/{announcementId}")
    public ResponseEntity<?> createImageToAnnouncement(@RequestBody ImageDto imageDto, @PathVariable(value = "announcementId", required = true) Long announcementId) {

        try {
            String image = imageService.saveImageToAnnouncement(imageDto,announcementId);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(image);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PostMapping("/activity/{activityId}")
    public ResponseEntity<?> createImageToActivity(@RequestBody ImageDto imageDto, @PathVariable(value = "activityId", required = true) Long activityId) {

        try {
            String image = imageService.saveImageToActivity(imageDto,activityId);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(image);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PostMapping("/club/{clubId}")
    public ResponseEntity<?> createImageToClub(@RequestBody ImageDto imageDto, @PathVariable(value = "clubId", required = true) Long clubId) {

        try {
            String image = imageService.saveImageToClub(imageDto,clubId);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(image);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable Long id) {
        Image image = imageService.getImageById(id);
        if (image == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
    @GetMapping("/announcement/{announcementId}")
    public ResponseEntity<ImageDto> getAnnouncementImageById(@PathVariable Long announcementId) {
        ImageDto image = imageService.downloadAnnouncementImage(announcementId);
        if (image == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<ImageDto> getActivityImageById(@PathVariable Long activityId) {

        ImageDto image = imageService.downloadActivityImage(activityId);
        if (image == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
    @GetMapping("/club/{clubId}")
    public ResponseEntity<ImageDto> getClubImageById(@PathVariable Long clubId) {
        ImageDto image = imageService.downloadClubImage(clubId);
        if (image == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

}
