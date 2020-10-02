package br.com.desafio.behoh.api.web.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Evento.class)
public abstract class Evento_ {

	public static volatile SingularAttribute<Evento, Integer> vagas;
	public static volatile SingularAttribute<Evento, Date> data_e_hora_de_inicio;
	public static volatile ListAttribute<Evento, InscricaoEvento> inscricoes;
	public static volatile SingularAttribute<Evento, String> nome;
	public static volatile SingularAttribute<Evento, Long> id;
	public static volatile SingularAttribute<Evento, Date> data_e_hora_de_fim;

	public static final String VAGAS = "vagas";
	public static final String DATA_E_HORA_DE_INICIO = "data_e_hora_de_inicio";
	public static final String INSCRICOES = "inscricoes";
	public static final String NOME = "nome";
	public static final String ID = "id";
	public static final String DATA_E_HORA_DE_FIM = "data_e_hora_de_fim";

}

