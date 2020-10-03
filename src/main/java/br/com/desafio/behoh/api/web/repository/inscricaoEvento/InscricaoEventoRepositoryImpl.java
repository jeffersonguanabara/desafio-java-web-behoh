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
		
		TypedQuery<InscricaoEvento> query = null;
		if (inscricaoEventoFilter.getId() != null 
				&& inscricaoEventoFilter.getEvento_id() != null 
				&& inscricaoEventoFilter.getUsuario_id() != null) {
			
			query = (TypedQuery<InscricaoEvento>) entityManager.createNativeQuery(
					"select * from inscricao_evento ie " +
					"inner join evento e on ie.evento_id = e.id " +
					"inner join usuario u on ie.usuario_id = u.id " +
					"where ie.situacao = :situacao and ie.evento_id = :evento_id " + 
					"and ie.usuario_id = :usuario_id and ei.id = :id", InscricaoEvento.class);			
			query.setParameter("evento_id", inscricaoEventoFilter.getEvento_id());
			query.setParameter("usuario_id", inscricaoEventoFilter.getUsuario_id());
			query.setParameter("id", inscricaoEventoFilter.getId());
			query.setParameter("situacao", situacao);
		
		} else {
			if (inscricaoEventoFilter.getEvento_id() != null) {
				
				query = (TypedQuery<InscricaoEvento>) entityManager.createNativeQuery(
					"select * from inscricao_evento ie " +
					"inner join evento e on ie.evento_id = e.id " +
					"inner join usuario u on ie.usuario_id = u.id " +
					"where ie.situacao = :situacao and ie.evento_id = :evento_id " +
					"order by ie.id asc", InscricaoEvento.class);			
				query.setParameter("evento_id", inscricaoEventoFilter.getEvento_id());
				query.setParameter("situacao", situacao);
				
			} else { 
				if (inscricaoEventoFilter.getUsuario_id() != null) {
					
					query = (TypedQuery<InscricaoEvento>) entityManager.createNativeQuery(
						"select * from inscricao_evento ie " +
						"inner join evento e on ie.evento_id = e.id " +
						"inner join usuario u on ie.usuario_id = u.id " +
						"where ie.situacao = :situacao and ie.usuario_id = :ususario_id " +
						"order by ie.id asc", InscricaoEvento.class);			
					query.setParameter("usuario", inscricaoEventoFilter.getUsuario_id());
					query.setParameter("situacao", situacao);
					
				} else {
					if (inscricaoEventoFilter.getId() != null) {
						
						query = (TypedQuery<InscricaoEvento>) entityManager.createNativeQuery(
							"select * from inscricao_evento ie " +
							"where ie.situacao = :situacao and ie.id = :id " +
							"order by ie.id asc", InscricaoEvento.class);			
						query.setParameter("id", inscricaoEventoFilter.getId());
						query.setParameter("situacao", situacao);
					}
				}
			}	
		}	
		System.out.println("Query: " + query.getResultList());
		return query.getResultList();
	}

}
