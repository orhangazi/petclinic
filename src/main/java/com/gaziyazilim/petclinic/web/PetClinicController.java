package com.gaziyazilim.petclinic.web;

import com.gaziyazilim.petclinic.service.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PetClinicController {
    @Autowired
    PetClinicService petClinicService;

    @RequestMapping(value = {"/","/index.html"})
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("/owners")
    public ModelAndView getOwners(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("owners",petClinicService.findOwners());
        mav.setViewName("owners");
        return mav;
    }

    @RequestMapping("/pcs")
    @ResponseBody
    public String welcome(){
        return "Welcome to PetClinic World!";
    }
}
