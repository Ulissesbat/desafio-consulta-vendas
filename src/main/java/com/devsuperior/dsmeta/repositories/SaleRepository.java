package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projection.SaleSummaryProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    Page<Sale> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
	
    Page<Sale> findByDateBetweenAndSellerNameContaining(LocalDate startDate, LocalDate endDate, String name, Pageable pageable);
    @Query("SELECT obj FROM Sale obj JOIN FETCH obj.seller s" +
    	       " WHERE obj.date >= :minDate" +
    	       " AND obj.date <= :maxDate" +
    	       " AND UPPER(s.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    	List<SaleSummaryProjection> findSalesSummary(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, @Param("name") String name);

}
