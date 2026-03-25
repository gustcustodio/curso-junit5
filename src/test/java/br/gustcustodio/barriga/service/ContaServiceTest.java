package br.gustcustodio.barriga.service;

import br.gustcustodio.barriga.domain.Conta;
import br.gustcustodio.barriga.repositories.ContaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.gustcustodio.barriga.domain.builders.ContaBuilder.umaConta;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {

    @InjectMocks
    private ContaService contaService;

    @Mock
    private ContaRepository contaRepository;

    @Test
    public void deveSalvarContaComSucesso() {
        Conta contaToSave = umaConta().comId(null).agora();
        when(contaRepository.salvar(contaToSave)).thenReturn(umaConta().agora());
        Conta savedConta = contaService.salvar(contaToSave);
        Assertions.assertNotNull(savedConta.getId());
    }

}
