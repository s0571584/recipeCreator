package de.htwberlin.recipecreator;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Willkommen beim Rezeptgenerator!";
    }
}
