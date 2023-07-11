package de.htwberlin.recipecreator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rezepte")
public class RezeptController {

    @Autowired
    RezeptService service;

    @PostMapping
    public Rezept createRezept(@RequestBody Rezept rezept) {
        return service.save(rezept);
    }

    @GetMapping("/{id}")
    public Rezept getRezept(@PathVariable String id) {
        Long rezeptId = Long.parseLong(id);
        return service.get(rezeptId);
    }
}