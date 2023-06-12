package Ph28001.ASM.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nameCustomer;
    private String phone;
    private long totalPay;
    @OneToMany(mappedBy ="hoaDon")
    List<ProductInCart> productInCartLis;
}
