package Ph28001.ASM.Controller;

import Ph28001.ASM.Entity.Category;
import Ph28001.ASM.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String listCategory(Model model){
        List<Category>categoryList = categoryRepository.findAll();
        model.addAttribute("categoryList", categoryList);
        return "category";
    }
    @RequestMapping(value = "newCategory")
    public String newCategory(Map<String, Category>model){
        Category category = new Category();
        model.put("category",category);
        return "newCategory";
    }
    @RequestMapping(value = "category/saveCategory",method = RequestMethod.POST)
    public String save(Category category){
        categoryRepository.save(category);
        return "redirect:/category";
    }

    @RequestMapping("category/edit")
    public ModelAndView editCategory(@RequestParam int id){
        ModelAndView modelAndView = new ModelAndView("edit_category");
        Category category = categoryRepository.findOneByCategoryId(id);
        modelAndView.addObject("category",category);
        return modelAndView;
    }
    @RequestMapping("category/delete")
    public String deleteCategory(@RequestParam int id){
        categoryRepository.deleteById(id);
        return "redirect:/category";
    }
}
