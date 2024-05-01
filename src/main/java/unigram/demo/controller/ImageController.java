package unigram.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unigram.demo.service.impl.ImageServiceImpl;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageServiceImpl service;

    @PostMapping("/{clubid}")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file ,@PathVariable(value = "clubid", required = true) Long clubid) throws IOException {
        System.out.println("Club id : " +clubid);
        String uploadImage = service.uploadImage(file,clubid);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/{clubid}")
    public ResponseEntity<?> downloadImage(@PathVariable(value = "clubid", required = true) Long clubid){
        byte[] imageData=service.downloadClubImage(clubid);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

    @PostMapping("/activity/{activityId}")
    public ResponseEntity<?> uploadImageActivity(@RequestParam("image") MultipartFile file ,@PathVariable(value = "activityId", required = true) Long activityid) throws IOException {

        String uploadImage = service.uploadImageActivity(file,activityid);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<?> downloadImageActivity(@PathVariable(value = "activityId", required = true) Long activityid){
        byte[] imageData=service.downloadActivityImage(activityid);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }
    @PostMapping("/announcement/{announcementId}")
    public ResponseEntity<?> uploadImageAnnouncement(@RequestParam("image") MultipartFile file ,@PathVariable(value = "announcementId", required = true) Long announcementid) throws IOException {

        String uploadImage = service.uploadImageAnnouncement(file,announcementid);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/announcement/{announcementId}")
    public ResponseEntity<?> downloadImageAnnouncement(@PathVariable(value = "announcementId", required = true) Long announcementid){
        byte[] imageData=service.downloadAnnouncementImage(announcementid);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }
}
