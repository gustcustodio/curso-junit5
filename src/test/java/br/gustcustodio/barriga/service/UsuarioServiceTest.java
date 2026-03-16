package br.gustcustodio.barriga.service;

import br.gustcustodio.barriga.domain.Usuario;
import br.gustcustodio.barriga.domain.builders.UsuarioBuilder;
import br.gustcustodio.barriga.repositories.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class UsuarioServiceTest {

    private UsuarioService usuarioService;

    @Test
    public void deveRetornarEmptyQuandoUsuarioInexistente() {
        UsuarioRepository usuarioRepository = Mockito.mock(UsuarioRepository.class);
        usuarioService = new UsuarioService(usuarioRepository);
        Optional<Usuario> user = usuarioService.getUserByEmail("mail@mail.com");
        Assertions.assertTrue(user.isEmpty());
    }

    @Test
    public void deveRetornarUsuarioPorEmail() {
        UsuarioRepository usuarioRepository = Mockito.mock(UsuarioRepository.class);
        usuarioService = new UsuarioService(usuarioRepository);

        Mockito.when(usuarioRepository.getUserByEmail("mail@mail.com")).thenReturn(Optional.of(UsuarioBuilder.umUsuario().agora()));

        Optional<Usuario> user = usuarioService.getUserByEmail("mail@mail.com");
        Assertions.assertFalse(user.isEmpty());
    }

}
