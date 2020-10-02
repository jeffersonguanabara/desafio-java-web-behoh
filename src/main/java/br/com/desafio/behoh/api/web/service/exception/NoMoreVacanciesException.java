package br.com.desafio.behoh.api.web.service.exception;

public class NoMoreVacanciesException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NoMoreVacanciesException() {
		
	}
	
	public NoMoreVacanciesException(String msg) {
		super(msg);
	}
	
}
