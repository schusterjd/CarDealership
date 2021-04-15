/**
 * @author Jaden Schuster - jdschuster
 * CIS175 - Spring 2021
 * Apr 10, 2021
 */
package dmacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.Cars;
import dmacc.beans.Options;
import dmacc.repository.CarsRepository;
import dmacc.repository.OptionsRepository;

@Controller
public class WebController {

	@Autowired
	CarsRepository carRepo;
	@Autowired
	OptionsRepository optRepo; 
	
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
		carRepo.delete(c);
		return viewAllCars(model);
		
	}
	@GetMapping("/manager/deleteOption/{id}")
	public String deleteOption(@PathVariable("id") long id, Model model ) { 
		
		Options o = optRepo.findById(id).orElse(null); 
		optRepo.delete(o);
		return viewAllCars(model); 
		
	}
	@GetMapping("/manager/addOption/{id}")
	public String addOption(@PathVariable("id") long id, Model model) { 
		Cars c = carRepo.findById(id).orElse(null); 
		Options o = new Options(); 
		model.addAttribute("car", c);
		model.addAttribute("newOption", o); 
		return "addOption"; 
	}
	@PostMapping("/manager/addOption/{id}")
	public String addOption(@PathVariable("id") long id, @ModelAttribute Options o, Model model ) { 
		Cars c = carRepo.findById(id).orElse(null); 
		if (c == null) { 
			return viewAllCars(model); 
		}
		c.getOptions().add(o); 
		o.setCar(c);
		carRepo.save(c); 
		optRepo.save(o); 
		return viewAllCars(model); 
	}
	@GetMapping("/manager/viewOptions/{id}")
	public String viewOptions(@PathVariable("id") long id, Model model) { 
		Cars c = carRepo.findById(id).orElse(null); 
		if (c == null || c.getOptions().isEmpty()) { 
			return viewAllCars(model); 
		}
		model.addAttribute("car", c); 
		model.addAttribute("options", c.getOptions());
		return "viewOptions"; 
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
		
		model.addAttribute("cars", carRepo.findAll());
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
	
}
