package br.com.desafio.behoh.api.web.service.exceptions;

public class EventStartedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EventStartedException() {
		
	}
	
	public EventStartedException(String msg) {
		super(msg);
	}

}
