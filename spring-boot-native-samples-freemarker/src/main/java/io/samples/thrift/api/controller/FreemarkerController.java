package io.samples.thrift.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/7/27
 */
@Slf4j
@Controller
public class FreemarkerController {

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("welcome","Hello Some!");
        return "index";
    }

    @RequestMapping("/format")
    public String format2(Model model){
        model.addAttribute("welcome","Hello Some!");
        return "some";
    }

}
