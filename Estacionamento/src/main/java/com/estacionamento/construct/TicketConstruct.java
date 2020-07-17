package com.estacionamento.construct;

import java.util.HashMap;

import com.estacionamento.Utils.Utils;
import com.estacionamento.document.Ticket;

public class TicketConstruct {
	
	public static Ticket ticketConstruct(String placa, int reservationNumber) {
		
		Ticket ticket = new Ticket();
		ticket.setPlaca(placa);
		ticket.setNumReserva(reservationNumber);
		ticket.setPago(false);
		ticket.setSaiu(false);
		ticket.setDataEntrada(Utils.getDate());
		
		return ticket;
	}
	
	public static HashMap<String,String> ticketHistoricConstruct(Ticket actualTicket) {
		
		HashMap<String,String> historicTicket = new HashMap<String,String>();
		historicTicket.put("id", actualTicket.get_id());
		
		if(actualTicket.getDataSaida() == null || actualTicket.getDataSaida().isEmpty()) {
			long minutes = Utils.getMinutesPermanence(actualTicket.getDataEntrada());
			historicTicket.put("tempoPermanencia",minutes+" minutos");
		} else {
			historicTicket.put("tempoPermanencia",actualTicket.getTempoPermanencia());
		}
		
		historicTicket.put("pago",actualTicket.isPago()+"");
		historicTicket.put("saiu",actualTicket.isSaiu()+"");		
		return historicTicket;
	}

}
