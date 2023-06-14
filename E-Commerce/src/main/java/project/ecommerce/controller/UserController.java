package project.ecommerce.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.ecommerce.component.FileStoreUtils;
import project.ecommerce.dto.ProductDto;
import project.ecommerce.dto.SearchItem;
import project.ecommerce.dto.UserDto;
import project.ecommerce.service.CategoryService;
import project.ecommerce.service.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static project.ecommerce.controller.CartController.checkedList;
import static project.ecommerce.controller.CartController.totalPrice;

@Controller
@RequestMapping("/user")

public class UserController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final FileStoreUtils fileStoreUtils;

    public UserController(CategoryService categoryService, ProductService productService, FileStoreUtils fileStoreUtils) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.fileStoreUtils = fileStoreUtils;
    }


    @GetMapping("error")
    public String error(){
        return "redirect:/";
    }
    @RequestMapping(value="/filter")
    public String filterRecipe(@ModelAttribute("SearchItem") SearchItem searchItem, RedirectAttributes redirectAttributes){
        String filterValue= searchItem.getFilterValue();
        List<ProductDto> productDtoList = productService.searchProduct(filterValue);
        redirectAttributes.addFlashAttribute("allProducts",productDtoList);
        return "redirect:/";
    }
    @GetMapping("/addUser")
    public String register(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // User is authenticated
            String username = authentication.getName(); // Get the username
            // Perform actions for authenticated user
            model.addAttribute("username", username);
        }
        model.addAttribute("user",new UserDto());
        return "/Register";
    }
    @GetMapping("/viewSingle/{id}")
    public String viewSinglePost(@PathVariable Integer id, Model model) throws IOException {
       ProductDto productDto = productService.getSinglePost(id);
       model.addAttribute("product",productDto);
        model.addAttribute("SearchItem", new SearchItem());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // User is authenticated
            String username = authentication.getName(); // Get the username
            // Perform actions for authenticated user
            model.addAttribute("username", username);
        }
        int productsNum = checkedList.size();
        model.addAttribute("productsNum", productsNum);



        return "/ProductDetails";
    }
}
