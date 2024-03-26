package unigram.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AnnouncementDto {
    private Long id ;
    private String title;
    private Date announcementDate;
    private String content;
    private String photoUrl;

}
