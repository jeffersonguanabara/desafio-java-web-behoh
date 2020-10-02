package br.com.desafio.behoh.api.web.service.exception;

public class UnavailableCheckin extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UnavailableCheckin(String msg) {
		super(msg);
	}
}
