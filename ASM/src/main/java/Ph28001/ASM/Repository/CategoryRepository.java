package Ph28001.ASM.Repository;

import Ph28001.ASM.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public Category findOneByCategoryId(Integer id);

    public Category findAllBycategoryName(String name);

}
