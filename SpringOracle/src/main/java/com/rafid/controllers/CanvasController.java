package com.rafid.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ASUS on 5/20/2017.
 */
@Controller
public class CanvasController {
    @Autowired
    @RequestMapping("/canvas")
    String canvas() {
        return "canvas";

    }
}
