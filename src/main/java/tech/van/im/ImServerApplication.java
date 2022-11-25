package tech.van.im;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "tech.van.im.dao")
public class ImServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImServerApplication.class, args);
	}

}
