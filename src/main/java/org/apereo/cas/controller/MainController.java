package org.apereo.cas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * description:
 *
 * @author : niudongjun
 * @date : 2022/3/31 11:52
 */
@Controller
public class MainController {

    @GetMapping("/preLogin")
    public ModelAndView preLogin() {
        return new ModelAndView("kaptcha");
    }

}
