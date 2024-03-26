package unigram.demo.repository;

import unigram.demo.dao.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,Long> {
    List<Activity> findByClubId(Long id);
}
