package com.example.StateMachine.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private OrderState state;
	
	private String address;
	
	public Order(Long id, OrderState state, String address) {
		this.id = id;
		this.state = state;
		this.address = address;
	}
	
	public Order() {
	}
	
	
	public OrderState getState() {
		return state;
	}
	
	public void setState(OrderState state) {
		this.state = state;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
}
