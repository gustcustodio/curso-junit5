package br.gustcustodio.barriga.service.repositories;

import br.gustcustodio.barriga.domain.Usuario;

import java.util.Optional;

public interface UsuarioRepository {

    Usuario salvar(Usuario usuario);

    Optional<Usuario> getUserByEmail(String email);

}
