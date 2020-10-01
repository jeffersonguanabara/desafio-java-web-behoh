package br.com.desafio.behoh.api.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.desafio.behoh.api.web.domain.InscricaoEvento;

@Repository
public interface InscricaoEventoRepository extends JpaRepository<InscricaoEvento, Long> {

	
	@Query(value = "SELECT * FROM inscricao_evento ie" +
			"WHERE ie.evento_id = :evento_id" +
			"ORDER BY ie.id",
			nativeQuery = true)
	List<InscricaoEvento> findAllEventoById(@Param("evento_id") Long evento_id);
}
