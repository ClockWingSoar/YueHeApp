package com.yuehe.app;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"com.yuehe.app.controller", "com.yuehe.app.action"})
public class YueHeMain  extends SpringBootServletInitializer {
	  @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(YueHeMain.class);
	    }
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 SpringApplication.run(YueHeMain.class, args);
	}

}
