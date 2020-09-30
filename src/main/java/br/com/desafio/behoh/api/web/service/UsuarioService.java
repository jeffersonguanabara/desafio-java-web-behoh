package br.com.desafio.behoh.api.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafio.behoh.api.web.domain.Usuario;
import br.com.desafio.behoh.api.web.repository.UsuarioRepository;
import javassist.NotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario salvarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll();
	}
	
	public Usuario buscarUsarioPeloId(Long id) {
		return usuarioRepository.getOne(id);
	}
	
	public void deletarUsuarioPeloId(Long id) {
		usuarioRepository.deleteById(id);
	}
	
	public Usuario atualizarUsuario(Usuario usuario) throws NotFoundException {
		Usuario usuarioSalvo = usuarioRepository.getOne(usuario.getId());
		if (usuarioSalvo != null) {
			return usuarioRepository.save(usuario);
		} else {
			throw new NotFoundException("Não foi possível editar/atualizar usuário. Usuário não encontrado!");
		}
	}
}
