package br.com.desafio.behoh.api.web.restcontroller;

import javax.servlet.UnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio.behoh.api.web.domain.Evento;
import br.com.desafio.behoh.api.web.domain.InscricaoEvento;
import br.com.desafio.behoh.api.web.domain.Presenca;
import br.com.desafio.behoh.api.web.domain.Situacao;
import br.com.desafio.behoh.api.web.domain.Usuario;
import br.com.desafio.behoh.api.web.service.EventoService;
import br.com.desafio.behoh.api.web.service.InscricaoEventoService;
import javassist.NotFoundException;

@RestController
@RequestMapping("/api")
public class EventoRestController {

	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private InscricaoEventoService inscricaoEventoService;
	
	@GetMapping("/eventos")
	public ResponseEntity<?> listarTodosOsEventos() {
		return ResponseEntity.ok(eventoService.listarEventos());
	}
	
	@PostMapping("/eventos")
	public ResponseEntity<?> salvarEvento(@RequestBody @Validated Evento evento) {
		return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.salvarEvento(evento));
	}
	
	@GetMapping("/eventos/{id}")
	public ResponseEntity<?> buscarEventoPeloId(@PathVariable Long id) {
		return ResponseEntity.ok(eventoService.buscarEventoPeloId(id));
	}
	
	@DeleteMapping("/eventos/{id}")
	public ResponseEntity<?> deletarEventoPeloId(@PathVariable Long id) {
		eventoService.deletarEventoPeloId(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/eventos")
	public ResponseEntity<?> editarEvento(@RequestBody @Validated Evento evento) throws NotFoundException {
		return ResponseEntity.ok(eventoService.atualizarEvento(evento));
	}
	
	@PostMapping("/eventos/{id}/inscricao")
	public ResponseEntity<?> inscreverNoEvento(@RequestBody @Validated InscricaoEvento inscricaoEvento) {
		return ResponseEntity.ok(inscricaoEventoService.salvarInscricao(inscricaoEvento));
	}
	
	@PostMapping("/eventos/{id}/reserva")
	public ResponseEntity<?> reservaNoEvento(@RequestBody @Validated InscricaoEvento inscricaoEvento) {
		return ResponseEntity.ok(inscricaoEventoService.salvarReserva(inscricaoEvento));
	}
	
	@GetMapping("/eventos/{id}/lista-inscritos")
	public ResponseEntity<?> listarInscritosNoEvento(@PathVariable Long id) {
		System.out.println("Chegou aqui");
		Evento evento = new Evento();
		evento.setId(id);
		System.out.println(evento.toString());
		InscricaoEvento inscricaoEvento = new InscricaoEvento();
		inscricaoEvento.setEvento(evento);
		inscricaoEvento.setSituacao(Situacao.INSCRITO);
		
		return ResponseEntity.ok(inscricaoEventoService.pesquisar(inscricaoEvento, inscricaoEvento.getSituacao()));
	}
	
	@DeleteMapping("/eventos/{evento_id}/usuario/{usuario_id}")
	public ResponseEntity<?> cancelarInscricaoNoEvento(@PathVariable Long evento_id, @PathVariable Long usuario_id) {
		
		Evento evento = new Evento();
		Usuario usuario = new Usuario();
		InscricaoEvento inscricaoEvento = new InscricaoEvento();
		
		evento.setId(evento_id);
		usuario.setId(usuario_id);
		inscricaoEvento.setEvento(evento);
		inscricaoEvento.setUsuario(usuario);
		
		inscricaoEventoService.cancelarInscricao(inscricaoEvento);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/eventos/{evento_id}/validar")
	public ResponseEntity<?> validarEntradaNoEvento(@RequestBody @Validated InscricaoEvento inscricaoEvento) throws UnavailableException {
		inscricaoEventoService.checkInEvent(inscricaoEvento, Presenca.PRESENTE); 
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/eventos/{evento_id}/converter")
	public ResponseEntity<?> converterSituacaoNoEvento(@RequestBody @Validated InscricaoEvento inscricaoEvento) throws UnavailableException {
		inscricaoEventoService.converterReservaEmInscricao(inscricaoEvento, Situacao.INSCRITO); 
		return ResponseEntity.noContent().build();
	}

}