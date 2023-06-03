package cz.cvut.fel.nss.budgetmanager.BudgetManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication(
		scanBasePackages = {
				"cz.cvut.fel.nss.budgetmanager.BudgetManager"
		},
		exclude = {SecurityAutoConfiguration.class}
)
@EnableMongoRepositories
public class BudgetManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetManagerApplication.class, args);
	}


}
