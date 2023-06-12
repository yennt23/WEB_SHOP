package Ph28001.ASM.Entity;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductForm {
    private int productId;
    private String productName;
    private Long productPrice;
    private String productBrand;
    @Column(nullable = true,length = 64)
    private MultipartFile img;
    private String productDescription;
    private String imageUrl;
}
