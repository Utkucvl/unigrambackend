package unigram.demo.controller;

import unigram.demo.dto.AnnouncementDto;
import unigram.demo.dto.ClubDto;
import unigram.demo.repository.AnnouncementRepository;
import unigram.demo.repository.ClubRepository;
import unigram.demo.service.impl.AnnouncementServiceImpl;
import unigram.demo.service.impl.ClubServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/announcement")
public class AnnouncementContoller {

    @Autowired
    AnnouncementRepository announcementRepository;
    @Autowired
    AnnouncementServiceImpl announcementServiceImpl;

    @GetMapping()
    public ResponseEntity<List<AnnouncementDto>> getAll() {
        List<AnnouncementDto> data= new ArrayList<>();
        data = announcementServiceImpl.getAnnouncements();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{announcementid}")
    public ResponseEntity<AnnouncementDto> getById(@PathVariable(value = "announcementid", required = true) Long announcementid) {
        try {

            AnnouncementDto announcementDto = announcementServiceImpl.getById(announcementid);
            return ResponseEntity.ok(announcementDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); //return 404, with null body
        }
    }
}
