package de.htwberlin.recipecreator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Welcome {

    @GetMapping("/")
    @ResponseBody
    public String welcome() {
        return "Hello World";
    }

}