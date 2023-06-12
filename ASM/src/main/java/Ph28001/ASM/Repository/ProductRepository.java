package Ph28001.ASM.Repository;

import Ph28001.ASM.Entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findOneByProductId(Integer id);
    List<Product> findAllByOrderByProductIdDesc(Pageable pageable);
}
