package unigram.demo.repository;
import unigram.demo.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);
}
