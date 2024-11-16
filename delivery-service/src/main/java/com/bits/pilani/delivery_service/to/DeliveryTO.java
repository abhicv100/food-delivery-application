package com.bits.pilani.delivery_service.to;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class DeliveryTO{	
	@Id
	@Column
	int order_id;
	
	@Column
	int delivery_person_id;
	
	@Column
	Boolean delivered;
	
	@Column
	String delivery_message;
	
	@Column
	Boolean delivery_accepted;

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getDelivery_person_id() {
		return delivery_person_id;
	}

	public void setDelivery_person_id(int delivery_person_id) {
		this.delivery_person_id = delivery_person_id;
	}

	public Boolean getDelivered() {
		return delivered;
	}

	public void setDelivered(Boolean delivered) {
		this.delivered = delivered;
	}

	public String getDelivery_message() {
		return delivery_message;
	}

	public void setDelivery_message(String delivery_message) {
		this.delivery_message = delivery_message;
	}

	public Boolean getDelivery_accepted() {
		return delivery_accepted;
	}

	public void setDelivery_accepted(Boolean delivery_accepted) {
		this.delivery_accepted = delivery_accepted;
	}

}
