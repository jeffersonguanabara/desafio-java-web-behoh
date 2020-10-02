package br.com.desafio.behoh.api.web.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "inscricao_evento")
public class InscricaoEvento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "evento_id")
	private Evento evento;
	
	@NonNull
	private Situacao situacao;
	
	@NonNull
	private Presenca presenca;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date data_de_inscricao;
	
	public InscricaoEvento() {
		super();
	}

	public InscricaoEvento(Usuario usuario, Evento evento, Situacao situacao, Presenca presenca,
			Date data_de_inscricao) {
		super();
		this.usuario = usuario;
		this.evento = evento;
		this.situacao = situacao;
		this.presenca = presenca;
		this.data_de_inscricao = data_de_inscricao;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public Presenca getPresenca() {
		return presenca;
	}

	public void setPresenca(Presenca presenca) {
		this.presenca = presenca;
	}
	
	public Date getData_de_inscricao() {
		return data_de_inscricao;
	}

	public void setData_de_inscricao(Date data_de_inscricao) {
		this.data_de_inscricao = data_de_inscricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InscricaoEvento other = (InscricaoEvento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InscricaoEvento [id=" + id + ", usuario=" + usuario + ", evento=" + evento + ", situacao=" + situacao
				+ ", presenca=" + presenca + ", data_de_inscricao=" + data_de_inscricao + "]";
	}

	
}
