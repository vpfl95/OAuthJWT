package com.example.oauthjwt.controller;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController {


    public String home(){
        return "index";
    }

}
