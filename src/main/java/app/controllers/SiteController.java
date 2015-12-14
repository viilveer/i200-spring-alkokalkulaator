package app.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by Mihkel on 07.10.2015.
 */
@Controller
@RequestMapping("/")
public class SiteController {

    @RequestMapping(method= RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        if ("logout".equals(request.getQueryString())) {
            model.addAttribute("flashMessage", "Olete edukalt välja logitud.");
        }
        return "site/index";
    }
}
