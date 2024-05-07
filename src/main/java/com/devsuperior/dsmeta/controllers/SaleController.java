package com.devsuperior.dsmeta.controllers;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
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
	public ResponseEntity<Page<SaleMinDTO>> getSalesReportByDateAndName(
			@RequestParam(value = "minDate", required = false) String minDate,
			@RequestParam(value = "maxDate", required = false) String maxDate,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			Pageable pageable) {
		Page<SaleMinDTO> dto = service.getSalesReport(minDate, maxDate, name, pageable);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/summary")
	public ResponseEntity<List<SaleSummaryDTO>> getSalesSummary(
			@RequestParam(name = "minDate", defaultValue = "") String minDate,
			@RequestParam(name = "maxDate", defaultValue = "") String maxDate) {
		LocalDate parsedMinDate = minDate.isEmpty() ? LocalDate.now() : LocalDate.parse(minDate);
		LocalDate parsedMaxDate = maxDate.isEmpty() ? LocalDate.now() : LocalDate.parse(maxDate);
		List<SaleSummaryDTO> dto = service.getSalesSummary(parsedMinDate, parsedMaxDate);
		return ResponseEntity.ok(dto);
	}



}
