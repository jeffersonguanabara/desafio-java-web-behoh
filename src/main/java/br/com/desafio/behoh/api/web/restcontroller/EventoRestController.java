package br.com.desafio.behoh.api.web.restcontroller;

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
import br.com.desafio.behoh.api.web.service.EventoService;
import javassist.NotFoundException;

@RestController
@RequestMapping("/api")
public class EventoRestController {

	@Autowired
	private EventoService eventoService;
	
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
	
	@PutMapping("eventos")
	public ResponseEntity<?> editarEvento(@RequestBody @Validated Evento evento) throws NotFoundException {
		return ResponseEntity.ok(eventoService.atualizarEvento(evento));
	}
}