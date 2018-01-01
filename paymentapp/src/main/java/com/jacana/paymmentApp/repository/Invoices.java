package com.jacana.paymmentApp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jacana.paymmentApp.model.Invoice;


public interface Invoices extends JpaRepository<Invoice, Long> {
	public List<Invoice> findByDescriptionContaining(String description);
}
