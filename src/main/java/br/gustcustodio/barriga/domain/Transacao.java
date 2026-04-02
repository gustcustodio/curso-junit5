package br.gustcustodio.barriga.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Transacao {

    private Long id;
    private String descricao;
    private Double valor;
    private Conta conta;
    private LocalDate data;
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Transacao transacao = (Transacao) o;
        return Objects.equals(getId(), transacao.getId())
                && Objects.equals(getDescricao(), transacao.getDescricao())
                && Objects.equals(getValor(), transacao.getValor())
                && Objects.equals(getConta(), transacao.getConta())
                && Objects.equals(getData(), transacao.getData())
                && Objects.equals(getStatus(), transacao.getStatus());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getId());
        result = 31 * result + Objects.hashCode(getDescricao());
        result = 31 * result + Objects.hashCode(getValor());
        result = 31 * result + Objects.hashCode(getConta());
        result = 31 * result + Objects.hashCode(getData());
        result = 31 * result + Objects.hashCode(getStatus());
        return result;
    }

}
