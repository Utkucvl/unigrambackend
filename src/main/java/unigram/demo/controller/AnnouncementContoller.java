package unigram.demo.controller;

import org.springframework.web.bind.annotation.*;
import unigram.demo.dto.AnnouncementDto;
import unigram.demo.dto.ClubDto;
import unigram.demo.repository.AnnouncementRepository;
import unigram.demo.repository.ClubRepository;
import unigram.demo.service.impl.AnnouncementServiceImpl;
import unigram.demo.service.impl.ClubServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
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
    @PostMapping
    public ResponseEntity<?> createAnnouncement(@RequestBody AnnouncementDto announcementDto) {
        try {
            AnnouncementDto newAnnouncementDto = announcementServiceImpl.save(announcementDto);

            return ResponseEntity.created(new URI("activity/"+newAnnouncementDto.getId())).body(newAnnouncementDto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{announcementId}")
    public ResponseEntity<AnnouncementDto> updateAnnouncement(@PathVariable(value = "announcementId", required = true) Long announcementId,
                                                              @RequestBody AnnouncementDto announcementDto) {
        try {
            announcementServiceImpl.update(announcementId, announcementDto);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{announcementId}")
    public ResponseEntity<?> delete(@PathVariable(value = "announcementId", required = true) Long announcementId) {
        try {
            if(announcementId!=null)
            {

                announcementServiceImpl.delete(announcementId);
                return ResponseEntity.noContent().build();
            }
            else
            {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

}
