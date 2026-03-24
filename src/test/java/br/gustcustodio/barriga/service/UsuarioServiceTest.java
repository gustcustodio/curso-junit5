package br.gustcustodio.barriga.service;

import br.gustcustodio.barriga.domain.Usuario;
import br.gustcustodio.barriga.domain.builders.UsuarioBuilder;
import br.gustcustodio.barriga.repositories.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    public void deveRetornarEmptyQuandoUsuarioInexistente() {
        Optional<Usuario> user = usuarioService.getUserByEmail("mail@mail.com");
        Assertions.assertTrue(user.isEmpty());
    }

    @Test
    public void deveRetornarUsuarioPorEmail() {
        when(usuarioRepository.getUserByEmail("mail@mail.com"))
                .thenReturn(Optional.of(UsuarioBuilder.umUsuario().agora()));

        Optional<Usuario> user = usuarioService.getUserByEmail("mail@mail.com");
        System.out.println(user);
        Assertions.assertTrue(user.isPresent());

        verify(usuarioRepository, Mockito.atLeastOnce()).getUserByEmail("mail@mail.com");
        verify(usuarioRepository, Mockito.never()).getUserByEmail("outro@mail.com");
        Mockito.verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    public void deveSalvarUsuarioComSucesso() {
        Usuario userToSave = UsuarioBuilder.umUsuario().comId(null).agora();

        when(usuarioRepository.salvar(userToSave)).thenReturn(UsuarioBuilder.umUsuario().agora());

        Usuario savedUser = usuarioService.salvar(userToSave);
        Assertions.assertNotNull(savedUser.getId());

        verify(usuarioRepository).getUserByEmail(userToSave.getEmail());
//		verify(repository).salvar(userToSave);
    }

}
