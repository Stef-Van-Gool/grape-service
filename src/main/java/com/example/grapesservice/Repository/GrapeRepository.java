package com.example.grapesservice.Repository;

import com.example.grapesservice.model.Grape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrapeRepository extends JpaRepository<Grape, Integer> {

    List<Grape> finGrapeByGrapeNameContaining(String grapeName);
    List<Grape> finGrapeByRegionContaining(String region);
    List<Grape> finGrapeByCountryContaining(String country);
    Grape findGrapeByGrapeName(String grapeName);

}
