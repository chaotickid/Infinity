package com.infinity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@Slf4j
@EnableConfigurationProperties
public class InfinityApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfinityApplication.class, args);
		log.debug("\n**********************************************************************\n" +
				"*                                                                    *\n" +
				"*                                                                    *\n" +
				"*      Started Application Infinity and Handling the traffic         *\n" +
				"*                                                                    *\n" +
				"*                                                                    *\n" +
				"**********************************************************************\"");
	}

}
