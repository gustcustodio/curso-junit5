package br.gustcustodio.barriga.service;

import br.gustcustodio.barriga.domain.Conta;
import br.gustcustodio.barriga.domain.exceptions.ValidationException;
import br.gustcustodio.barriga.service.repositories.ContaRepository;
import br.gustcustodio.barriga.service.external.ContaEvent;

import java.time.LocalDateTime;
import java.util.List;

public class ContaService {

    private ContaRepository contaRepository;
    private ContaEvent contaEvent;

    public ContaService(ContaRepository contaRepository, ContaEvent contaEvent) {
        this.contaRepository = contaRepository;
        this.contaEvent = contaEvent;
    }

    public Conta salvar(Conta conta) {
        List<Conta> contas = contaRepository.obterContasPorUsuario(conta.getUsuario().getId());
        contas.stream().forEach(contaExistente -> {
            if (conta.getNome().equalsIgnoreCase(contaExistente.getNome())) {
                throw new ValidationException("Usuário já possui uma conta com este nome");
            }
        });
        Conta novaConta = new Conta(conta.getId(), conta.getNome() + LocalDateTime.now(), conta.getUsuario());
        Conta contaPersistida = contaRepository.salvar(novaConta);
        try {
            contaEvent.dispatch(contaPersistida, ContaEvent.EventType.CREATED);
        } catch (Exception e) {
            contaRepository.delete(contaPersistida);
            throw new RuntimeException("Falha na criação da conta, tente novamente");
        }
        return contaPersistida;
    }

}
