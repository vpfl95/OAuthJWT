package com.example.oauthjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/login")
    //@ResponseBody
    public String mainAPI(){

        return "index";
    }


}
