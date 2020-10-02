package br.com.desafio.behoh.api.web.repository.inscricaoEvento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.desafio.behoh.api.web.domain.Evento;
import br.com.desafio.behoh.api.web.domain.Evento_;
import br.com.desafio.behoh.api.web.domain.InscricaoEvento;
import br.com.desafio.behoh.api.web.domain.InscricaoEvento_;
import br.com.desafio.behoh.api.web.domain.Situacao;
import br.com.desafio.behoh.api.web.domain.Usuario;
import br.com.desafio.behoh.api.web.domain.Usuario_;
import br.com.desafio.behoh.api.web.repository.filter.InscricaoEventoFilter;

public class InscricaoEventoRespositoryImpl implements InscricaoEventoQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<InscricaoEvento> pesquisar(InscricaoEventoFilter inscricaoEventoFilter, Situacao situacao) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<InscricaoEvento> criteria = builder.createQuery(InscricaoEvento.class);
		Root<InscricaoEvento> root = criteria.from(InscricaoEvento.class);
		
		Predicate[] predicates = criarRestricoes(inscricaoEventoFilter, situacao, criteria, builder, root);
		criteria.where(predicates);
				
		TypedQuery<InscricaoEvento> query = entityManager.createQuery(criteria);
		return query.getResultList();
	}

	private Predicate[] criarRestricoes(InscricaoEventoFilter inscricaoEventoFilter, Situacao situacao, 
			CriteriaQuery<InscricaoEvento> criteria, CriteriaBuilder builder, Root<InscricaoEvento> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		Join<InscricaoEvento, Usuario> usuarioJoin = root.join(InscricaoEvento_.usuario, JoinType.INNER);
		Join<InscricaoEvento, Evento> eventoJoin = root.join(InscricaoEvento_.evento, JoinType.INNER);
				
		if((inscricaoEventoFilter.getEvento_id() == null) && (inscricaoEventoFilter.getUsuario_id() != null)){
			
			predicates.add(builder.equal(root.get(InscricaoEvento_.usuario), inscricaoEventoFilter.getUsuario_id()));
		}
		if((inscricaoEventoFilter.getEvento_id() != null) && (inscricaoEventoFilter.getUsuario_id() == null)) {
			predicates.add(builder.equal(root.get(InscricaoEvento_.evento), inscricaoEventoFilter.getEvento_id()));
		}
		if(inscricaoEventoFilter.getId() != null) {
			predicates.add(builder.equal(root.get(InscricaoEvento_.id), inscricaoEventoFilter.getId()));
		}
		
		criteria.multiselect(root.get(InscricaoEvento_.id),
							 root.get(InscricaoEvento_.evento),
							 eventoJoin.get(Evento_.nome),
							 eventoJoin.get(Evento_.data_e_hora_de_inicio),
							 eventoJoin.get(Evento_.data_e_hora_de_fim),
							 eventoJoin.get(Evento_.vagas),
							 root.get(InscricaoEvento_.usuario),
							 usuarioJoin.get(Usuario_.nome),
							 root.get(InscricaoEvento_.presenca),
							 root.get(InscricaoEvento_.situacao)
							 );

		return predicates.stream().toArray(Predicate[]::new);
	}
}
