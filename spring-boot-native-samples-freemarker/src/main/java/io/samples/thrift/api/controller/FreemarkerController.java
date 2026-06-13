package io.samples.thrift.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: baoxin.zhao
 * @date: 2024/7/27
 */
@Controller
public class FreemarkerController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

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
