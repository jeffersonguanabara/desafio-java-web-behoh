package br.com.desafio.behoh.api.web.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(InscricaoEvento.class)
public abstract class InscricaoEvento_ {

	public static volatile SingularAttribute<InscricaoEvento, Presenca> presenca;
	public static volatile SingularAttribute<InscricaoEvento, Evento> evento;
	public static volatile SingularAttribute<InscricaoEvento, Situacao> situacao;
	public static volatile SingularAttribute<InscricaoEvento, Date> data_de_inscricao;
	public static volatile SingularAttribute<InscricaoEvento, Usuario> usuario;
	public static volatile SingularAttribute<InscricaoEvento, Long> id;

	public static final String PRESENCA = "presenca";
	public static final String EVENTO = "evento";
	public static final String SITUACAO = "situacao";
	public static final String DATA_DE_INSCRICAO = "data_de_inscricao";
	public static final String USUARIO = "usuario";
	public static final String ID = "id";

}

