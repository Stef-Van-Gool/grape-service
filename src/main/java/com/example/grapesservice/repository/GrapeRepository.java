package com.example.grapesservice.repository;

import com.example.grapesservice.model.Grape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrapeRepository extends JpaRepository<Grape, Integer> {

    public Grape findGrapeByGrapeName(String grapeName);
    public List<Grape> findGrapeByGrapeNameContaining(String grapeName);
    public List<Grape> findGrapeByRegionContaining(String region);
    public List<Grape> findGrapeByCountryContaining(String country);

}
