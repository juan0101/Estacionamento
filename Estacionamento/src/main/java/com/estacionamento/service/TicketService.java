package com.estacionamento.service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamento.Utils.Utils;
import com.estacionamento.document.Ticket;
import com.estacionamento.repository.TicketRepository;

@Service
public class TicketService {
	
	@Autowired
	private TicketRepository ticketRepository;
	
	public Ticket saveTicket(Ticket ticket) {
		return ticketRepository.save(ticket);
	}
	
	public ArrayList<Ticket> findByPlaca(String placa){
		return ticketRepository.findByPlaca(placa);
	}
	
	public Ticket findByNumReserva(int numReserva) {
		return ticketRepository.findByNumReserva(numReserva);
	}
	
	public Ticket findById(String id) {
		try {
			Optional<Ticket> opTicket = ticketRepository.findById(id);
			Ticket ticket = opTicket.get();
			return ticket;
		}catch (NoSuchElementException e) {
			throw new NoSuchElementException();
		}
	}
	
	public Ticket updatePagamento(String id) {
		try {
			Ticket ticket = findById(id);
			ticket.setPago(true);
			return saveTicket(ticket);
		}catch (NoSuchElementException e) {
			throw new NoSuchElementException();
		}		
	}
	
	public Ticket updateSaida(String id) {
		try {
			Ticket ticket = findById(id);
			if(ticket.isPago()) {
				long minutes = Utils.getMinutesPermanence(ticket.getDataEntrada());
				ticket.setTempoPermanencia(minutes+" minutos");
				ticket.setDataSaida(Utils.getDate());
				ticket.setSaiu(true);
			} else {
				return null;
			}			
			return saveTicket(ticket);
		}catch (NoSuchElementException e) {
			throw new NoSuchElementException();
		}catch (NullPointerException e) {
			throw new NullPointerException();
		}		
	}
	
	public boolean existWithOpenTicket(String placa) {
		try {
			ArrayList<Ticket> tickets = findByPlaca(placa);
			if(tickets.size()>0) {
				for(Ticket ticket: tickets) {
					if(ticket.getDataEntrada() != null &&
							(ticket.getDataSaida() == null || ticket.getDataSaida().isEmpty())){
						return true;
					}
				}
			}
			return false;
		}catch (NoSuchElementException e) {
			throw new NoSuchElementException();
		}	
	}

}
