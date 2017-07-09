package com.rafid.controllers.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ab9ma on 4/30/2017.
 */
@Controller
public class TestController {

    @RequestMapping("/ajaxDemo")
    public String showAjaxDemo(){
        return "test/ajaxDemo";
    }
    @RequestMapping("/changeAjaxContent")
    public @ResponseBody String getAjaxDemoResponse(){
        return "Fu*k you bitch";
    }
}

