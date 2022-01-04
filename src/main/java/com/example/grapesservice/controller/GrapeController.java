package com.example.grapesservice.controller;

import com.example.grapesservice.Repository.GrapeRepository;
import com.example.grapesservice.model.Grape;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;



@RestController
public class GrapeController {

    @Autowired
    private GrapeRepository grapeRepository;

    @PostConstruct
    public void fillDB() {
        if(grapeRepository.count()==0){
            grapeRepository.save(new Grape("Fernao Pires", "spanje", "catalonië"));
            grapeRepository.save(new Grape("semillon", "italië", "rome"));
            grapeRepository.save(new Grape("Merlot", "frankrijk", "champagne"));
            grapeRepository.save(new Grape("Sangiovese", "frankrijk", "sangio"));
        }

        System.out.println(grapeRepository.finGrapeByGrapeNameContaining("2"));
    }

    @GetMapping("/grapes/grapename/{grapeName}")
    public List<Grape> getGrapesByGrapeName(@PathVariable String grapeName){
        return grapeRepository.finGrapeByGrapeNameContaining(grapeName);
    }

    @GetMapping("/grapes/region/{region}")
    public List<Grape> getGrapesByRegion(@PathVariable String region){
        return grapeRepository.finGrapeByRegionContaining(region);
    }

    @GetMapping("/grapes/country/{country}")
    public List<Grape> getGrapesByCountry(@PathVariable String country){
        return grapeRepository.finGrapeByCountryContaining(country);
    }

    @PostMapping("/grapes")
    public Grape addGrape(@RequestBody Grape grape){
        grapeRepository.save(grape);
        return grape;
    }

    @PutMapping("/grapes")
    public Grape updateGrape(@RequestBody Grape updatedGrape){
        Grape retrievedGrape = grapeRepository.findGrapeByGrapeName(updatedGrape.getGrapeName());
        retrievedGrape.setGrapeName(updatedGrape.getGrapeName());
        retrievedGrape.setRegion(updatedGrape.getRegion());
        retrievedGrape.setCountry(updatedGrape.getCountry());

        grapeRepository.save(retrievedGrape);

        return retrievedGrape;
    }

    @DeleteMapping("/grapes/grapename/{grapeName}")
    public ResponseEntity deleteGrape(@PathVariable String grapeName){
        Grape grape = grapeRepository.findGrapeByGrapeName(grapeName);

        if(grape != null){
            grapeRepository.delete(grape);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
