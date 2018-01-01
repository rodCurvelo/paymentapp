package com.jacana.paymmentApp.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Invoice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long coding;
	
	@NotEmpty(message = "Description field must not be empty")
	@Size(max = 60, message = "Description field must not contain more than 60 characters")
	private String description;
	
	@NotNull(message = "Expiration field must not be empty")
	@DateTimeFormat(pattern = "MM/dd/yyyy" )
	@Temporal(TemporalType.DATE)
	private Date expiration;
	
	@NotNull(message = "Price field must not be empty")
	@DecimalMin(value = "0.01", message = "Price field must not be less than $0.01")
	@DecimalMax (value = "9999999.99", message = "Price field must not be more than $9.999.999.999,99")
	private BigDecimal price;
	
	@Enumerated(EnumType.STRING)
	private InvoiceStatus status;
	
	public Long getCoding() {
		return coding;
	}

	public void setCoding(Long coding) {
		this.coding = coding;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getExpiration() {
		return expiration;
	}
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public InvoiceStatus getStatus() {
		return status;
	}
	public void setStatus(InvoiceStatus status) {
		this.status = status;
	}
	
	
	public boolean isPending() {
		return InvoiceStatus.PENDING.equals(this.status);
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coding == null) ? 0 : coding.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		if (coding == null) {
			if (other.coding != null)
				return false;
		} else if (!coding.equals(other.coding))
			return false;
		return true;
	}
	

}
