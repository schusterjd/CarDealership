/**
 * @author Jaden Schuster - jdschuster
 * CIS175 - Spring 2021
 * Apr 10, 2021
 */
package dmacc.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import dmacc.beans.Cars;
import dmacc.beans.Options;
import dmacc.beans.Orders;
import dmacc.repository.CarsRepository;
import dmacc.repository.OptionsRepository;
import dmacc.repository.OrdersRepository;
import dmacc.repository.CustomersRepository;

@Controller
public class ManagerController {

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
	public static String addNewCar(Model model) {
		
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
	@GetMapping("/manager/addImage/{id}") 
	public String addImage(@PathVariable("id") long id, Model model) { 
		Cars c = carRepo.findById(id).orElse(null); 
		model.addAttribute("car", c); 
		return "addImageView"; 
	}
	@PostMapping("/manager/addImage/{id}")
	public String addImage(@RequestParam("file") MultipartFile file,
						   @PathVariable("id") long id, Model model) {
		if (file.isEmpty()) { //Do nothing if file is empty
			return viewAllCars(model); 
		}
		String encoded = "";
		String clientID = "";
		String imageLoc = ""; 
		try { 
			Properties prop = new Properties(); 
			String fileName = "src/main/resources/apikey.txt"; 
			InputStream is = new FileInputStream("src/main/resources/apikey.txt"); 
			prop.load(is); 
			clientID = prop.getProperty("ClientID"); 
		}
		catch (FileNotFoundException e) { 
			e.printStackTrace();
		}
		catch (IOException e) { 
			e.printStackTrace();
		}
		try {
			byte[] byteArr = file.getBytes();
			encoded = Base64.getEncoder().encodeToString(byteArr); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try { 
			URL url = new URL("https://api.imgur.com/3/image"); 
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
			String data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(encoded, "UTF-8"); 
			conn.setDoOutput(true); 
			conn.setDoInput(true); 
			conn.setRequestMethod("POST"); 
			conn.setRequestProperty("Authorization", "Client-ID " + clientID); 
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
			conn.connect();
			StringBuilder stb = new StringBuilder(); 
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
			wr.write(data);
			wr.flush(); 
			JsonReaderFactory fact = Json.createReaderFactory(null); 
			JsonReader read = fact.createReader(conn.getInputStream());
			JsonObject fullJson = (JsonObject) read.read(); 
			JsonObject obj = fullJson.getJsonObject("data");
			imageLoc = obj.getString("link"); 
		}catch (Exception e) { 
			e.printStackTrace(); 
		}
		Cars c = carRepo.findById(id).orElse(null);
		if (c == null) { // can't add image location if car is null
			return viewAllCars(model); 
		}
		c.setImageLoc(imageLoc); 
		carRepo.save(c); 
		return viewAllCars(model);
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
		List<Orders> orders = ordRepo.findAll(); 
		double total = 0; 
		for (Orders order : orders) { 
			total += Double.valueOf(order.getCar().getCarPrice()); 
		}
		model.addAttribute("total", total); 
		model.addAttribute("orders", orders);
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

	
	
		
}
