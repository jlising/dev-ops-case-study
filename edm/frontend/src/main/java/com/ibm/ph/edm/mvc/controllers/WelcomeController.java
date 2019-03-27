package com.ibm.ph.edm.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Controller
@RequestMapping(value="/")
public class WelcomeController {
    private static Logger LOG = LoggerFactory.getLogger(WelcomeController.class);

    @RequestMapping(value="", method = RequestMethod.GET)
    public String welcome(){
        return "welcome";
    }
}
