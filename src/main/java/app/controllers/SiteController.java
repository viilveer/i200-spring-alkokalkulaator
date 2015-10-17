package app.controllers;

import app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by Mihkel on 07.10.2015.
 */
@Controller
@RequestMapping("/")
public class SiteController {
    // Private fields

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method= RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("user", this.userRepository.findByEmail("mihkel@opus.ee"));
        return "site/index";
    }
}
