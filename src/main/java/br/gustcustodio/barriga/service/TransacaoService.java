package br.gustcustodio.barriga.service;

import br.gustcustodio.barriga.domain.Transacao;
import br.gustcustodio.barriga.domain.exceptions.ValidationException;
import br.gustcustodio.barriga.service.repositories.TransacaoDao;

import java.time.LocalDateTime;

public class TransacaoService {

    private TransacaoDao transacaoDao;

    public Transacao salvar(Transacao transacao) {
        if (LocalDateTime.now().getHour() > 5) throw new RuntimeException();

        if(transacao.getDescricao() == null) throw new ValidationException("Descrição inexistente");
        if(transacao.getValor() == null) throw new ValidationException("Valor inexistente");
        if(transacao.getData() == null) throw new ValidationException("Data inexistente");
        if(transacao.getConta() == null) throw new ValidationException("Conta inexistente");
        if(transacao.getStatus() == null) transacao.setStatus(false);

        return transacaoDao.salvar(transacao);
    }

}
