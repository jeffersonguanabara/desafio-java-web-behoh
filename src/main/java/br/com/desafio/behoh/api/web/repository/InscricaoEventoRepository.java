package br.com.desafio.behoh.api.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.desafio.behoh.api.web.domain.InscricaoEvento;
import br.com.desafio.behoh.api.web.repository.inscricaoEvento.InscricaoEventoQuery;

@Repository
public interface InscricaoEventoRepository extends JpaRepository<InscricaoEvento, Long>, InscricaoEventoQuery {

}
