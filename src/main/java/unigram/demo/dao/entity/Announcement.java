package unigram.demo.dao.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="Announcements")
@Data
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;

    @Column(name = "title")
    String title;

    @Column(name = "Content")
    String Content;

    @Column(name = "PhotoUrl", length = 1000)
    private String photoUrl;

    @Temporal(TemporalType.DATE) // Specify the temporal type
    @Column(name = "AnnouncementDate")
    private Date announcementDate; // Add the announcementDate field
}
