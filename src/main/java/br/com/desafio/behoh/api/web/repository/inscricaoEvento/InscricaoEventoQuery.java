package br.com.desafio.behoh.api.web.repository.inscricaoEvento;

import java.util.List;

import br.com.desafio.behoh.api.web.domain.InscricaoEvento;
import br.com.desafio.behoh.api.web.domain.Situacao;
import br.com.desafio.behoh.api.web.repository.filter.InscricaoEventoFilter;

public interface InscricaoEventoQuery {

	public List<InscricaoEvento> pesquisar(InscricaoEventoFilter inscricaoEventoFilter, Situacao situacao);
	
}
