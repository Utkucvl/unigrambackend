package unigram.demo.service;
import unigram.demo.dao.entity.Club;
import unigram.demo.dao.entity.User;
import unigram.demo.dto.UserDtoWithName;
import unigram.demo.repository.ClubRepository;
import unigram.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private ClubRepository clubRepository;



    public UserService(UserRepository userRepository , ClubRepository clubRepository) {
        this.userRepository = userRepository;
        this.clubRepository = clubRepository;
    }

    public List<UserDtoWithName> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    UserDtoWithName userDtoWithName = new UserDtoWithName();
                    userDtoWithName.setId(user.getId());
                    userDtoWithName.setUserName(user.getUserName());
                    return userDtoWithName;
                })
                .collect(Collectors.toList());
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User createOneUser(User user) {
        return userRepository.save(user);
    }

    public User getOneUserByUserName(String userName) {
        return (userRepository.findByUserName(userName));
    }



}
