package br.gustcustodio.barriga.service;

import br.gustcustodio.barriga.domain.Usuario;
import br.gustcustodio.barriga.domain.exceptions.ValidationException;
import br.gustcustodio.barriga.repositories.UsuarioRepository;

public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario salvar(Usuario usuario) {
        usuarioRepository.getUserByEmail(usuario.getEmail()).ifPresent(user -> {
            throw new ValidationException(String.format("Usuário %s já cadastrado!", usuario.getEmail()));
        });
        return usuarioRepository.salvar(usuario);
    }

}
