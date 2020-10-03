package br.com.desafio.behoh.api.web.repository.filter;

public class InscricaoEventoFilter {

	private Long id;
	private Long usuario_id;
	private Long evento_id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(Long usuario_id) {
		this.usuario_id = usuario_id;
	}
	public Long getEvento_id() {
		return evento_id;
	}
	public void setEvento_id(Long evento_id) {
		this.evento_id = evento_id;
	}
	@Override
	public String toString() {
		return "InscricaoEventoFilter [id=" + id + ", usuario_id=" + usuario_id + ", evento_id=" + evento_id + "]";
	}
	
	
}
