package com.estacionamento.Utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.estacionamento.service.TicketService;

public class Utils {
	
	@Autowired
	private static TicketService ticketService;
	
	public static String getDate() {
		String pattern = "dd/MM/yyyy HH:mm:ss";
		DateFormat df = new SimpleDateFormat(pattern);
		
		Date actualDate = Calendar.getInstance().getTime();
		String formatedDate = df.format(actualDate);
		
		return formatedDate;
	}
	
	public static long getMinutesPermanence(String entryDateStr) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");
		LocalDateTime localTime = LocalDateTime.parse(entryDateStr,format);
		
		Date entryDate = Timestamp.valueOf(localTime);
		Date actualDate = Calendar.getInstance().getTime();
		
		long diffInMillies = Math.abs(actualDate.getTime() - entryDate.getTime());
		long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
		
		return diff;		
	}
	
	private static int generateNumber() {
		Random random = new Random();
		int low = 1000000;
		int high = 9999999;
		int result = random.nextInt(high-low) + low;
		
		return result;
	}
	
	public static int generateReservation() {
		int newReservation;
		while(true){
			newReservation = generateNumber();
			System.out.println("new reservation: "+newReservation);
			try {
				ticketService.findByNumReserva(newReservation);	
			}catch (NullPointerException e) {
				break;
			}
		}
		return newReservation;
	}
	
	public static boolean verifyMask(String placa) {
		if(placa.length() == 8) {
			String[] arrayPlaca = placa.split("-");
			if(arrayPlaca.length == 2) {
				String letters = arrayPlaca[0];
				if(letters.length() == 3) {
					String maskLetter = "[a-zA-Z]+";
					if(letters.matches(maskLetter)) {
						String numbers = arrayPlaca[1];
						if(numbers.length() == 4) {
							String maskNumbers = "[0-9]+";
							if(numbers.matches(maskNumbers)) {
								return true;
							} else {
								throw new IllegalArgumentException();
							}
						} else {
							throw new IllegalArgumentException();
						}
					} else {
						throw new IllegalArgumentException();
					}
				} else {
					throw new IllegalArgumentException();
				}
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

}
