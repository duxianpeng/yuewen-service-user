package com.yuewen.responsitory;

import com.yuewen.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlightResponsitory extends JpaRepository<Flight, String> {

    @Query("select o from flight where category=?1")
    public Page<Flight> findByCategory(String category, Pageable pageable);
}
