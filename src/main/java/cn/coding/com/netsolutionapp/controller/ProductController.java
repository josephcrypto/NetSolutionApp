package cn.coding.com.netsolutionapp.controller;

import cn.coding.com.netsolutionapp.exception.ProductNotFoundException;
import cn.coding.com.netsolutionapp.model.Product;
import cn.coding.com.netsolutionapp.service.ProductService;
import cn.coding.com.netsolutionapp.ulti.PdfGenerator;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductService productService;


    @GetMapping("/list")
    public String productList(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                              @RequestParam(value = "size", required = false, defaultValue = "10") int size, Model model) {
        model.addAttribute("listProducts", productService.getProductWithPaging(pageNumber, size));
        return "item/product-list";
    }


    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("pageTitle", "Add New Item");
        return "item/product-form";
    }


    @PostMapping("/save")
    public String saveProduct(Product product, RedirectAttributes redirect) {
        logger.info("New Product has been saved to database!. . .");
        productService.save(product);
        redirect.addFlashAttribute("message", "New Product Add Successfully.");
        return "redirect:/product/list";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        try {
            Product product = productService.getById(id);
            model.addAttribute("product", product);
            model.addAttribute("pageTitle", "Edit Product (ID: " + id + ")");
            redirect.addFlashAttribute("message", "Product is updated!.");
            return "item/product-form";
        } catch (ProductNotFoundException exception) {
            redirect.addFlashAttribute("message", exception.getMessage());
            return "redirect:/list";
        }
    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirect) {
        try {
            productService.deleteById(id);
            redirect.addFlashAttribute("message", "Product with Id " + id + " has been deleted!");
        } catch (ProductNotFoundException exception) {
            redirect.addFlashAttribute("message", exception.getMessage());
        }
        return "redirect:/list";
    }


    @GetMapping("/export-to-pdf")
    public void generatedPdfFile(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; fileName=Net_Solution" + currentDateTime + " .pdf";
        response.setHeader(headerKey, headerValue);

        List<Product> listOfProducts = productService.listAll();
        PdfGenerator generator = new PdfGenerator();
        generator.generate(listOfProducts, response);


    }
}
