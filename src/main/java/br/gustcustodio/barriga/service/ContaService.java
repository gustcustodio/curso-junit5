package br.gustcustodio.barriga.service;

import br.gustcustodio.barriga.domain.Conta;
import br.gustcustodio.barriga.domain.exceptions.ValidationException;
import br.gustcustodio.barriga.repositories.ContaRepository;
import br.gustcustodio.barriga.service.external.ContaEvent;

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
//        System.out.println(contas);
        contas.stream().forEach(contaExistente -> {
            if (conta.getNome().equalsIgnoreCase(contaExistente.getNome())) {
                throw new ValidationException("Usuário já possui uma conta com este nome");
            }
        });
        Conta contaPersistida = contaRepository.salvar(conta);
        contaEvent.dispatch(contaPersistida, ContaEvent.EventType.CREATED);
        return contaPersistida;
    }

}
