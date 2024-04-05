package unigram.demo.dto;

import unigram.demo.dao.entity.Club;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class ActivityDto {
    private int id;
    private String name;
    private String place;
    private LocalDate date;
    private String content;
    private String photoUrl;
    private String clubName;
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty("clubid")
    private Club club;
    private List<Long> usersId;
}
