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

	List<Sale> findByDateBetweenAndSellerNameContaining(LocalDate startDate, LocalDate endDate, String name);
	List<Sale> findByDateBetween(LocalDate startDate, LocalDate endDate);
	@Query("SELECT s.seller.name AS sellerName, SUM(s.amount) AS totalSales " +
			"FROM Sale s " +
			"WHERE s.date BETWEEN :startDate AND :endDate " +
			"AND UPPER(s.seller.name) LIKE UPPER(CONCAT('%', :name, '%')) " +
			"GROUP BY s.seller.name")
	List<SaleSummaryProjection> findSalesSummaryBySeller(
			@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate,
			@Param("name") String name);

	@Query("SELECT sale FROM Sale sale JOIN FETCH sale.seller " +
			"WHERE sale.date BETWEEN :min AND :max " +
			"AND UPPER(sale.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))")
	List<Sale> findSalesBetweenDatesAndSellerName(
			@Param("min") LocalDate min,
			@Param("max") LocalDate max,
			@Param("name") String name);

}