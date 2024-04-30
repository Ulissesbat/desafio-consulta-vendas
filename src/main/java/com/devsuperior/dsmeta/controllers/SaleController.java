package com.devsuperior.dsmeta.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projection.SaleSummaryProjection;
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/report")
	public ResponseEntity<List<SaleMinDTO>> getSalesReportByDateAndName(
	    @RequestParam LocalDate minDate,
	    @RequestParam LocalDate maxDate,
	    @RequestParam String name
	) {
	    List<Sale> sales = service.getSalesByDateAndName(minDate, maxDate, name);
	    List<SaleMinDTO> dtos = sales.stream()
	                                 .map(SaleMinDTO::new)
	                                 .collect(Collectors.toList());
	    return ResponseEntity.ok(dtos);
	}



	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleSummaryProjection>> getSummary(@RequestParam(required = false) LocalDate minDate,
	                                                       @RequestParam(required = false) LocalDate maxDate) {
	    if (minDate == null) {
	        minDate = LocalDate.now().minusYears(1);
	    }
	    if (maxDate == null) {
	        maxDate = LocalDate.now();
	    }

	    List<SaleSummaryProjection> salesSummary = service.getSalesSummary(minDate, maxDate);

	    return ResponseEntity.ok(salesSummary);
	}




}
