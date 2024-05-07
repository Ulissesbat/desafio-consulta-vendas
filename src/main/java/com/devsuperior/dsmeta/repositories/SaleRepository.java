package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projection.SaleSummaryProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query(value = """
        SELECT obj FROM Sale obj JOIN FETCH obj.seller
        WHERE obj.date >= :min
        AND obj.date <= :max
        AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))
        """, countQuery = """
        SELECT COUNT(obj) FROM Sale obj JOIN obj.seller
        WHERE obj.date >= :min
        AND obj.date <= :max
        AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))
        """)
Page<Sale> searchSales(LocalDate min, LocalDate max, String name, Pageable pageable);
	@Query("""
        SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(s.seller.name, SUM(s.amount))
        FROM Sale s
        WHERE s.date >= :minDate AND s.date <= :maxDate
        GROUP BY s.seller.name
        """)
	List<SaleSummaryDTO> getSalesSummary(LocalDate minDate, LocalDate maxDate);

}
