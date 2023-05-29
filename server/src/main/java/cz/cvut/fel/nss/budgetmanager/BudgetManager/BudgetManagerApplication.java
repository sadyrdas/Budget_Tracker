package cz.cvut.fel.nss.budgetmanager.BudgetManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(
		scanBasePackages = {
				"cz.cvut.fel.nss.budgetmanager.BudgetManager"
		},
		exclude = {SecurityAutoConfiguration.class}
)
public class BudgetManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetManagerApplication.class, args);
	}


}
