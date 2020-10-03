package br.com.desafio.behoh.api.web.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "evento")
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NonNull
	private String nome;
	
	@NonNull
	private Integer vagas;
	
	@NonNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date data_e_hora_de_inicio;
	
	@NonNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date data_e_hora_de_fim;
	
	@OneToMany(mappedBy = "evento")
	@JsonIgnore
	private List<InscricaoEvento> inscricoes;
	
	public Evento() {
		super();
	}

	public Evento(String nome, Integer vagas, 
			Date data_e_hora_de_inicio, Date data_e_hora_de_fim) {
		super();
		this.nome = nome;
		this.vagas = vagas;
		this.data_e_hora_de_inicio = data_e_hora_de_inicio;
		this.data_e_hora_de_fim = data_e_hora_de_fim;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getVagas() {
		return vagas;
	}

	public void setVagas(Integer vagas) {
		this.vagas = vagas;
	}

	public Date getData_e_hora_de_inicio() {
		return data_e_hora_de_inicio;
	}

	public void setData_e_hora_de_inicio(Date data_e_hora_de_inicio) {
		this.data_e_hora_de_inicio = data_e_hora_de_inicio;
	}

	public Date getData_e_hora_de_fim() {
		return data_e_hora_de_fim;
	}

	public void setData_e_hora_de_fim(Date data_e_hora_de_fim) {
		this.data_e_hora_de_fim = data_e_hora_de_fim;
	}

	public List<InscricaoEvento> getInscricoes() {
		return inscricoes;
	}

	public void setInscricoes(List<InscricaoEvento> inscricoes) {
		this.inscricoes = inscricoes;
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
		Evento other = (Evento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Evento [id=" + id + ", nome=" + nome + ", vagas=" + vagas + ", data_e_hora_de_inicio="
				+ data_e_hora_de_inicio + ", data_e_hora_de_fim=" + data_e_hora_de_fim + "]";
	}	
}
