package br.gustcustodio.barriga.domain;

import br.gustcustodio.barriga.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static br.gustcustodio.barriga.domain.builders.ContaBuilder.umaConta;
import static br.gustcustodio.barriga.domain.builders.UsuarioBuilder.umUsuario;

public class ContaTest {

    @Test
    public void deveCriarContaValida() {
        Conta conta = umaConta().agora();
        Assertions.assertAll("Conta",
                () -> Assertions.assertEquals(1L, conta.getId()),
                () -> Assertions.assertEquals("Conta Válida", conta.getNome()),
                () -> Assertions.assertEquals(umUsuario().agora(), conta.getUsuario())
        );
    }

    @ParameterizedTest
    @MethodSource(value = "dataProvider")
    public void deveRejeitarContaInvalida(Long id, String nome, Usuario usuario, String mensagem) {
        String errorMessage = Assertions.assertThrows(ValidationException.class, () ->
                umaConta().comId(id).comNome(nome).comUsuario(usuario).agora()).getMessage();
        Assertions.assertEquals(mensagem, errorMessage);
    }

    private static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of(1L, null, umUsuario().agora(), "Nome é obrigatório"),
                Arguments.of(1L, "Conta Válida", null, "Usuário é obrigatório")
        );
    }

}