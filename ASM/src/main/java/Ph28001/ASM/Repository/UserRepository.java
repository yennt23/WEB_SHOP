package Ph28001.ASM.Repository;

import Ph28001.ASM.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findOneById(int id);

    public List<User> findAllByName(String name);

    public User deleteById(int id);

    User findByEmail(String email);
}
