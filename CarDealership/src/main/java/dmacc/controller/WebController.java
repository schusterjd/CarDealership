/**
 * @author Jaden Schuster - jdschuster
 * CIS175 - Spring 2021
 * Apr 10, 2021
 */
package dmacc.controller;

import java.util.List;
import java.util.Set;

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
	
	@GetMapping({"/", "viewAll"})
	public String viewAllCars(Model model) {
		
		if(carRepo.findAll().isEmpty()) {
			return addNewCar(model);
		}
		model.addAttribute("cars", carRepo.findAll());
		return "allCars";
		
	}
	
	@GetMapping("/addNewCar")
	public String addNewCar(Model model) {
		
		Cars s = new Cars();
		model.addAttribute("newCar", s);
		return "addNewCar";
		
	}
	
	@PostMapping("/addNewCar")
	public String addNewCar(@ModelAttribute Cars s, Model model) {
		
		carRepo.save(s);
		return viewAllCars(model);
		
	}
	
	@GetMapping("/edit/{id}")
	public String showUpdateCar(@PathVariable("id") long id, Model model) {
		Cars c = carRepo.findById(id).orElse(null);
		model.addAttribute("newCar", c);
		return "input";
		
	}
	
	@PostMapping("/update/{id}")
	public String reviseCar(Cars c, Model model) {
		
		carRepo.save(c);
		return viewAllCars(model);
		
	}
	
	@GetMapping("/delete/{id}")
	public String deleteCar(@PathVariable("id") long id, Model model) {
		
		Cars c = carRepo.findById(id).orElse(null);
		carRepo.delete(c);
		return viewAllCars(model);
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
		c.addOption(o); 
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

}
