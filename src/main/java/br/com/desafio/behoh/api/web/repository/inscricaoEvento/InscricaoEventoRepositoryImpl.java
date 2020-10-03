package br.com.desafio.behoh.api.web.repository.inscricaoEvento;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.desafio.behoh.api.web.domain.InscricaoEvento;

import br.com.desafio.behoh.api.web.domain.Situacao;
import br.com.desafio.behoh.api.web.repository.filter.InscricaoEventoFilter;

public class InscricaoEventoRepositoryImpl implements InscricaoEventoQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<InscricaoEvento> pesquisar(InscricaoEventoFilter inscricaoEventoFilter, Situacao situacao) {
		System.out.println("Chegou no reposiroty");
		System.out.println(inscricaoEventoFilter.toString());
		int nova_situacao = 0;
		if (situacao == Situacao.INSCRITO) {
			nova_situacao += 0;
		} else {
			nova_situacao += 1;
		}
		
		TypedQuery<InscricaoEvento> query = null;
		if ((inscricaoEventoFilter.getEvento_id() != null) &&
				(inscricaoEventoFilter.getUsuario_id() != null)) {
			System.out.println("entrou no 1º if");	
			query = (TypedQuery<InscricaoEvento>) entityManager.createNativeQuery(
				"select * from inscricao_evento ie " +
				"inner join evento e on ie.evento_id = e.id " +
				"inner join usuario u on ie.usuario_id = u.id " +
				"where ie.situacao = :situacao and ie.evento_id = :evento_id " + 
				"and ie.usuario_id = :usuario_id " +
				"order by ie.id asc", InscricaoEvento.class);			
			query.setParameter("evento_id", inscricaoEventoFilter.getEvento_id());
			query.setParameter("usuario_id", inscricaoEventoFilter.getUsuario_id());
			query.setParameter("situacao", nova_situacao);
			
		} else	if (inscricaoEventoFilter.getEvento_id() != null) {
			System.out.println("entrou no 2º if");	
			query = (TypedQuery<InscricaoEvento>) entityManager.createNativeQuery(
				"select * from inscricao_evento ie " +
				"inner join evento e on ie.evento_id = e.id " +
				"inner join usuario u on ie.usuario_id = u.id " +
				"where ie.situacao = :situacao and ie.evento_id = :evento_id " +
				"order by ie.id asc", InscricaoEvento.class);			
			query.setParameter("evento_id", inscricaoEventoFilter.getEvento_id());
			query.setParameter("situacao", nova_situacao);
			
		} else if (inscricaoEventoFilter.getUsuario_id() != null) {
			System.out.println("entrou no 3º if");		
			query = (TypedQuery<InscricaoEvento>) entityManager.createNativeQuery(
				"select * from inscricao_evento ie " +
				"inner join evento e on ie.evento_id = e.id " +
				"inner join usuario u on ie.usuario_id = u.id " +
				"where ie.situacao = :situacao and ie.usuario_id = :usuario_id " +
				"order by ie.id asc", InscricaoEvento.class);			
			query.setParameter("usuario_id", inscricaoEventoFilter.getUsuario_id());
			query.setParameter("situacao", nova_situacao);
					
		} else if (inscricaoEventoFilter.getId() != null) {
			System.out.println("entrou no 4º if");			
			query = (TypedQuery<InscricaoEvento>) entityManager.createNativeQuery(
				"select * from inscricao_evento ie " +
				"inner join evento e on ie.evento_id = e.id " +
				"inner join usuario u on ie.usuario_id = u.id " +
				"where ie.situacao = :situacao and ie.id = :id " +
				"order by ie.id asc", InscricaoEvento.class);			
			query.setParameter("id", inscricaoEventoFilter.getId());
			query.setParameter("situacao", nova_situacao);
		}
				
		System.out.println("Query: " + query.getResultList());
		return query.getResultList();
	}

}
