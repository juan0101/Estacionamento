package com.estacionamento.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.estacionamento.document.Ticket;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String>{
	
	public ArrayList<Ticket> findByPlaca(String placa);
	
	@Query("{'numReserva': ?0")
	public Ticket findByNumReserva(int numReserva);

}
