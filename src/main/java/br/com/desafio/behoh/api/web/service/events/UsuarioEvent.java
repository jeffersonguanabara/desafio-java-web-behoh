package br.com.desafio.behoh.api.web.service.events;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class UsuarioEvent extends ApplicationEvent {

	private HttpServletResponse response;
	private Long id;
	
	public UsuarioEvent(Object source, HttpServletResponse response, Long id) {
		super(source);
		this.response = response;
		this.id = id;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Long getId() {
		return id;
	}

}
