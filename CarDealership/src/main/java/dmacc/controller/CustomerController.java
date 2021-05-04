package dmacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.Cars;
import dmacc.beans.Customers;
import dmacc.beans.Orders;
import dmacc.repository.CarsRepository;
import dmacc.repository.CustomersRepository;
import dmacc.repository.OptionsRepository;
import dmacc.repository.OrdersRepository;

@Controller
public class CustomerController {

	@Autowired
	CarsRepository carRepo;
	@Autowired
	OptionsRepository optRepo; 
	@Autowired
	OrdersRepository ordRepo;
	@Autowired
	CustomersRepository cusRepo;
	
	//Customer view
	@GetMapping({"/customer"})
	public String customerView() {
		return "customerView";
	}
	
	@GetMapping({"/customer/viewAll"})
	public String viewAllCarsCustomer(Model model) {
		
		if(carRepo.findAll().isEmpty()) {
			return ManagerController.addNewCar(model);
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
