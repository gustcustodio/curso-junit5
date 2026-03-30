package br.gustcustodio.barriga.service;

import br.gustcustodio.barriga.domain.Conta;
import br.gustcustodio.barriga.domain.exceptions.ValidationException;
import br.gustcustodio.barriga.repositories.ContaRepository;

import java.util.List;

public class ContaService {

    private ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Conta salvar(Conta conta) {
        List<Conta> contas = contaRepository.obterContasPorUsuario(conta.getUsuario().getId());
        System.out.println(contas);
        contas.stream().forEach(contaExistente -> {
            if (conta.getNome().equalsIgnoreCase(contaExistente.getNome())) {
                throw new ValidationException("Usuário já possui uma conta com este nome");
            }
        });
        return contaRepository.salvar(conta);
    }

}
