package com.cdamayab.flox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cdamayab.flox")
public class DemoApplication {

	public static void main(String[] args) {

		if ("dev".equals(System.getenv("RUN_MODE"))) {
			System.out.println("--------------------------------------------------------------------------------");
        	System.out.println("---------------------- Running in development mode -----------------------------");
        	System.out.println("--------------------------------------------------------------------------------");
		}
		
		SpringApplication.run(DemoApplication.class, args);
	}

}
