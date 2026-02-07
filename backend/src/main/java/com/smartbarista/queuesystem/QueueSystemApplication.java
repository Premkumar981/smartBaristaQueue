package com.smartbarista.queuesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QueueSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(QueueSystemApplication.class, args);
	}

	@org.springframework.context.annotation.Bean
	public org.springframework.boot.CommandLineRunner initSchema(javax.sql.DataSource dataSource) {
		return args -> {
			try (java.sql.Connection conn = dataSource.getConnection();
					java.sql.Statement stmt = conn.createStatement()) {
				stmt.execute("ALTER TABLE coffee_order MODIFY COLUMN status VARCHAR(255)");
				stmt.execute("ALTER TABLE coffee_order MODIFY COLUMN drink_type VARCHAR(255)");
				System.out.println("Schema updated successfully via CommandLineRunner");
			} catch (Exception e) {
				System.err.println("Failed to update schema: " + e.getMessage());
			}
		};
	}

}
