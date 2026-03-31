package br.gustcustodio.barriga.service.external;

import br.gustcustodio.barriga.domain.Conta;

public interface ContaEvent {

    enum EventType {CREATED, UPDATED, DELETED}

    void dispatch(Conta conta, EventType eventType) throws Exception;

}
