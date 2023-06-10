package com.example.StateMachine.services;

import com.example.StateMachine.entities.Order;
import com.example.StateMachine.entities.OrderEvent;
import com.example.StateMachine.entities.OrderState;
import com.example.StateMachine.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	
	@Autowired
	private StateMachine<OrderState, OrderEvent> stateMachine;
	@Autowired
	private OrderRepository orderRepository;
	
	public Order startProcessing(Order order) {
		stateMachine.sendEvent(OrderEvent.START_PROCESSING);
		order.setState(stateMachine.getState().getId());
		return orderRepository.save(order);
	}
	
	public Order completeOrder(Order order) {
		stateMachine.sendEvent(OrderEvent.COMPLETE);
		order.setState(stateMachine.getState().getId());
		return orderRepository.save(order);
	}
	
	public Order cancelOrder(Order order) {
		stateMachine.sendEvent(OrderEvent.CANCEL);
		order.setState(stateMachine.getState().getId());
		return orderRepository.save(order);
	}
	
	public Order getOrderById(Long orderId) {
		return orderRepository.getReferenceById(orderId);
	}
	
	public Order createOrder(Order order) {
		return orderRepository.save(order);
	}
}
