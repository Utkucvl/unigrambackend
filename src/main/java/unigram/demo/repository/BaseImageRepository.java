package unigram.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unigram.demo.dao.entity.Image;

public interface BaseImageRepository extends JpaRepository<Image, Long> {
}