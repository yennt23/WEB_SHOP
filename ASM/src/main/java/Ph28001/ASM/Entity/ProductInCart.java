package Ph28001.ASM.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductInCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "CartId")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "productInCart")

    private Product product;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "productInCartLis")
    private HoaDon hoaDon;

    private Boolean hadPaid=false;
}
