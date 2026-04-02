package br.gustcustodio.barriga.service;

import br.gustcustodio.barriga.domain.Conta;
import br.gustcustodio.barriga.domain.exceptions.ValidationException;
import br.gustcustodio.barriga.service.repositories.ContaRepository;
import br.gustcustodio.barriga.service.external.ContaEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static br.gustcustodio.barriga.domain.builders.ContaBuilder.umaConta;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
public class ContaServiceTest {

    @InjectMocks
    private ContaService contaService;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private ContaEvent contaEvent;

    @Captor
    private ArgumentCaptor<Conta> contaArgumentCaptor;

    @Test
    public void deveSalvarPrimeiraContaComSucesso() throws Exception {
        Conta contaToSave = umaConta().comId(null).agora();
        when(contaRepository.salvar(any(Conta.class))).thenReturn(umaConta().agora());
        doNothing().when(contaEvent).dispatch(umaConta().agora(), ContaEvent.EventType.CREATED);
        Conta savedConta = contaService.salvar(contaToSave);
        Assertions.assertNotNull(savedConta.getId());
        Mockito.verify(contaRepository).salvar(contaArgumentCaptor.capture());
        Assertions.assertNull(contaArgumentCaptor.getValue().getId());
        Assertions.assertTrue(contaArgumentCaptor.getValue().getNome().startsWith("Conta Válida"));
    }

    @Test
    public void deveSalvarSegundaContaComSucesso() {
        Conta contaToSave = umaConta().comId(null).agora();
        when(contaRepository.obterContasPorUsuario(contaToSave.getUsuario().getId())).thenReturn(Arrays.asList(umaConta().comNome("Outra conta").agora()));
        when(contaRepository.salvar(any(Conta.class))).thenReturn(umaConta().agora());
        Conta savedConta = contaService.salvar(contaToSave);
        Assertions.assertNotNull(savedConta.getId());
    }

    @Test
    public void deveRejeitarContaRepetida() {
        Conta contaToSave = umaConta().comId(null).agora();
        when(contaRepository.obterContasPorUsuario(contaToSave.getUsuario().getId())).thenReturn(Arrays.asList(umaConta().agora()));
        String mensagem = Assertions.assertThrows(ValidationException.class, () -> contaService.salvar(contaToSave)).getMessage();
        Assertions.assertEquals("Usuário já possui uma conta com este nome", mensagem);
    }

    @Test
    public void naoDeveManterContaSemEvento() throws Exception {
        Conta contaToSave = umaConta().comId(null).agora();
        Conta contaSalva = umaConta().agora();
        when(contaRepository.salvar(any(Conta.class))).thenReturn(contaSalva);
        doThrow(new Exception("Falha catastrófica")).when(contaEvent).dispatch(contaSalva, ContaEvent.EventType.CREATED);
        String mensagem = Assertions.assertThrows(Exception.class, () -> contaService.salvar(contaToSave)).getMessage();
        Assertions.assertEquals("Falha na criação da conta, tente novamente", mensagem);
        verify(contaRepository).delete(contaSalva);
    }

}
