package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
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

	public Page<SaleMinDTO> getSalesReport(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		LocalDate max = (maxDate == null || maxDate.equals("")) ? today : LocalDate.parse(maxDate);
		LocalDate min = (minDate == null || minDate.equals("")) ? max.minusYears(1L) : LocalDate.parse(minDate);
		Page<Sale> result = repository.searchSales(min, max, name, pageable);

		return result.map(x -> new SaleMinDTO(x));
	}
	public List<SaleSummaryDTO> getSalesSummary(LocalDate minDate, LocalDate maxDate) {
		maxDate = LocalDate.now();
		minDate = maxDate.minus(Period.ofMonths(12));
		return repository.getSalesSummary(minDate, maxDate);
	}
}
