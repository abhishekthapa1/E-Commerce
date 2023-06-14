package project.ecommerce.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.ecommerce.component.FileStoreUtils;
import project.ecommerce.dto.ProductDto;
import project.ecommerce.dto.SearchItem;
import project.ecommerce.service.CategoryService;
import project.ecommerce.service.ProductService;

import java.util.List;

import static project.ecommerce.controller.CartController.checkedList;

@Controller
@RequestMapping("/")
public class HomeController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public HomeController(CategoryService categoryService, ProductService productService, FileStoreUtils fileStoreUtils) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.fileStoreUtils = fileStoreUtils;
    }

    private final FileStoreUtils fileStoreUtils;

    @GetMapping
    public String indexPage(Model model) {
        int productsNum = checkedList.size();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // User is authenticated
            String username = authentication.getName(); // Get the username
            // Perform actions for authenticated user
            model.addAttribute("username", username);
        }

        if (!model.containsAttribute("allProducts")) {
            List<ProductDto> allproducts = productService.getALlPost();
            model.addAttribute("allProducts", allproducts);

        }
        model.addAttribute("SearchItem", new SearchItem());
        if (model.getAttribute("productsNum") == null) {
            model.addAttribute("productsNum", productsNum);
        }
        return "mainPage";
    }
}
