package cz.cvut.fel.nss.budgetmanager.Dispatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Dispatcher {

	public static void main(String[] args) {
		SpringApplication.run(Dispatcher.class, args);
	}

}
