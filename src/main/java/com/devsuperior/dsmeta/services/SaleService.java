package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projection.SaleSummaryProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SaleMinDTO> getSalesReport(LocalDate minDate, LocalDate maxDate, String name) {
		List<Sale> sales;
		if (name != null && !name.isEmpty()) {
			sales = repository.findByDateBetweenAndSellerNameContaining(minDate, maxDate, name);
		} else {
			sales = repository.findByDateBetween(minDate, maxDate);
		}

		return sales.stream()
				.map(SaleMinDTO::new)
				.collect(Collectors.toList());
	}
	public List<SaleMinDTO> getSalesReportLast12Months() {
		LocalDate startDate = LocalDate.now().minusMonths(12);
		LocalDate endDate = LocalDate.now();
		List<Sale> sales = repository.findByDateBetween(startDate, endDate);
		return sales.stream().map(SaleMinDTO::new).collect(Collectors.toList());
	}


	public List<SaleSummaryDTO> getSalesSummary(LocalDate minDate, LocalDate maxDate) {
		List<SaleSummaryProjection> salesSummaryProjections = repository.findSalesSummaryBySeller(minDate, maxDate, "");
		List<SaleSummaryDTO> salesSummaryDTO = salesSummaryProjections.stream().map(SaleSummaryDTO::new).collect(Collectors.toList());
		return salesSummaryDTO;
	}
	public List<SaleSummaryDTO> getSalesSummaryBySeller(LocalDate startDate, LocalDate endDate) {
		List<SaleSummaryProjection> salesSummaryProjections = repository.findSalesSummaryBySeller(startDate,endDate,"");
		List<SaleSummaryDTO> salesSummaryDTO = salesSummaryProjections.stream().map(SaleSummaryDTO::new).collect(Collectors.toList());
		return salesSummaryDTO;
	}

}
