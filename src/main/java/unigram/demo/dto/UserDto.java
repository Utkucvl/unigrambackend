package unigram.demo.dto;

import unigram.demo.dao.entity.Club;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private int id;
    private String userName;
    private List<Club> clubs;

}
