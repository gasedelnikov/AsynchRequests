package com.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.main.model.Queue;
import com.main.utils.TypesUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

@Controller
//@RequestMapping(value = "/welcome")
public class WelcomeController {
    private final String form = "/welcome";    

//    @Value("${welcome.message:test}")
//    private String message = "Hello World yyyy";

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome(ModelMap model) {
        model.put("queue_types", Queue.QUEUE_TYPES);
        return this.form;
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.POST)
    public String processSubmit(HttpServletRequest request, ModelMap model) {
        RestTemplate restTemplate = new RestTemplate();
        String contextRoot = TypesUtils.getRequestContextPath(request)+"/request";
        
        int delay = TypesUtils.getIntFromString(request.getParameter("delay"), 0);        
        String message = "";
        ObjectMapper mapper = new ObjectMapper();        
        for (String type:Queue.QUEUE_TYPES){
            String cnt_str = request.getParameter(type);
            int cnt = TypesUtils.getIntFromString(cnt_str, 0);
            for (int i =0; i < cnt; i++){
                try {
                    Map<String, Object> map = new HashMap<>();
                    map.put("queue_type", type);
                    map.put("number", i);  
                    map.put("delay", delay);                      
                    String body = mapper.writeValueAsString(map);
                    String result2 = restTemplate.postForObject(contextRoot, body, String.class);     
                    message += "; "  + result2;
                } catch (JsonProcessingException ex) {
                }
            }
        }

        model.put("message", message);
        model.put("queue_types", Queue.QUEUE_TYPES);
        return this.form;
    }    
    
}