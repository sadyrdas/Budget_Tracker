package cz.cvut.fel.nss.budgetmanager.BudgetManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

//@EntityScan(basePackages = {
//		"cz.cvut.fel.nss.budgetmanager.BudgetManager"
//})
@SpringBootApplication(
		scanBasePackages = {
				"cz.cvut.fel.nss.budgetmanager.BudgetManager"
		}
)
public class BudgetManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetManagerApplication.class, args);
	}

}
