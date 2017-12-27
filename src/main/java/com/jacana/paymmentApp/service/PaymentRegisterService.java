package com.jacana.paymmentApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.jacana.paymmentApp.model.Invoice;
import com.jacana.paymmentApp.model.InvoiceStatus;
import com.jacana.paymmentApp.repository.Invoices;
import com.jacana.paymmentApp.repository.filter.InvoiceFilter;


@Service
public class PaymentRegisterService {
	
	@Autowired
	private Invoices invoices;
	
	
	public void save(Invoice invoice) {
		try{
			invoices.save(invoice);
		} catch(DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Invalid format date");
		}
	}


	public void delete(Long coding) {
		invoices.delete(coding);	
	}


	public String receive(Long coding) {
		Invoice invoice = invoices.findOne(coding);
		invoice.setStatus(InvoiceStatus.RECEIVED);
		invoices.save(invoice);
		
		return InvoiceStatus.RECEIVED.getDescription();
	}
	
	public List<Invoice> filtering(InvoiceFilter filter){
		String description = filter.getDescription() == null ? "%" : filter.getDescription();
		return invoices.findByDescriptionContaining(description);
	}

}
