package com.jacana.paymmentApp.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jacana.paymmentApp.model.Invoice;
import com.jacana.paymmentApp.model.InvoiceStatus;
import com.jacana.paymmentApp.repository.Invoices;
import com.jacana.paymmentApp.repository.filter.InvoiceFilter;
import com.jacana.paymmentApp.service.PaymentRegisterService;

@Controller
@RequestMapping("/invoice")
public class PaymentOrderController {
	
	private static final String REGISTER_VIEW = "Payment_Register";
	
	
	@Autowired
	private PaymentRegisterService paymentRegisterService;
	
	@RequestMapping("/add")
	public ModelAndView add() {
		ModelAndView mv = new ModelAndView(REGISTER_VIEW);
		mv.addObject(new Invoice());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveInvoice(@Validated Invoice invoice, Errors errors, RedirectAttributes attributes ) {
		if(errors.hasErrors()) {
			return REGISTER_VIEW;
		}
		
		// TODO: Salvar no banco de dados
		System.out.println(">>> " + invoice.getDescription());
		
		try {
			paymentRegisterService.save(invoice);
			
			
			attributes.addFlashAttribute("message", "Payment saved succesfully!");
			return "redirect:/invoice/add";
		} catch (IllegalArgumentException e) {
			errors.rejectValue("expiration", null, e.getMessage());
			return REGISTER_VIEW;
		}
	}
	
	@RequestMapping
	public ModelAndView searching(@ModelAttribute("filter")  InvoiceFilter filter) {
		
		List<Invoice> allInvoices = paymentRegisterService.filtering(filter);
		
		
		ModelAndView mv = new ModelAndView("InvoiceSearch");
		mv.addObject("invoices", allInvoices);
		return mv;
	}
	
	@RequestMapping("{coding}")
	public ModelAndView edition(@PathVariable ("coding") Invoice invoice) {
		ModelAndView mv = new ModelAndView(REGISTER_VIEW);
		mv.addObject(invoice);
		return mv;
	}
	
	
	@RequestMapping(value="{coding}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Long coding, RedirectAttributes attributes) {
		paymentRegisterService.delete(coding);
		
		attributes.addFlashAttribute("message", "Order succesfully deleted!");
		return "redirect:/invoice";
	}
	
	@RequestMapping(value = "/{coding}/receive", method = RequestMethod.PUT)
	public @ResponseBody String receive(@PathVariable Long coding) {
		return paymentRegisterService.receive(coding);
	}
	
	
	@ModelAttribute("allPayments") 
	public List<InvoiceStatus>allPayments(){
		return Arrays.asList(InvoiceStatus.values());
	}
	
	
}
