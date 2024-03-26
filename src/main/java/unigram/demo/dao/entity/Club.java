package unigram.demo.dao.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="Clubs")
@Data
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id ;

    @Column(name = "Name")
    String Name;

    @Column(name = "Content")
    String Content;

    @Column(name = "Communication")
    String Communication;

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty("activityIds")
    private List<Activity> activities;

    @ElementCollection
    @Column(name = "usersId")
    List<Long> usersId;

    @Column(name = "PhotoUrl", length = 1000)
    private String photoUrl;



}
