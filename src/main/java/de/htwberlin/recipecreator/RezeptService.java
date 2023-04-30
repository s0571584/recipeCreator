package de.htwberlin.recipecreator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RezeptService {

    @Autowired
    RezeptRepository repo;


    public Rezept save(Rezept rezept){
        Rezept result = repo.save(rezept);
        return result;
    }

    public Rezept get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException());
    }

}