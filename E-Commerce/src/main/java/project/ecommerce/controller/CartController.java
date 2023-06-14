package project.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.ecommerce.component.FileStoreUtils;
import project.ecommerce.config.UserInfoUserDetails;
import project.ecommerce.dto.ProductDto;
import project.ecommerce.dto.SearchItem;
import project.ecommerce.service.CategoryService;
import project.ecommerce.service.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final FileStoreUtils fileStoreUtils;

    List<Integer> productsAdded = new ArrayList<>();

    public CartController(CategoryService categoryService, ProductService productService, FileStoreUtils fileStoreUtils) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.fileStoreUtils = fileStoreUtils;
    }
    public static List<ProductDto> checkedList = new ArrayList<ProductDto>();
    public static double totalPrice;

    private UserInfoUserDetails userInfoUserDetails;


    @GetMapping("/add/{id}")
    public String itemAdded(@PathVariable Integer id, RedirectAttributes redirectAttributes) throws IOException {
        int productList = id;
        productsAdded.add(productList);
        int productsNum =productsAdded.size()+checkedList.size();
        redirectAttributes.addFlashAttribute("productsNum", productsNum);
        System.out.println(productsAdded);
        for (int i = 0; i < productsAdded.size(); i++) {
            ProductDto productDto = productService.getSinglePost(productsAdded.get(i));
            checkedList.add(productDto);
        }

        productsAdded.clear();
        totalPrice = checkedList.stream()
                .mapToDouble(x -> x.getPrice())
                .sum();
        return "redirect:/";
    }

    @GetMapping("/checkout")
    public String checkoutCart(Model model) throws IOException {

        int cartSize = checkedList.size();
        model.addAttribute("product", checkedList);
        model.addAttribute("productsNum",cartSize);
        model.addAttribute("SearchItem", new SearchItem());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // User is authenticated
            String username = authentication.getName(); // Get the username
            // Perform actions for authenticated user
            model.addAttribute("username",username);
        }


        if(model.getAttribute("totalPrice")==null){
        model.addAttribute("totalPrice",totalPrice);
        }
        return "checkoutPage";
    }
    @GetMapping("/removeItem/{id}")
    public String itemRemoved(@PathVariable Integer id,Model model) throws IOException {
       List<ProductDto> removeItem = checkedList.stream().filter(x->x.getId()==id).toList();
       int itemCount = removeItem.size();
       if(itemCount>1){
           ProductDto itemAt = productService.getSinglePost(id);
            int getIndex = checkedList.indexOf(itemAt);
            checkedList.remove(getIndex);
           System.out.println("more than one item");
       }else {
           System.out.println(removeItem);
           checkedList.removeIf(x -> x.getId() == id);
       }
        return "redirect:/cart/checkout";
    }
    @GetMapping("/removeAll")
    public String emptyCart() throws IOException {
        productsAdded.clear();
        checkedList.clear();
        return "redirect:/cart/checkout";
    }

}
