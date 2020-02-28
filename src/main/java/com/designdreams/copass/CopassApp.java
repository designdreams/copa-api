package com.designdreams.copass;

import com.designdreams.copass.mysql.DBConfiguration;
import com.designdreams.copass.utils.AES;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@SpringBootApplication
public class CopassApp {

    private static final Logger logger = LogManager.getLogger(CopassApp.class);

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = null;

        try {


            SpringApplication.run(CopassApp.class, args);

            applicationContext = new AnnotationConfigApplicationContext(
                    DBConfiguration.class);
            AppStaticFactory.setContext(applicationContext);;

            logger.info(" Read context " + AppStaticFactory.getContext());

        } catch (Exception e) {
            logger.error(e, e);
            e.printStackTrace();
        }

        logger.info(" COPAYANA  started......!!!!! " + Arrays.asList(applicationContext.getBeanDefinitionNames()));

    }

}