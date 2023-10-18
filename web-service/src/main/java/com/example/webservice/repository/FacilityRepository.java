package com.example.webservice.repository;

import com.example.webservice.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import javax.persistence.*;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    Facility findByUserName(String userName);
}
