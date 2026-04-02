package br.gustcustodio.barriga.service;

import br.gustcustodio.barriga.domain.Usuario;
import br.gustcustodio.barriga.domain.exceptions.ValidationException;
import br.gustcustodio.barriga.service.repositories.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.gustcustodio.barriga.domain.builders.UsuarioBuilder.umUsuario;
import static org.mockito.Mockito.never;
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
        when(usuarioRepository.getUserByEmail("mail@mail.com")).thenReturn(Optional.of(umUsuario().agora()));

        Optional<Usuario> user = usuarioService.getUserByEmail("mail@mail.com");
        System.out.println(user);
        Assertions.assertTrue(user.isPresent());

        verify(usuarioRepository, Mockito.atLeastOnce()).getUserByEmail("mail@mail.com");
        verify(usuarioRepository, never()).getUserByEmail("outro@mail.com");
        Mockito.verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    public void deveSalvarUsuarioComSucesso() {
        Usuario userToSave = umUsuario().comId(null).agora();

        when(usuarioRepository.salvar(userToSave)).thenReturn(umUsuario().agora());

        Usuario savedUser = usuarioService.salvar(userToSave);
        Assertions.assertNotNull(savedUser.getId());

        verify(usuarioRepository).getUserByEmail(userToSave.getEmail());
//		verify(repository).salvar(userToSave);
    }

    @Test
    public void deveRejeitarUsuarioExistente() {
        Usuario userToSave = umUsuario().comId(null).agora();
        when(usuarioRepository.getUserByEmail(userToSave.getEmail())).thenReturn(Optional.of(umUsuario().agora()));

        ValidationException ex = Assertions.assertThrows(ValidationException.class, () -> usuarioService.salvar(userToSave));

        Assertions.assertTrue(ex.getMessage().endsWith("já cadastrado!"));

        verify(usuarioRepository, never()).salvar(userToSave);
    }

}
