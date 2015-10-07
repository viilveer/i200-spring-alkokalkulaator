package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Mihkel on 07.10.2015.
 */
@Controller
@RequestMapping("/")
public class SiteController {
    @RequestMapping(method= RequestMethod.GET)
    public String helloFacebook() {
        return "index";
    }
}
