package Ph28001.ASM.Controller;

import Ph28001.ASM.Entity.Cart;
import Ph28001.ASM.Entity.Role;
import Ph28001.ASM.Repository.RoleRepository;
import Ph28001.ASM.Repository.UserRepository;
import Ph28001.ASM.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping(value = "/user")
    public String listUser(Model m){
        User user = userRepository.findByEmail("admin@gmail.com");
        if (user == null) {
            Role role = roleRepository.findByName("ROLE_ADMIN");

            User adminUser = new User();
            adminUser.setEmail("admin@gmail.com");
            adminUser.setName("admin");
            adminUser.setPassword("12345");
            adminUser.setRoles(Collections.singletonList(role));
            userRepository.save(adminUser);
        }
        List<User> listUsers = userRepository.findAll();
        m.addAttribute("listUsers", listUsers);
        return "users";
    }

//    @GetMapping(value = "/login")
//    public ModelAndView login(){
//        ModelAndView mav = new ModelAndView("login");
//        return mav;
//    }

    @RequestMapping(value = "/new")
    public String newUsers(Map<String, User>m){
        User user = new User();
        m.put("user", user);
        return "new";
    }

    @PostMapping(value = "user/save")
    public String save(User user) {
        userRepository.save(user);
        return "redirect:/user";
    }

    @RequestMapping("/edit")
    public ModelAndView editUser(@RequestParam int id){
        ModelAndView modelAndView = new ModelAndView("edit_user");
        User user = userRepository.findOneById(id);
        modelAndView.addObject("user",user);
        return modelAndView;

    }
    @RequestMapping("/delete")
    public String deleteUser(@RequestParam int id){
        userRepository.deleteById(id);
        return "redirect:/user";
    }
}
