package br.com.desafio.behoh.api.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.desafio.behoh.api.web.domain.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long>{

}
