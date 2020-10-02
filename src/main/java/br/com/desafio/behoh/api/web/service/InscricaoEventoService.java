package br.com.desafio.behoh.api.web.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.servlet.UnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafio.behoh.api.web.domain.Evento;
import br.com.desafio.behoh.api.web.domain.InscricaoEvento;
import br.com.desafio.behoh.api.web.domain.Presenca;
import br.com.desafio.behoh.api.web.domain.Situacao;
import br.com.desafio.behoh.api.web.domain.Usuario;
import br.com.desafio.behoh.api.web.repository.InscricaoEventoRepository;
import br.com.desafio.behoh.api.web.repository.filter.InscricaoEventoFilter;
import br.com.desafio.behoh.api.web.service.exception.EventStartedException;
import br.com.desafio.behoh.api.web.service.exception.NoMoreVacanciesException;


@Service
public class InscricaoEventoService {
	
	@Autowired
	private InscricaoEventoRepository inscricaoEventoRepository;
	
	public InscricaoEvento salvarInscricao(Evento evento, Usuario usuario, Situacao situacao) {
		
		InscricaoEvento nova_inscricao = null;
		InscricaoEventoFilter filter = new InscricaoEventoFilter();
		filter.setEvento_id(evento.getId());
				
		List<InscricaoEvento> inscricoesSalvas = inscricaoEventoRepository.pesquisar(filter, situacao);
		
		if(inscricoesSalvas.isEmpty() || inscricoesSalvas == null) {
			nova_inscricao = new InscricaoEvento(usuario, evento, situacao, Presenca.AUSENTE, Date.from(Instant.now()));
		} else {
			if (situacao == Situacao.INSCRITO) {
				if(inscricoesSalvas.size() >= evento.getVagas()) {
					throw new NoMoreVacanciesException("Não há mais vagas!");
				} else {
					if ((evento.getData_e_hora_de_inicio().compareTo(Date.from(Instant.now()))) < 0) {
						throw new EventStartedException("Tempo de inscrição expirado!");
					} else {
						boolean validador = false;
						for (InscricaoEvento inscricaoEvento : inscricoesSalvas) {
							if(inscricaoEvento.getUsuario().getId() == usuario.getId()) {
								validador = true;
							}
						}
						if(validador) {
							throw new NoMoreVacanciesException("Usuário já está inscrito!");
						} else {
							nova_inscricao = new InscricaoEvento(usuario, evento, situacao, Presenca.AUSENTE, Date.from(Instant.now()));
						}
					}
				}	
			} else {
				nova_inscricao = new InscricaoEvento(usuario, evento, situacao, Presenca.AUSENTE, Date.from(Instant.now()));		
			}
		}
		return inscricaoEventoRepository.save(nova_inscricao);
	}
	
	public InscricaoEvento salvarReserva(Evento evento, Usuario usuario, Situacao situacao) {
		InscricaoEvento nova_reserva = new InscricaoEvento(usuario, evento, situacao, Presenca.AUSENTE, Date.from(Instant.now()));
		return inscricaoEventoRepository.save(nova_reserva);
	}
	
	public InscricaoEvento converterReservaEmInscricao(InscricaoEvento inscricaoEvento,
			Situacao novaSituacao) {
		
		InscricaoEventoFilter inscricaoEventoFilter = new InscricaoEventoFilter();
		inscricaoEventoFilter.setId(inscricaoEvento.getId());
		
		List<InscricaoEvento> reservados = inscricaoEventoRepository.pesquisar(inscricaoEventoFilter, inscricaoEvento.getSituacao());
		List<InscricaoEvento> inscritos = inscricaoEventoRepository.pesquisar(inscricaoEventoFilter, novaSituacao);
		
		int vagasNoEvento = numeroVagasNoEvento(inscritos);
		
		if(!reservados.isEmpty() || reservados != null) {
			if(inscritos.size() <= vagasNoEvento) {
				inscricaoEvento.setSituacao(novaSituacao);
			}
		}
		return inscricaoEventoRepository.save(inscricaoEvento);
	}
	
	public List<InscricaoEvento> pesquisar(InscricaoEvento inscricaoEvento) {
		
		InscricaoEventoFilter inscricaoEventoFilter = new InscricaoEventoFilter();
		inscricaoEventoFilter.setUsuario_id(inscricaoEvento.getUsuario().getId());
		
		List<InscricaoEvento> inscricoes = inscricaoEventoRepository.pesquisar(inscricaoEventoFilter, null);
		
		return inscricoes;
	}
	
	public void checkInEvent(InscricaoEvento inscricaoEvento, Presenca presenca) throws UnavailableException {
		
		InscricaoEventoFilter inscricaoEventoFilter = new InscricaoEventoFilter();
		inscricaoEventoFilter.setId(inscricaoEvento.getId());
		inscricaoEventoFilter.setEvento_id(inscricaoEvento.getEvento().getId());
		inscricaoEventoFilter.setUsuario_id(inscricaoEvento.getUsuario().getId());
		
		Date data_de_comparacao = inscricaoEvento.getEvento().getData_e_hora_de_inicio();
		int horas = data_de_comparacao.getHours() - 2;
		data_de_comparacao.setHours(horas);
		
		List<InscricaoEvento> inscricoesEventos = inscricaoEventoRepository.pesquisar(inscricaoEventoFilter, null);
		
		if (!inscricoesEventos.isEmpty() || inscricoesEventos != null) {
			for (InscricaoEvento insEvento : inscricoesEventos) {
				if((data_de_comparacao).after(Date.from(Instant.now())) &&
					(inscricaoEvento.getEvento().getData_e_hora_de_fim().before(Date.from(Instant.now())))) {
					inscricaoEvento.setPresenca(presenca);
					inscricaoEventoRepository.save(inscricaoEvento);
				} else {
					throw new UnavailableException("Check-in indisponível.");
				}
			}
		}
	}
	
	public void cancelarInscricao(InscricaoEvento inscricaoEvento) {
	
		InscricaoEventoFilter novo_filtro = converterInscricaoEventoFilter(inscricaoEvento);
		
		List<InscricaoEvento> inscricoes = inscricaoEventoRepository.pesquisar(novo_filtro, null);
		if (!inscricoes.isEmpty() || inscricoes != null) {
			for (InscricaoEvento inscricao : inscricoes) {
				if(inscricao.getPresenca() != Presenca.PRESENTE) {
					inscricaoEventoRepository.delete(inscricaoEvento);
				} else {
					throw new EventStartedException("Não é possível realizar o cancelamento da inscrição!");
				}
			}
		}
		
	}

	private InscricaoEventoFilter converterInscricaoEventoFilter(InscricaoEvento inscricaoEvento) {
		InscricaoEventoFilter inscricaoEventoFilter = new InscricaoEventoFilter();
		inscricaoEventoFilter.setId(inscricaoEvento.getId());
		inscricaoEventoFilter.setEvento_id(inscricaoEvento.getEvento().getId());
		inscricaoEventoFilter.setUsuario_id(inscricaoEvento.getUsuario().getId());
		return inscricaoEventoFilter;
	}

	private Integer numeroVagasNoEvento(List<InscricaoEvento> inscritos) {
		int vagas = 0;
		for (InscricaoEvento inscricaoEvento : inscritos) {
			vagas = inscricaoEvento.getEvento().getVagas();
		}
		return vagas;
	}
	
}
