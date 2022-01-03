/*
 * Copyright (c) 2020. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package ch.fhnw.acrm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/profilePage")
public class ProfileController {

    @GetMapping
    public String getProfileView(){
        return "acrm/profile.html";
    }

    @GetMapping("/searchFollower")
    public String getCustomerCreateView(){
        return "../acrm/searchFollower.html";
    }

}
