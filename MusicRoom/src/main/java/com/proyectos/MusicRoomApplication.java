package com.proyectos;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;

//@EnableJpaRepositories("com.proyectos")
@SpringBootApplication
public class MusicRoomApplication {

	public static final Contact DEFAULT_CONTACT = new Contact("Music Room", "CMVB", "veracm@globalhitss.com");

	@SuppressWarnings("rawtypes")
	public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Api For Music Rooms", "Music Room 2019", "1.0.0",
			"PREMIUM", DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licences/LICENSE-2.0",
			new ArrayList<VendorExtension>());

	public static void main(String[] args) {
		SpringApplication.run(MusicRoomApplication.class, args);
	}

}
