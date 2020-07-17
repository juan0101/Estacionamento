package com.estacionamento.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamento.Utils.Utils;
import com.estacionamento.construct.TicketConstruct;
import com.estacionamento.document.Ticket;
import com.estacionamento.service.TicketService;

@RestController
@RequestMapping("/estacionamentos")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@PostMapping()
	public HashMap<String, Integer> saveTicket(@RequestBody String body, HttpServletResponse response) {
		try {
			JSONObject json = new JSONObject(body);
			String placa = json.getString("placa");
			int reservationNumber = 0;
			if(Utils.verifyMask(placa)) {
				if(!ticketService.existWithOpenTicket(placa)) {
					reservationNumber = Utils.generateReservation();		
					Ticket ticket = TicketConstruct.ticketConstruct(placa, reservationNumber);
					ticketService.saveTicket(ticket);
					response.setStatus(HttpServletResponse.SC_ACCEPTED);
					HashMap<String, Integer> responseBody = new HashMap<String, Integer>();
					responseBody.put("reserva", reservationNumber);
					return responseBody;
				}else {
					try {
						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
						response.getWriter().print("<html><head><title>Oops houve um engano!</title></head>");
						response.getWriter().print("<body>Acreditamos que já existe um ticket aberto para esta placa.</body>");
						response.getWriter().println("</html>");
					}catch (IOException ioe) {System.out.println(ioe.getStackTrace());}
				}								
			}	
			return null;
			
		}catch (JSONException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}catch (IllegalArgumentException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}		
	}
	
	@GetMapping("/{placa}") 
	public ArrayList<HashMap<String,String>> findHistoric(@PathVariable("placa") String placa, HttpServletResponse response){
		try {
			ArrayList<HashMap<String,String>> tickets = new ArrayList<HashMap<String,String>>();
			if(Utils.verifyMask(placa)) {
				ArrayList<Ticket> ticketsFromMongo = ticketService.findByPlaca(placa);
				
				if(ticketsFromMongo.size() > 0) {
					for(Ticket ticket: ticketsFromMongo) {
						tickets.add(TicketConstruct.ticketHistoricConstruct(ticket));
					}
				} else {
					throw new NoSuchElementException();
				}
			} 	
			return tickets;	
		}catch (IllegalArgumentException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);			
			return null;
		}catch (NoSuchElementException e) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);			
			return null;
		}		
	}
	
	@PutMapping("/{id}/pagamento")
	public void payParking(@PathVariable("id") String id, HttpServletResponse response) {
		try {
			ticketService.updatePagamento(id);
		}catch (NoSuchElementException e) {			
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);					
		}
	}
	
	
	@PutMapping("/{id}/saida")
	public void leaveParking(@PathVariable("id") String id, HttpServletResponse response) {
		try {
			Ticket ticket = ticketService.updateSaida(id);
			if(ticket == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().print("<html><head><title>Oops houve um engano!</title></head>");
				response.getWriter().print("<body>Acreditamos que você se esqueceu de pagar o estacionamento.</body>");
				response.getWriter().println("</html>");
			}
		}catch (Exception e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	
	

}
