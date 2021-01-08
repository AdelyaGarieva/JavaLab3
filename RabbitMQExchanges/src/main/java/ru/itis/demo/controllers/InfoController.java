package ru.itis.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.demo.dto.UserDTO;
import ru.itis.demo.services.InfoService;

@Controller
public class InfoController {
    @Autowired
    private InfoService infoService;

    @GetMapping("/")
    public ModelAndView getPage() {
        return new ModelAndView("info");
    }

    @PostMapping("/")
    public String info(UserDTO userDTO) {
        try {
            infoService.sendToExchange(userDTO);
            return "redirect:/";
        } catch (IllegalStateException e) {
            return "redirect:/?error";
        }
    }
}
