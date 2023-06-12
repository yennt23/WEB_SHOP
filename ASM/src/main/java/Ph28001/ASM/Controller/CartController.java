package Ph28001.ASM.Controller;

import Ph28001.ASM.Entity.*;
import Ph28001.ASM.Repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class CartController {
    @Autowired
    private ProductRepository productService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductInCartRepository productInCartRepository;
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @GetMapping(value = "/cart")
    public String viewCart(Model model) {
        //Lay thong tin user dang dang nhap
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userRepository.findByEmail(username);

        //Lay ra gio hang cua user
        Cart cart = cartRepository.findCardByUser(user);
        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setUser(user);
            cart = cartRepository.save(newCart);
        }
        List<ProductInCart> cartItems = cart.getListProduct();
        List<ProductInCart> cartList = new ArrayList<>();
        for (ProductInCart productInCart: cartItems) {
            if (productInCart.getHadPaid() == false) {
                cartList.add(productInCart);
            }
        }

        model.addAttribute("cartList", cartList);
        return "viewCart";
    }

    @RequestMapping("cart/delete")
    public String deleteCart(@RequestParam int id) {
        productInCartRepository.deleteById(id);
        return "redirect:/cart";
    }

    @PostMapping("cart/update")
    public String updateCart(@RequestParam int id, @RequestParam int quantity) {
        Optional<ProductInCart> productInCart = productInCartRepository.findById(id);
        productInCart.get().setQuantity(quantity);
        productInCartRepository.save(productInCart.get());
        return "redirect:/cart";
    }

    @PostMapping("pay")
    public String payment(@RequestParam("ids") List<Integer> ids, @RequestParam("phone") String phone) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userRepository.findByEmail(username);
        List<ProductInCart> productInCarts = productInCartRepository.findAllById(ids);
        HoaDon hoaDon = new HoaDon();
        hoaDon.setNameCustomer(user.getName());
        hoaDon.setPhone(phone);
        long totalPay = 0;
        hoaDon = hoaDonRepository.save(hoaDon);

        //Tinh tong gia phai thanh toan va chuyen trang thai san pham trong gio hang thanh da thanh toan
        for (ProductInCart productInCart: productInCarts){
            productInCart.setHadPaid(true);
            productInCart.setHoaDon(hoaDon);
            productInCartRepository.save(productInCart);
            totalPay += (productInCart.getProduct().getProductPrice() * productInCart.getQuantity());
        }
        hoaDon.setTotalPay(totalPay);
        hoaDon = hoaDonRepository.save(hoaDon);
        return "redirect:/hoadon/"+hoaDon.getId();
    }

}
