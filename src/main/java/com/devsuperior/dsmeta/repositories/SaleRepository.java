package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projection.SaleSummaryProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	List<Sale> findByDateBetween(LocalDate startDate, LocalDate endDate);

	
	@Query("SELECT s.seller.name AS sellerName, SUM(s.amount) AS totalSales " +
		       "FROM Sale s " +
		       "WHERE s.date BETWEEN :minDate AND :maxDate " +
		       "GROUP BY s.seller.name")
		List<SaleSummaryProjection> findSalesSummary(@Param("minDate") LocalDate minDate,
		                                             @Param("maxDate") LocalDate maxDate);


	List<Sale> findByDateBetweenAndSellerNameContaining(LocalDate startDate, LocalDate endDate, String name);


	
	

}
