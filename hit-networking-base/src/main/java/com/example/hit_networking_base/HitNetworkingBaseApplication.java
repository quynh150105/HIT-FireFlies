package com.example.hit_networking_base;


import com.example.hit_networking_base.config.DotenvApplicationContextInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
@Slf4j
@SpringBootApplication
public class HitNetworkingBaseApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(HitNetworkingBaseApplication.class);
		app.addInitializers(new DotenvApplicationContextInitializer());
		Environment env = app.run(args).getEnvironment();

		String appName = env.getProperty("spring.application.name");
		if (appName != null) {
			appName = appName.toUpperCase();
		}
		String port = env.getProperty("server.port");
		log.info("-------------------------START " + appName
				+ " Application------------------------------");
		log.info("   Application         : " + appName);
		log.info("   Url swagger-ui      : http://localhost:" + port + "/swagger-ui.html");
		log.info("-------------------------START SUCCESS " + appName
				+ " Application------------------------------");
	}

}