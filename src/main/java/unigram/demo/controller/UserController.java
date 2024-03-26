package unigram.demo.controller;

import unigram.demo.dao.entity.Club;
import unigram.demo.dao.entity.User;
import unigram.demo.dto.ClubDto;
import unigram.demo.dto.UserClubDto;
import unigram.demo.dto.UserDto;
import unigram.demo.repository.ClubRepository;
import unigram.demo.service.ClubService;
import unigram.demo.service.UserService;
import unigram.demo.service.impl.ClubServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private ClubRepository clubRepository;

    public UserController(UserService userService,ClubServiceImpl clubServiceImpl) {
        this.userService = userService;
        this.clubRepository=clubRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getOneUser(@PathVariable Long userId){
        User user = userService.findById(userId);
        UserDto userDto = new UserDto();
        userDto.setUserName(user.getUserName());
        userDto.setId(user.getId());
       // userDto.setClubs(user.getClubs());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}
