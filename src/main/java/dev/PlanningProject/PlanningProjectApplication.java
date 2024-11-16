package dev.PlanningProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlanningProjectApplication {


//	private static Initializer initiator;
//
//	@Autowired
//	public void setInitialLouder(Initializer initiator) {
//		PlanningProjectApplication.initiator = initiator;
//	}

	public static void main(String[] args) {
		SpringApplication.run(PlanningProjectApplication.class, args);
//		initiator.initial();
	}

}
