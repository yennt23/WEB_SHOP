package Ph28001.ASM.Controller;

import Ph28001.ASM.Entity.HoaDon;
import Ph28001.ASM.Repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BillController {
    @Autowired
    HoaDonRepository hoaDonRepository;
    @GetMapping(value = "/hoadon/{id}")
    public String billDetail(@PathVariable("id") Integer id, Model model){
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        model.addAttribute("hoaDon", hoaDon);
        return "chiTietHoaDon";
    }
}
