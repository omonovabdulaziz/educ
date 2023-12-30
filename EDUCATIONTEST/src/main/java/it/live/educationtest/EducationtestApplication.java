package it.live.educationtest;

import it.live.educationtest.util.SmsServiceUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EducationtestApplication {

    public static void main(String[] args) {
//        SmsServiceUtil.setToken();
        SpringApplication.run(EducationtestApplication.class, args);
    }

}
