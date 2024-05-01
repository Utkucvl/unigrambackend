package unigram.demo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.Date;

@Data
public class ActivityEditDto {
    private int id;
    private String name;
    private String place;
    private LocalDate date;
    private String content;
    private String photoUrl;
    @NotNull
    private Long clubid;


}
