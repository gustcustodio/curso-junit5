package br.gustcustodio.barriga.domain;

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
        Usuario usuario = new Usuario(1L, "Usuario Valido", "usuario@email.com", "123456");
        Assertions.assertAll("Usuario",
                () -> assertEquals(1L, usuario.getId()),
                () -> assertEquals("Usuario Valido", usuario.getNome()),
                () -> assertEquals("usuario@email.com", usuario.getEmail()),
                () -> assertEquals("123456", usuario.getSenha())
        );
    }

    @Test
    @DisplayName("Deve rejeitar usuário sem nome")
    public void deveRejeitarUsuarioSemNome() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                new Usuario(1L, null, "usuario@email.com", "123456")
        );
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

}
