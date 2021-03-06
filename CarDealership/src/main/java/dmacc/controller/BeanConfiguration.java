/**
 * @author Jaden Schuster - jdschuster
 * CIS175 - Spring 2021
 * Apr 10, 2021
 */
package dmacc.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dmacc.beans.Cars;
import dmacc.beans.Employees;
import dmacc.beans.Managers;
import dmacc.beans.Options;

@Configuration
public class BeanConfiguration {

	@Bean
	public Cars car() {
		
		Cars bean = new Cars();
		return bean;
		
	}

	@Bean
	public Options options() { 
		Options option = new Options(); 
		return option; 
	}

	public Managers manager() {
		Managers bean = new Managers();
		return bean;
	}

	public Employees employee() {
		Employees bean = new Employees();
		return bean;
				
	}
}
