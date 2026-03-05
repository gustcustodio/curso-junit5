package br.gustcustodio.barriga.domain;

import br.gustcustodio.barriga.domain.builders.UsuarioBuilder;
import br.gustcustodio.barriga.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Domínio: Usuário")
public class UsuarioTest {

    @Test
    @DisplayName("Deve criar usuário válido")
    public void deveCriarUsuarioValido() {
        Usuario usuario = UsuarioBuilder.umUsuario().agora();
        Assertions.assertAll("Usuario",
                () -> assertEquals(1L, usuario.getId()),
                () -> assertEquals("Usuário Válido", usuario.getNome()),
                () -> assertEquals("usuario@email.com", usuario.getEmail()),
                () -> assertEquals("12345678", usuario.getSenha())
        );
    }

    @Test
    @DisplayName("Deve rejeitar usuário sem nome")
    public void deveRejeitarUsuarioSemNome() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                UsuarioBuilder.umUsuario().comNome(null).agora()
        );
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar usuário sem email")
    public void deveRejeitarUsuarioSemEmail() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                UsuarioBuilder.umUsuario().comEmail(null).agora()
        );
        assertEquals("Email é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar usuário sem senha")
    public void deveRejeitarUsuarioSemSenha() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                UsuarioBuilder.umUsuario().comSenha(null).agora()
        );
        assertEquals("Senha é obrigatória", exception.getMessage());
    }

}
