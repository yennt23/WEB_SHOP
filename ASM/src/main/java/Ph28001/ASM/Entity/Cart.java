package Ph28001.ASM.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToMany(mappedBy = "cart")
    private List<ProductInCart> listProduct;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;
}
