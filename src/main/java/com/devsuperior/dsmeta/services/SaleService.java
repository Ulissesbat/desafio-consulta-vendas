package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
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
	
	  public Page<Sale> getSalesReport(LocalDate startDate, LocalDate endDate, String name, Pageable pageable) {
	        if (name != null && !name.isEmpty()) {
	            return repository.findByDateBetweenAndSellerNameContaining(startDate, endDate, name, pageable);
	        } else {
	            return repository.findByDateBetween(startDate, endDate, pageable);
	        }
	    }

	  public List<SaleSummaryDTO> getSalesSummary(LocalDate minDate, LocalDate maxDate) {
	        List<SaleSummaryProjection> salesSummaryProjections = repository.findSalesSummary(minDate, maxDate);
	        List<SaleSummaryDTO> salesSummaryDTOs = salesSummaryProjections.stream()
	                .map(SaleSummaryDTO::new)
	                .collect(Collectors.toList());
	        return salesSummaryDTOs;
	    }
	
	 public Page<SaleMinDTO> getSalesByDateAndName(LocalDate startDate, LocalDate endDate, String name, Pageable pageable) {
	        Page<Sale> salesPage;
	        if (name != null && !name.isEmpty()) {
	            salesPage = repository.findByDateBetweenAndSellerNameContaining(startDate, endDate, name, pageable);
	        } else {
	            salesPage = repository.findByDateBetween(startDate, endDate, pageable);
	        }
	        return salesPage.map(SaleMinDTO::new);
	    }
}
