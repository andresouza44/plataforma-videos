package com.andre.plataformavideos;

import com.andre.plataformavideos.entity.Video;
import com.andre.plataformavideos.repositories.VideoRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class PlataformaVideosApplication  {
	public static void main(String[] args) {
		SpringApplication.run(PlataformaVideosApplication.class, args);


	}




}
