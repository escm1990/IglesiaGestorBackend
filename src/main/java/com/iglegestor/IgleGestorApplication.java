package com.iglegestor;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.iglegestor.service.FileService;

@SpringBootApplication
public class IgleGestorApplication implements CommandLineRunner{

	@Resource
	FileService fileService;
	
	public static void main(String[] args) {
		SpringApplication.run(IgleGestorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//fileService.deleteAll();
		//fileService.init();
	}

}
