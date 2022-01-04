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


    //spring.datasource.url=jdbc:postgresql://${POSTGRESQL_DB_HOST:localhost}:\
    //  ${POSTGRESQL_DB_PORT:5432}/grapedb_test?createDatabaseIfNotExist=true
    //
    //spring.datasource.username=${POSTGRESQL_DB_USERNAME:root}
    //spring.datasource.password=${POSTGRESQL_DB_PASSWORD:abc123}
}
