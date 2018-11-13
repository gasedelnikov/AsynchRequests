package com.main;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;

@Controller
@RequestMapping("/test")
public class test {
    private final String form = "/test";    

    @RequestMapping(method = RequestMethod.GET)
    public String welcome(ModelMap model) {
        System.out.println("Done test GET!");        
        return this.form;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(BindingResult result) {
        System.out.println("Done test POST!");     
        return this.form;
    }    
    
}