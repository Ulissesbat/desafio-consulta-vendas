package com.devsuperior.dsmeta.controllers;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
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
			@RequestParam(value = "minDate", required = false) String minDateStr,
			@RequestParam(value = "maxDate", required = false) String maxDateStr,
			@RequestParam(value = "name", required = false, defaultValue = "") String name) {

		LocalDate minDate = null;
		if (minDateStr != null && !minDateStr.isEmpty()) {
			minDate = LocalDate.parse(minDateStr);
		} else {
			minDate = LocalDate.now().minusYears(1);
		}

		LocalDate maxDate = null;
		if (maxDateStr != null && !maxDateStr.isEmpty()) {
			maxDate = LocalDate.parse(maxDateStr);
		} else {
			maxDate = LocalDate.now();
		}

		List<SaleMinDTO> sales = service.getSalesReport(minDate, maxDate, name);
		return ResponseEntity.ok(sales);
	}



	@GetMapping("/summary")
	public ResponseEntity<List<SaleSummaryDTO>> getSalesSummary(
			@RequestParam(value = "minDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate minDate,
			@RequestParam(value = "maxDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maxDate) {

		if (minDate == null) {
			minDate = LocalDate.now().minusYears(1);
		}
		if (maxDate == null) {
			maxDate = LocalDate.now();
		}

		List<SaleSummaryDTO> salesSummary = service.getSalesSummary(minDate, maxDate);
		return ResponseEntity.ok(salesSummary);
	}

}
