package Ph28001.ASM.Controller;

import Ph28001.ASM.Entity.*;
import Ph28001.ASM.Repository.CartRepository;
import Ph28001.ASM.Repository.ProductInCartRepository;
import Ph28001.ASM.Repository.ProductRepository;
import Ph28001.ASM.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductInCartRepository productInCartRepository;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/product", method = RequestMethod.GET )
    public String listProduct(Model model, @RequestParam(name="page", required=false) String page,HttpServletRequest request) {
        int currentPage;
        int limit = 2;
        if(page == null) {
            currentPage = 1;
        }
        else {
            currentPage=Integer.parseInt(page);
        }
        Pageable pageable= PageRequest.of(currentPage-1, limit);
        List<Product> productList = productRepository.findAllByOrderByProductIdDesc(pageable);
        model.addAttribute("productList", productList);
        model.addAttribute("page",currentPage);
        model.addAttribute("totalPage", ((int) Math.ceil((productRepository.count()*1.0/limit))));
        String productId = messageSource.getMessage("productId", null, "default message", request.getLocale());
        String productName = messageSource.getMessage("productName", null, "default message", request.getLocale());
        String productPrice = messageSource.getMessage("productPrice", null, "default message", request.getLocale());
        String productBrand = messageSource.getMessage("productBrand", null, "default message", request.getLocale());
        String Img = messageSource.getMessage("Img", null, "default message", request.getLocale());
        String productDescription = messageSource.getMessage("productDescription", null, "default message", request.getLocale());
        String Action = messageSource.getMessage("Action", null, "default message", request.getLocale());
        model.addAttribute("productId", productId);
        model.addAttribute("productName", productName);
        model.addAttribute("productPrice", productPrice);
        model.addAttribute("productBrand", productBrand);
        model.addAttribute("Img", Img);
        model.addAttribute("productDescription", productDescription);
        model.addAttribute("Action", Action);
        return "product";
    }
    @Value("D:\\YenNT")
    private String fileUpdate;

    @RequestMapping(value = "/newProduct")
    public String newProduct(Model model) {
        ProductForm product = new ProductForm();
        model.addAttribute("product", product);
        return "newProduct";
    }

    @PostMapping(value = "/product/saveProduct")
    public String save(@ModelAttribute ProductForm productForm) {
        Product productForm1= new Product();
        productForm1.setProductId(productForm.getProductId());
        productForm1.setProductBrand(productForm.getProductBrand());
        productForm1.setProductName(productForm.getProductName());
        productForm1.setProductPrice(productForm.getProductPrice());
        productForm1.setProductDescription(productForm.getProductDescription());
        MultipartFile multipartFile = productForm.getImg();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(productForm.getImg().getBytes(), new File(new File(this.fileUpdate) + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productForm1.setImg(fileName);
        productRepository.save(productForm1);
        return "redirect:/product";
    }

    @RequestMapping("product/edit")
    public ModelAndView editProduct(@RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView("edit_product");
        Product product = productRepository.findOneByProductId(id);
        ProductForm productForm = new ProductForm();
        productForm.setProductId(product.getProductId());
        productForm.setProductBrand(product.getProductBrand());
        productForm.setProductDescription(product.getProductDescription());
        productForm.setProductName(product.getProductName());
        productForm.setImageUrl(product.getImg());
        productForm.setProductPrice(product.getProductPrice());
        modelAndView.addObject("product", productForm);
        return modelAndView;
    }

    @RequestMapping("product/delete")
    public String deleteProduct(@RequestParam int id) {
        productRepository.deleteById(id);
        return "redirect:/product";
    }

    @GetMapping("product/detail/{productId}")
    public String showProduct(@PathVariable("productId") int id, Model model) {
        Product product = productRepository.findOneByProductId(id);

        model.addAttribute("product", product);
        return "detail_product";
    }
    @GetMapping("product/addCart/{productId}")
    public String addCart(@PathVariable("productId") int id,Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username=((UserDetails) principal).getUsername() ;
        User user=userRepository.findByEmail(username);
        Product product = productRepository.findOneByProductId(id);
        Cart cart = cartRepository.findCardByUser(user);
        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setUser(user);
            cart = cartRepository.save(newCart);
        }
        ProductInCart productInCart = new ProductInCart();
        productInCart.setCart(cart);
        productInCart.setProduct(product);
        productInCart.setQuantity(1);
        productInCart.setHadPaid(false);
        productInCartRepository.save(productInCart);
        return "redirect:/product";
    }


}
