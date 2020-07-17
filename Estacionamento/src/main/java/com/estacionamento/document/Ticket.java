package com.estacionamento.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Ticket {
	
	@Id
	private String _id;
	private Integer numReserva;
	private String placa;
	private String tempoPermanencia;
	private String dataEntrada;
	private String dataSaida;
	private boolean pago;
	private boolean saiu;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public void setNumReserva(Integer numReserva) {
		this.numReserva = numReserva;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getTempoPermanencia() {
		return tempoPermanencia;
	}
	public void setTempoPermanencia(String tempoPermanencia) {
		this.tempoPermanencia = tempoPermanencia;
	}
	public boolean isPago() {
		return pago;
	}
	public void setPago(boolean pago) {
		this.pago = pago;
	}
	public boolean isSaiu() {
		return saiu;
	}
	public void setSaiu(boolean saiu) {
		this.saiu = saiu;
	}
	public String getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(String dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public String getDataSaida() {
		return dataSaida;
	}
	public void setDataSaida(String dataSaida) {
		this.dataSaida = dataSaida;
	}
	public int getNumReserva() {
		return numReserva;
	}
	public void setNumReserva(int numReserva) {
		this.numReserva = numReserva;
	}
	
	

}
