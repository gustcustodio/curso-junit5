package br.gustcustodio.barriga.service;

import br.gustcustodio.barriga.domain.Transacao;
import br.gustcustodio.barriga.domain.exceptions.ValidationException;
import br.gustcustodio.barriga.service.external.ClockService;
import br.gustcustodio.barriga.service.repositories.TransacaoDao;

public class TransacaoService {

    private TransacaoDao transacaoDao;
    private ClockService clockService;

    public Transacao salvar(Transacao transacao) {
        if (clockService.getCurrentTime().getHour() > 5) throw new RuntimeException();

        if(transacao.getDescricao() == null) throw new ValidationException("Descrição inexistente");
        if(transacao.getValor() == null) throw new ValidationException("Valor inexistente");
        if(transacao.getData() == null) throw new ValidationException("Data inexistente");
        if(transacao.getConta() == null) throw new ValidationException("Conta inexistente");
        if(transacao.getStatus() == null) transacao.setStatus(false);

        return transacaoDao.salvar(transacao);
    }

}
