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
import dmacc.repository.CarsRepository;

@Controller
public class WebController {

	@Autowired
	CarsRepository repo;
	
	@GetMapping({"/manager/viewAll"})
	public String viewAllCars(Model model) {
		
		if(repo.findAll().isEmpty()) {
			return addNewCar(model);
		}
		
		model.addAttribute("cars", repo.findAll());
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
		
		repo.save(s);
		return viewAllCars(model);
		
	}
	
	@GetMapping("/manager/edit/{id}")
	public String showUpdateCar(@PathVariable("id") long id, Model model) {
		Cars c = repo.findById(id).orElse(null);
		model.addAttribute("newCar", c);
		return "addNewCar";
		
	}
	
	@PostMapping("/manager/update/{id}")
	public String reviseCar(Cars c, Model model) {
		
		repo.save(c);
		return viewAllCars(model);
		
	}
	
	@GetMapping("/manager/delete/{id}")
	public String deleteCar(@PathVariable("id") long id, Model model) {
		
		Cars c = repo.findById(id).orElse(null);
		repo.delete(c);
		return viewAllCars(model);
		
	}

}
