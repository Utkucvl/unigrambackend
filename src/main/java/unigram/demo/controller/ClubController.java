package unigram.demo.controller;

import unigram.demo.dao.entity.Club;
import unigram.demo.dto.ClubByUserDto;
import unigram.demo.dto.ClubDto;
import unigram.demo.dto.UserClubDto;
import unigram.demo.repository.ClubRepository;
import unigram.demo.service.impl.ClubServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/club")
public class ClubController {
    @Autowired
    ClubRepository clubRepository;
    @Autowired
    ClubServiceImpl clubServiceImpl;

    @GetMapping()
    public ResponseEntity<List<ClubDto>> getAll() {
        List<ClubDto> data= new ArrayList<>();
        data = clubServiceImpl.getClubs();
        return ResponseEntity.ok(data);
    }


    @GetMapping("/{clubid}")
    public ResponseEntity<ClubDto> getById(@PathVariable(value = "clubid", required = true) Long clubid) {
        try {

            ClubDto clubDto = clubServiceImpl.getById(clubid);
            return ResponseEntity.ok(clubDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); //return 404, with null body
        }
    }
    @PostMapping
    public ResponseEntity<?> createClub(@RequestBody ClubDto clubDto) {
        System.out.println(clubDto);
        try {
            ClubDto newTestplan = clubServiceImpl.save(clubDto);

            return ResponseEntity.created(new URI("club/"+newTestplan.getId())).body(newTestplan);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{clubid}")
    public ResponseEntity<ClubDto> updateTestplan(@PathVariable(value = "clubid", required = true) Long clubid,
                                                       @RequestBody ClubDto clubDto) {
        try {
            clubServiceImpl.update(clubid, clubDto);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{clubid}")
    public ResponseEntity<?> delete(@PathVariable(value = "clubid", required = true) Long clubid) {
        try {
            if(clubid!=null)
            {

                clubServiceImpl.delete(clubid);
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
    @PostMapping("/add")
    public ResponseEntity<?> addUserToClub(@RequestBody UserClubDto userClubDto) {
        try {
            if(userClubDto.getClubId()!=null || userClubDto.getUserId() != null)
            {

                clubServiceImpl.addUserToClub(userClubDto.getUserId(), userClubDto.getClubId());
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
    @PostMapping("/remove")
    public ResponseEntity<?> removeUserFromClub(@RequestBody UserClubDto userClubDto) {
        try {
            if(userClubDto.getClubId()!=null || userClubDto.getUserId() != null)
            {

                clubServiceImpl.removeUserFromClub(userClubDto.getUserId(), userClubDto.getClubId());
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
    @GetMapping("/filter")
    public ResponseEntity<List<ClubDto>> getAllByUserId(@RequestBody ClubByUserDto clubByUserDto) {
        List<ClubDto> data= new ArrayList<>();
        data = clubServiceImpl.getFiltered(clubByUserDto.getUserId()); // hata burda
        return ResponseEntity.ok(data);
    }



}
