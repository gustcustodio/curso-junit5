package br.gustcustodio.barriga.infra;

import br.gustcustodio.barriga.domain.Usuario;
import br.gustcustodio.barriga.domain.exceptions.ValidationException;
import br.gustcustodio.barriga.service.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static br.gustcustodio.barriga.domain.builders.UsuarioBuilder.umUsuario;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceComUserMemoryRepositoryTest {

    private static UsuarioService usuarioService = new UsuarioService(new UsuarioMemoryRepository());

    @Test
    @Order(1)
    public void deveSalvarUsuarioValido() {
        Usuario user = usuarioService.salvar(umUsuario().comId(null).agora());
        Assertions.assertNotNull(user.getId());
    }

    @Test
    @Order(2)
    public void deveRejeitarUsuarioExistente() {
        ValidationException ex = Assertions.assertThrows(ValidationException.class, () ->
                usuarioService.salvar(umUsuario().comId(null).agora()));
        Assertions.assertEquals("Usuário usuario@email.com já cadastrado!", ex.getMessage());
    }

}
