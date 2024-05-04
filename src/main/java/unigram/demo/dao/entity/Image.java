package unigram.demo.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    @Column(name = "base64Image", length = 100000)
    private String base64Image;
}
