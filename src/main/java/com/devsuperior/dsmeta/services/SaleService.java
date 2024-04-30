package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
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
	
	public List<Sale> getSalesReport(LocalDate startDate, LocalDate endDate) {
        return repository.findByDateBetween(startDate, endDate);
    }

	public List<SaleSummaryProjection> getSalesSummary(LocalDate minDate, LocalDate maxDate) {
        return repository.findSalesSummary(minDate, maxDate);
    }
	  public List<Sale> getSalesByDateAndName(LocalDate startDate, LocalDate endDate, String name) {
	        if (name == null) {
	            return repository.findByDateBetween(startDate, endDate);
	        } else {
	            return repository.findByDateBetweenAndSellerNameContaining(startDate, endDate, name);
	        }
	  }
}
