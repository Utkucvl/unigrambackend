package unigram.demo.dao.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Activities")
@Data
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id ;

    @Column(name = "Name")
    String Name;

    @Column(name = "Place")
    String Place;

    @Column(name="Date")
    @Temporal(TemporalType.DATE) // Specify the temporal type
    LocalDate date;

    @Column(name = "Content")
    String Content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clubid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty("clubid")
    private Club club;

    @ElementCollection
    @Column(name = "usersId")
    List<Long> usersId;

    @Column(name = "image_id")
    private Long imageId;

    String photoUrl;
    @Column(name = "base_image")
    private Long baseId;


}
