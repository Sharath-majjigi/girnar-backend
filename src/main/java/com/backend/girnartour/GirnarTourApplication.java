package com.backend.girnartour;

import com.backend.girnartour.models.User;
import com.backend.girnartour.repository.UserDAO;
import com.backend.girnartour.services.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.Instant;

@SpringBootApplication
public class GirnarTourApplication {
	@Autowired
	private UserDAO userDAO;

//    @PostConstruct
//    public void initUsers(){
//        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
//		User dummy=User.builder()
//				.userid("SHARATH")
//				.userName("sharath")
//				.active(true)
//				.email("admin@girnar.com")
//				.password(passwordEncoder.encode("xyz"))
//				.role("ADMIN")
//				.lastLogin(Timestamp.from(Instant.now()))
//				.build();
//		userDAO.save(dummy);
//
//		User dummy1=User.builder()
//				.userid("BHARATH")
//				.userName("bharath")
//				.active(true)
//				.email("user@girnar.com")
//				.password(passwordEncoder.encode("xyz"))
//				.role("USER")
//				.lastLogin(Timestamp.from(Instant.now()))
//				.build();
//
//        userDAO.save(dummy1);
//    }

	public static void main(String[] args) {
		SpringApplication.run(GirnarTourApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurerAdapter() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**")
//						.allowedOrigins("*")
//						.allowedHeaders("*")
//						.allowedMethods("*");
//			}
//		};
//	}

}
