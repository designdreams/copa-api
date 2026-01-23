package com.designdreams.copass.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailContentBuilder {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    public EmailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(String src,  String dest, int tripMatchCount) {
        Context context = new Context();
        context.setVariable("source", src);
        context.setVariable("destination", dest);
        context.setVariable("tripMatchCount", tripMatchCount);
        return templateEngine.process("EmailAlert", context);
    }

}
