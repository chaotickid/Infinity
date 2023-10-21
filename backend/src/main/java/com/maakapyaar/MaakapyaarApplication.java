package com.maakapyaar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@Slf4j
@EnableConfigurationProperties
@ComponentScan(basePackages = {"com.*"})
public class MaakapyaarApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaakapyaarApplication.class, args);
		log.debug("\n**********************************************************************\n" +
				"*                                                                    *\n" +
				"*                                                                    *\n" +
				"*      Started Application MaakaPayaar and Handling the traffic      *\n" +
				"*                                                                    *\n" +
				"*                                                                    *\n" +
				"**********************************************************************\"");
	}

}
