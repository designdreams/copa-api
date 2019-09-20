package com.designdreams.copass;

import com.designdreams.copass.mysql.DBConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class CopassApp {



    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = null;

        try {

            SpringApplication.run(CopassApp.class, args);

            applicationContext = new AnnotationConfigApplicationContext(
                    DBConfiguration.class);
            AppStaticFactory.setContext(applicationContext);

            System.out.println(" Read Traveller context " + AppStaticFactory.getContext());


        } catch (Exception e) {
            System.out.println(" COPASS ");

            e.printStackTrace();
        }

        System.out.println(" COPASS API started......!!!!! " + Arrays.asList(applicationContext.getBeanDefinitionNames()));

    }

}