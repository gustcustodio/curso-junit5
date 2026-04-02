package br.gustcustodio.barriga.service.repositories;

import br.gustcustodio.barriga.domain.Conta;

import java.util.List;

public interface ContaRepository {

    Conta salvar(Conta conta);

    List<Conta> obterContasPorUsuario(Long usuarioId);

    void delete(Conta conta);

}
