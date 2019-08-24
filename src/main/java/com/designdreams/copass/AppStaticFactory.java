package com.designdreams.copass;

import org.springframework.context.ApplicationContext;

public class AppStaticFactory {

    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        AppStaticFactory.context = context;
    }


}
