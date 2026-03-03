package br.gustcustodio.barriga.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Domínio: Usuário")
public class UsuarioTest {

    @Test
    @DisplayName("Deve criar usuário válido")
    public void deveCriarUsuarioValido() {
        Usuario usuario = new Usuario(1L, "Usuario valido", "usuario@email.com", "123456");
        Assertions.assertEquals(1L, usuario.getId());
        Assertions.assertEquals("Usuario valido", usuario.getNome());
        Assertions.assertEquals("usuario@email.com", usuario.getEmail());
        Assertions.assertEquals("123456", usuario.getSenha());
    }

}
