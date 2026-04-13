package br.gustcustodio.barriga.service;

import br.gustcustodio.barriga.domain.Transacao;
import br.gustcustodio.barriga.domain.builders.TransacaoBuilder;
import br.gustcustodio.barriga.service.repositories.TransacaoDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private TransacaoDao transacaoDao;

    @Test
    public void deveSalvarUmaTransacaoValida() {
        Transacao transacaoParaSalvar = TransacaoBuilder.umaTransacao().comId(null).agora();
        when(transacaoDao.salvar(transacaoParaSalvar)).thenReturn(TransacaoBuilder.umaTransacao().agora());

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
    }

}
