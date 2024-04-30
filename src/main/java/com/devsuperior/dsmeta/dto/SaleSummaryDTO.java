package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projection.SaleSummaryProjection;

public class SaleSummaryDTO {
	
	private String sellerName;
	private Double totalSales;

	public SaleSummaryDTO(String sellerName, Double totalSales) {
		this.sellerName = sellerName;
		this.totalSales = totalSales;
	}
	
	public SaleSummaryDTO(SaleSummaryProjection projection) {
		sellerName = projection.getSellerName();
		totalSales = projection.getTotalSales();
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public Double getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(Double totalSales) {
		this.totalSales = totalSales;
	}

}
