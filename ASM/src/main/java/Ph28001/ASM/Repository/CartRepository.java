package Ph28001.ASM.Repository;

import Ph28001.ASM.Entity.Cart;
import Ph28001.ASM.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    Cart findCardByUser(User user);
}
