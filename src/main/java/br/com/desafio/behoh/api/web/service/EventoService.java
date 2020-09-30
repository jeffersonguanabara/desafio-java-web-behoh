package br.com.desafio.behoh.api.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafio.behoh.api.web.domain.Evento;
import br.com.desafio.behoh.api.web.repository.EventoRepository;
import javassist.NotFoundException;

@Service
public class EventoService {

	@Autowired
	private EventoRepository eventoRepository;
	
	public Evento salvarEvento(Evento evento) {
		return eventoRepository.save(evento);
	}
	
	public List<Evento> listarEventos() {
		return eventoRepository.findAll();
	}
	
	public Evento buscarEventoPeloId(Long id) {
		return eventoRepository.getOne(id);
	}
	
	public void deletarEventoPeloId(Long id) {
		eventoRepository.deleteById(id);
	}
	
	public Evento atualizarEvento(Evento evento) throws NotFoundException {
		Evento eventoSalvo = eventoRepository.getOne(evento.getId());
		if (eventoSalvo != null) {
			return eventoRepository.save(evento);
		} else {
			throw new NotFoundException("Não foi possível editar/atualizar evento. Evento não encontrado!");
		}
	}
}
