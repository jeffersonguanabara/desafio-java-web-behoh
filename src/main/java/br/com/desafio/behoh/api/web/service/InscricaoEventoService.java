package br.com.desafio.behoh.api.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafio.behoh.api.web.domain.Evento;
import br.com.desafio.behoh.api.web.domain.InscricaoEvento;
import br.com.desafio.behoh.api.web.domain.Presenca;
import br.com.desafio.behoh.api.web.domain.Situacao;
import br.com.desafio.behoh.api.web.domain.Usuario;
import br.com.desafio.behoh.api.web.repository.InscricaoEventoRepository;

@Service
public class InscricaoEventoService {

	@Autowired
	private InscricaoEventoRepository inscricaoEventoRepository;
	
	public InscricaoEvento salvarInscricao(Evento evento, Usuario usuario) {
		InscricaoEvento nova_inscricao = null;
		List<InscricaoEvento> inscricoesSalva = inscricaoEventoRepository.findAllEventoById(evento.getId());
		if(inscricoesSalva.isEmpty() || inscricoesSalva == null) {
			nova_inscricao = new InscricaoEvento(usuario, evento, Situacao.INSCRITO, Presenca.AUSENTE);			
		} else {
			if(inscricoesSalva.size() >= evento.getVagas()) {
				nova_inscricao = new InscricaoEvento(usuario, evento, Situacao.RESERVADO, Presenca.AUSENTE);
			} else {
				nova_inscricao = new InscricaoEvento(usuario, evento, Situacao.INSCRITO, Presenca.AUSENTE);
			}
		}
		return inscricaoEventoRepository.save(nova_inscricao);
	}
	
}
