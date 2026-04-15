package br.gustcustodio.barriga.service;

import br.gustcustodio.barriga.domain.Conta;
import br.gustcustodio.barriga.domain.Transacao;
import br.gustcustodio.barriga.domain.builders.TransacaoBuilder;
import br.gustcustodio.barriga.domain.exceptions.ValidationException;
import br.gustcustodio.barriga.service.repositories.TransacaoDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static br.gustcustodio.barriga.domain.builders.ContaBuilder.umaConta;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// @EnabledIf(value = "isHoraValida")
@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private TransacaoDao transacaoDao;

/*
    @BeforeEach
    void checkTime() {
        Assumptions.assumeTrue(LocalDateTime.now().getHour() < 5);
    }
*/

    @Test
    public void deveSalvarUmaTransacaoValida() {
        Transacao transacaoParaSalvar = TransacaoBuilder.umaTransacao().comId(null).agora();
        when(transacaoDao.salvar(transacaoParaSalvar)).thenReturn(TransacaoBuilder.umaTransacao().agora());

        LocalDateTime dataDesejada = LocalDateTime.of(2023, 1, 1, 4, 30, 28);
        System.out.println(dataDesejada);
        System.out.println(LocalDateTime.now());

        try(MockedStatic<LocalDateTime> ldt = Mockito.mockStatic(LocalDateTime.class)) {
            ldt.when(() -> LocalDateTime.now()).thenReturn(dataDesejada);
            System.out.println(LocalDateTime.now());

            Transacao transacaoSalva = transacaoService.salvar(transacaoParaSalvar);
            assertEquals(TransacaoBuilder.umaTransacao().agora(), transacaoSalva);
            assertAll("Transacao",
                    () -> assertEquals(1L, transacaoSalva.getId()),
                    () -> assertEquals("Transação Válida", transacaoSalva.getDescricao()),
                    () -> {
                        assertAll("Conta",
                                () -> assertEquals("Conta Válida", transacaoSalva.getConta().getNome()),
                                () -> {
                                    assertAll("Usuário",
                                            () -> assertEquals("Usuário Válido", transacaoSalva.getConta().getUsuario().getNome()),
                                            () -> assertEquals("12345678", transacaoSalva.getConta().getUsuario().getSenha())
                                    );
                                }
                        );
                    }
            );
            ldt.verify(() -> LocalDateTime.now(), Mockito.times(2));
        }
        System.out.println(LocalDateTime.now());
    }

    @ParameterizedTest(name = "{6}")
    @MethodSource(value = "cenariosObrigatorios")
    public void deveValidarCamposObrigatoriosAoSalvar(Long id, String descricao, Double valor,
                                                      LocalDate data, Conta conta, Boolean status, String mensagem) {
        String exMessage = Assertions.assertThrows(ValidationException.class, () -> {
            Transacao transacao = TransacaoBuilder.umaTransacao().comId(id).comDescricao(descricao).comValor(valor)
                    .comData(data).comStatus(status).comConta(conta).agora();
            transacaoService.salvar(transacao);
        }).getMessage();
        Assertions.assertEquals(mensagem, exMessage);
    }

    static Stream<Arguments> cenariosObrigatorios() {
        return Stream.of(
                Arguments.of(1L, null, 10D, LocalDate.now(), umaConta().agora(), true, "Descrição inexistente"),
                Arguments.of(1L, "Descrição", null, LocalDate.now(), umaConta().agora(), true, "Valor inexistente"),
                Arguments.of(1L, "Descrição", 10D, null, umaConta().agora(), true, "Data inexistente"),
                Arguments.of(1L, "Descrição", 10D, LocalDate.now(), null, true, "Conta inexistente")
        );
    }

    public static boolean isHoraValida() {
        return LocalDateTime.now().getHour() < 5;
    }

}
