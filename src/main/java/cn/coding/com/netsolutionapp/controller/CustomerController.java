package cn.coding.com.netsolutionapp.controller;

import cn.coding.com.netsolutionapp.exception.CustomerNotFoundException;
import cn.coding.com.netsolutionapp.model.Customer;
import cn.coding.com.netsolutionapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.logging.Logger;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger LOGGER = Logger.getLogger(CustomerController.class.getName());

    @Autowired
    private CustomerService customerService;


    @PostMapping("/save")
    public String saveCustomer(Customer customer, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "New Customer has been Saved.");
        customerService.save(customer);
        return "redirect:/customers/list";
    }

    @GetMapping("/list")
    public String customerList(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                               @RequestParam(value = "size", required = false, defaultValue = "10") int size, Model model) {
        model.addAttribute("listCustomers", customerService.getCustomerWithPaginated(pageNumber, size));
        return "customer/customer-list";
    }

    @GetMapping("/new")
    public String showCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/customer-form";
    }

    @GetMapping("/name")
    public String findByName(Customer customer) {
        customerService.getByName(customer.getName());
        return "customer/list";
    }

    @GetMapping("/edit/{id}")
    public String editCustomer(@PathVariable("id") Integer id, Model model, RedirectAttributes reidirect) {
        try {
            Customer customer = customerService.getCustomerById(id);
            model.addAttribute("customer", customer);
            return "customer/customer-form";
        } catch (CustomerNotFoundException exception) {
            reidirect.addFlashAttribute("message", exception.getMessage());
            return "redirect:/customer/list";
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Integer id, RedirectAttributes attribute) {
        try {
            customerService.deleteById(id);
            attribute.addFlashAttribute("message", "Customer Id " + id + " has been Deleted!");
        } catch (Exception e) {
            attribute.addAttribute("message", e.getMessage());
        }
        return "redirect:/customer/list";
    }
}
