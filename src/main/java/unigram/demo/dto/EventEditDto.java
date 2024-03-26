package unigram.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventEditDto {
    private Long id ;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
}
