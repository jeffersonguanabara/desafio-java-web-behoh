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
import br.com.desafio.behoh.api.web.service.exceptions.EventStartedException;
import br.com.desafio.behoh.api.web.service.exceptions.NoMoreVacanciesException;


@Service
public class InscricaoEventoService {
	
	@Autowired
	private InscricaoEventoRepository inscricaoEventoRepository;
	
	public InscricaoEvento salvarInscricao(InscricaoEvento inscricao) {
				
		InscricaoEventoFilter filter = new InscricaoEventoFilter();
		filter.setEvento_id(inscricao.getEvento().getId());
		filter.setUsuario_id(inscricao.getUsuario().getId());
				
		List<InscricaoEvento> inscricoesSalvas = inscricaoEventoRepository.pesquisar(filter, inscricao.getSituacao());
		
		System.out.println("inscriçoes: " + inscricoesSalvas);
		
		if(inscricoesSalvas.isEmpty() || inscricoesSalvas == null) {
			inscricao.setSituacao(Situacao.INSCRITO);
			inscricao.setPresenca(Presenca.AUSENTE);
			inscricao.setData_de_inscricao(Date.from(Instant.now()));
		} else {
			if (inscricao.getSituacao() == Situacao.INSCRITO) {
				if(inscricoesSalvas.size() >= inscricao.getEvento().getVagas()) {
					throw new NoMoreVacanciesException("Não há mais vagas!");
				} else {
					if ((inscricao.getEvento().getData_e_hora_de_inicio().compareTo(Date.from(Instant.now()))) < 0) {
						throw new EventStartedException("Tempo de inscrição expirado!");
					} else {
						boolean validador = false;
						for (InscricaoEvento inscricaoEvento : inscricoesSalvas) {
							if(inscricaoEvento.getUsuario().getId() == inscricao.getUsuario().getId()) {
								validador = true;
							}
						}
						if(validador) {
							throw new NoMoreVacanciesException("Usuário já está inscrito!");
						} else {
							inscricao.setSituacao(Situacao.INSCRITO);
							inscricao.setPresenca(Presenca.AUSENTE);
							inscricao.setData_de_inscricao(Date.from(Instant.now()));
						}
					}
				}	
			} else {
				inscricao.setSituacao(Situacao.INSCRITO);
				inscricao.setPresenca(Presenca.AUSENTE);
				inscricao.setData_de_inscricao(Date.from(Instant.now()));		
			}
		}
		return inscricaoEventoRepository.save(inscricao);
	}
	
	public InscricaoEvento salvarReserva(InscricaoEvento inscricaoEvento) {
		inscricaoEvento.setSituacao(Situacao.RESERVADO);
		inscricaoEvento.setPresenca(Presenca.AUSENTE);
		inscricaoEvento.setData_de_inscricao(Date.from(Instant.now()));
		return inscricaoEventoRepository.save(inscricaoEvento);
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
	
	public List<InscricaoEvento> pesquisar(InscricaoEvento inscricaoEvento, Situacao situacao) {
		System.out.println("Entrou no metodo de pesquisa");
		InscricaoEventoFilter inscricaoEventoFilter = new InscricaoEventoFilter();
		
		if(inscricaoEvento.getUsuario().getId() == null && inscricaoEvento.getId() == null) {
			inscricaoEventoFilter.setEvento_id(inscricaoEvento.getEvento().getId());
		}
		else if(inscricaoEvento.getEvento().getId() == null && inscricaoEvento.getId() == null) {
			inscricaoEventoFilter.setUsuario_id(inscricaoEvento.getUsuario().getId());
		}
		else if(inscricaoEvento.getEvento().getId() == null && inscricaoEvento.getUsuario().getId() == null) {
			inscricaoEventoFilter.setId(inscricaoEvento.getId());
		}		
		else if(inscricaoEvento.getEvento().getId() == null) {
			inscricaoEventoFilter.setUsuario_id(inscricaoEvento.getUsuario().getId());
			inscricaoEventoFilter.setId(inscricaoEvento.getId());
		}
		else if(inscricaoEvento.getUsuario().getId() == null) {
			inscricaoEventoFilter.setEvento_id(inscricaoEvento.getEvento().getId());
			inscricaoEventoFilter.setId(inscricaoEvento.getId());
		}
		else if(inscricaoEvento.getId() == null) {
			inscricaoEventoFilter.setUsuario_id(inscricaoEvento.getUsuario().getId());
			inscricaoEventoFilter.setEvento_id(inscricaoEvento.getEvento().getId());
		}
				
		List<InscricaoEvento> inscricoes = inscricaoEventoRepository.pesquisar(inscricaoEventoFilter, situacao);
		
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
					
					inscricaoEvento.setPresenca(Presenca.PRESENTE);
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
