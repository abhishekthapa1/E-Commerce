package project.ecommerce.controller;

import org.apache.tika.exception.TikaException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.ecommerce.component.FileStoreUtils;
import project.ecommerce.dto.CategoryDto;
import project.ecommerce.dto.ProductDto;
import project.ecommerce.model.Category;
import project.ecommerce.model.Product;
import project.ecommerce.service.CategoryService;
import project.ecommerce.service.ProductService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {


    private final CategoryService categoryService;
    private final ProductService productService;
    private final FileStoreUtils fileStoreUtils;

    public AdminController(CategoryService categoryService, ProductService productService, FileStoreUtils fileStoreUtils) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.fileStoreUtils = fileStoreUtils;
    }


    @GetMapping
    public String adminPage(Model model) {
        List<ProductDto> allproducts = productService.getALlPost();
        model.addAttribute("allProducts",allproducts);
        return "adminHomePage";
    }

    @GetMapping("/addCategory")
    public String viewCategoryList(Model model) {
       model.addAttribute("category",new CategoryDto());
        List<CategoryDto> categoryDtoList = categoryService.getAllCategory();
        model.addAttribute("categoryList",categoryDtoList);

        return "/internal/adminCreateCategory";
    }

    @PostMapping("/createCategory")
    public String createCategory(@ModelAttribute("CategoryDto") CategoryDto categoryDto, RedirectAttributes redirectAttributes) {
        categoryDto = categoryService.create(categoryDto);
        String success_message = "Category Created Successfully";
        redirectAttributes.addFlashAttribute("success_message", success_message);
        return "redirect:/admin/addCategory";
    }


    @GetMapping("/addProduct")
    public String addProduct(Model model){
        List<CategoryDto> categoryDtoList = categoryService.getAllCategory();
        model.addAttribute("categoryList",categoryDtoList);
        if(model.getAttribute("productDto")==null) {
            model.addAttribute("productDto", new ProductDto());
        }
        return "/internal/adminCreateProduct";
    }
    @PostMapping("/createPost")
    public String createProductPost(@ModelAttribute ProductDto productDto , RedirectAttributes redirectAttributes) throws IOException, TikaException {
        String type = fileStoreUtils.extensionvalidation(productDto.getMultipartFile());
        if (type.equals("image/jpeg") || type.equals("image/png")) {
            productService.createPost(productDto);
            String success_message = "Post Created Successfully";
            redirectAttributes.addFlashAttribute("success_message", success_message);
        } else {
            String message = "Failed! File type should be jpg or png or jpeg";
            redirectAttributes.addFlashAttribute("message", message);
        }
        return "redirect:/admin/addProduct";

    }
    @GetMapping("/viewProduct")
    public String viewAllProducts(){
        List<ProductDto> allproducts = productService.getALlPost();
        return allproducts.toString();

    }
    @GetMapping("/view/{id}")
    public String viewProduct(@PathVariable("id") Integer id, Model model) throws IOException {
        ProductDto productDto = productService.getSinglePost(id);
        model.addAttribute("post",productDto);
        return "/internal/adminSinglePostView";
    }
    @RequestMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") Integer id) {
        productService.deletePost(id);
        return "redirect:/admin";
    }
    @RequestMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) {
        categoryService.delete(id);
        return "redirect:/admin";
    }
    @GetMapping("/edit/{id}")
    public String UpdateProduct(@PathVariable("id") Integer id,RedirectAttributes redirectAttributes) throws IOException {
        redirectAttributes.addFlashAttribute("productDto",productService.getSinglePost(id));
        return "redirect:/admin/addProduct";

    }
}
