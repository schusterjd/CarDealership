/**
 * @author Jaden Schuster - jdschuster
 * CIS175 - Spring 2021
 * Apr 10, 2021
 */
package dmacc.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.Cars;
import dmacc.beans.Customers;
import dmacc.beans.Options;
import dmacc.beans.Orders;
import dmacc.repository.CarsRepository;
import dmacc.repository.OptionsRepository;
import dmacc.repository.OrdersRepository;
import dmacc.repository.CustomersRepository;

@Controller
public class WebController {

	@Autowired
	CarsRepository carRepo;
	@Autowired
	OptionsRepository optRepo; 
	@Autowired
	OrdersRepository ordRepo;
	@Autowired
	CustomersRepository cusRepo;

	
	//Default view
	@GetMapping({"/"})
	public String defaultView() {
		return "defaultView";
	}
	
	
	//Manager view
	@GetMapping({"/manager"})
	public String managerView() {
		return "managerView";
	}
	
	@GetMapping({"/manager/viewAll"})
	public String viewAllCars(Model model) {
		
		if(carRepo.findAll().isEmpty()) {
			return addNewCar(model);
		}
		model.addAttribute("cars", carRepo.findAll());
		return "allCars";
		
	}
	
	@GetMapping("/manager/addNewCar")
	public String addNewCar(Model model) {
		
		Cars s = new Cars();
		model.addAttribute("newCar", s);
		return "addNewCar";
		
	}
	
	@PostMapping("/manager/addNewCar")
	public String addNewCar(@ModelAttribute Cars s, Model model) {
		
		carRepo.save(s);
		return viewAllCars(model);
		
	}
	
	@GetMapping("/manager/edit/{id}")
	public String showUpdateCar(@PathVariable("id") long id, Model model) {
		Cars c = carRepo.findById(id).orElse(null);
		model.addAttribute("newCar", c);
		return "addNewCar";
		
	}
	
	@PostMapping("/manager/update/{id}")
	public String reviseCar(Cars c, Model model) {
		
		carRepo.save(c);
		return viewAllCars(model);
		
	}
	
	@GetMapping("/manager/delete/{id}")
	public String deleteCar(@PathVariable("id") long id, Model model) {
		
		Cars c = carRepo.findById(id).orElse(null);
		//was getting an error for null when trying to delete a car that hadn't been ordered. This if check will fix that.
		if (!c.isAvailable()) {
			Orders o = ordRepo.findOrderByCarID(c);
			ordRepo.delete(o); 
		}
		
		carRepo.delete(c);
		return viewAllCars(model);
	}
	
	@GetMapping({"/manager/orders"})
	public String ordersView() {
		
		return "ordersView";
		
	}
	
	@GetMapping({"/manager/orders/viewAll"})
	public String viewAllOrders(Model model) {
		
		if(ordRepo.findAll().isEmpty()) {
			return addNewOrder(model);
		}
		model.addAttribute("orders", ordRepo.findAll());
		return "allOrders";
		
	}
	
	@GetMapping("/manager/orders/addNewOrder")
	public String addNewOrder(Model model) {
		
		Orders o = new Orders();
		model.addAttribute("newOrder", o);
		return "addNewOrder";
		
	}
	
	@PostMapping("/manager/orders/addNewOrder")
	public String addNewOrder(@ModelAttribute Orders o, Model model) {
		
		ordRepo.save(o);
		return viewAllOrders(model);
		
	}
	
	@GetMapping("/manager/orders/edit/{id}")
	public String showUpdateOrder(@PathVariable("id") long id, Model model) {
		Orders o = ordRepo.findById(id).orElse(null);
		model.addAttribute("newOrder", o);
		return "addNewOrder";
		
	}
	
	@PostMapping("/manager/orders/update/{id}")
	public String reviseOrder(Orders o, Model model) {
		
		ordRepo.save(o);
		return viewAllOrders(model);
		
	}
	
	@GetMapping("/manager/orders/delete/{id}")
	public String deleteOrder(@PathVariable("id") long id, Model model) {
		
		Orders o = ordRepo.findById(id).orElse(null);
		ordRepo.delete(o);
		return viewAllOrders(model);
		
	}
	
	@GetMapping("/edit/addOption/{id}")
	public String addOption(@PathVariable("id") long id, Model model) { 
		Cars c = carRepo.findById(id).orElse(null); 
		Options o = new Options();
		System.out.println(c.toString());
		model.addAttribute("car", c);
		model.addAttribute("newOption", o); 
		return "addOption"; 
	} 
	@PostMapping("/edit/addOption/{id}")
	public String addOption(@PathVariable("id") long id, @ModelAttribute Options o, Model model) { 
		Cars c = carRepo.findById(id).orElse(null); 
		if (c == null) { 
			return viewAllCars(model); 
		}
	//	c.addOption(o);  ** Commented out this line as it was causing a build error
		o.setCar(c); 
		carRepo.save(c); 
		optRepo.save(o); 
		return viewAllCars(model); 
	}
	@GetMapping("/view/{id}")
	public String viewOptions(@PathVariable("id") long id, Model model) { 
		Cars c = carRepo.findById(id).orElse(null); 
		List<Options> options = c.getOptions();
		model.addAttribute("car", c); 
		model.addAttribute("options", options); 
		return "viewOptions";	
	}
	@GetMapping("/manager/deleteOption/{id}")
	public String deleteOption(@PathVariable("id") long id, Model model ) { 
		
		Options o = optRepo.findById(id).orElse(null);
		Cars c = o.getCar();
		List<Options> carOptions = c.getOptions();
		carOptions.remove(o);
		c.setOptions(carOptions);
		carRepo.save(c);
		optRepo.delete(o);
		return viewAllCars(model); 
		
	}
	@GetMapping("/manager/addOption/{id}")
	public String managerAddOption(@PathVariable("id") long id, Model model) { 
		Cars c = carRepo.findById(id).orElse(null); 
		Options o = new Options(); 
		model.addAttribute("car", c);
		model.addAttribute("newOption", o);
		System.out.println(o.getId());
		return "addOption"; 
	}
	@PostMapping("/manager/addOption/{id}")
	public String managerAddOption(@PathVariable("id") long id, @ModelAttribute Options o, Model model ) { 
		Cars c = carRepo.findById(id).orElse(null); 
		if (c == null) { 
			return viewAllCars(model); 
		}
		Options opt = new Options(); 
		opt.setCar(c);
		opt.setOptionName(o.getOptionName());
		optRepo.save(opt); 
		return viewAllCars(model); 
	}
	@GetMapping("/manager/viewOptions/{id}")
	public String managerViewOptions(@PathVariable("id") long id, Model model) { 
		Cars c = carRepo.findById(id).orElse(null); 
		if (c == null || c.getOptions().isEmpty()) { 
			return viewAllCars(model); 
		}
		model.addAttribute("car", c); 
		model.addAttribute("options", c.getOptions());
		return "viewOptions"; 
	}
	
	@GetMapping("/manager/sales")
	public String salesView() {
		return "salesView";
	}
	
	@GetMapping("/manager/sales/total")
	public String totalSales(Model model) {
		
		if(ordRepo.findAll().isEmpty()) {
			return "noSales";
		}
		model.addAttribute("orders", ordRepo.findAll());
		return "totalSales";
	}
	
	@GetMapping("/manager/sales/viewSale")
	public String viewSale(Model model) {
		
		if(ordRepo.findAll().isEmpty()) {
			return "noSales";
		}
		model.addAttribute("orders", ordRepo.findAll());
		return "viewSale";
		
	}
	
	@GetMapping("/manager/sales/singleOrder/{id}")
	public String singleOrder(@PathVariable("id") long id, Model model) {
		
		Orders o = ordRepo.findById(id).orElse(null);
		model.addAttribute("order", o);
		return "singleOrder";
		
	}
	
	
	
	//Customer view
	@GetMapping({"/customer"})
	public String customerView() {
		return "customerView";
	}
	
	@GetMapping({"/customer/viewAll"})
	public String viewAllCarsCustomer(Model model) {
		
		if(carRepo.findAll().isEmpty()) {
			return addNewCar(model);
		}
		
		model.addAttribute("cars", carRepo.findAvailableCars());
		return "allCarsCustomer";
		
	}
	@GetMapping({"/customer/carOptions/{id}"})
	public String viewCarOptionsCustomer(@PathVariable("id") long id, Model model) { 
		Cars c = carRepo.findById(id).orElse(null); 
		if (c.getOptions().isEmpty()) { 
			return "allCarsCustomer"; 
		}
		model.addAttribute("options", c.getOptions());
		model.addAttribute("car", c); 
		return "carOptionCustomer"; 
	}
	
	@GetMapping("/customer/purchase/{id}")
	public String showPurchaseCar(@PathVariable("id") long id, Model model) {
		Cars c = carRepo.findById(id).orElse(null);
		model.addAttribute("thisCar", c);
		return "purchaseCar";
	}
	
	@PostMapping("/customer/purchase/{id}")
	public String purchaseCar(@PathVariable("id") long id, Model model, String cName, String cLastName, String cPhone) { 
		Cars c = carRepo.findById(id).orElse(null);
		Orders ord = new Orders(); 
		ord.setCar(c);
		
		//save the customer
		Customers cu = new Customers();
		cu.setCName(cName);
		cu.setCLastName(cLastName);
		cu.setCPhone(cPhone);
		cusRepo.save(cu);
		
		ord.setCustomer(cu);
		
		ordRepo.save(ord);
		c.setAvailable(false);
		carRepo.save(c); 
		model.addAttribute("order", ord); 
		System.out.println(cName);
		return "receipt";
	}
	
}
