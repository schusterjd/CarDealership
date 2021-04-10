/**
 * @author Jaden Schuster - jdschuster
 * CIS175 - Spring 2021
 * Apr 10, 2021
 */
package dmacc.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dmacc.beans.Cars;

@Configuration
public class BeanConfiguration {

	@Bean
	public Cars car() {
		
		Cars bean = new Cars();
		return bean;
		
	}

}
