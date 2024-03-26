package unigram.demo.controller;

import unigram.demo.dao.entity.Club;
import unigram.demo.dao.entity.User;
import unigram.demo.dto.AuthDto;
import unigram.demo.dto.UserLoginDto;
import unigram.demo.security.JwtTokenProvider;
import unigram.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserService userService;

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    private PasswordEncoder passwordEncoder;

    AuthController(UserService userService,AuthenticationManager authenticationManager,
                   PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider=jwtTokenProvider;
    }
    @PostMapping("/login")
    public ResponseEntity<AuthDto> login (@RequestBody UserLoginDto userRequest){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userRequest.getUserName(),userRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        User user = userService.getOneUserByUserName(userRequest.getUserName());
        System.out.println(user);
        String accessToken = "Bearer " + jwtToken;
        String message = "You have logged in" ;
        AuthDto authResponse = new AuthDto();
        authResponse.setMessage(message);
        authResponse.setUserId(user.getId());
        authResponse.setAccessToken(accessToken);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<AuthDto> register (@RequestBody UserLoginDto userRequest){
        String message ;
        if(userService.getOneUserByUserName(userRequest.getUserName()) != null){
            message = "Username is alredy in use";
            AuthDto authResponse = new AuthDto();
            authResponse.setMessage(message);
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }

        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User user = new User();
        user.setUserName(userRequest.getUserName());
        user.setPassword(userRequest.getPassword());
        userService.createOneUser(user);
        message = "User successfully has been created";
        AuthDto authResponse = new AuthDto();
        authResponse.setMessage(message);
        authResponse.setUserId(user.getId());
        return new ResponseEntity<>( authResponse, HttpStatus.CREATED);

    }

    }


