package app.controllers;

/**
 * Created by Mihkel on 27.10.2015.
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class SignInController {

    @RequestMapping(value="/signin", method=RequestMethod.GET)
    public void signin() {
    }

}