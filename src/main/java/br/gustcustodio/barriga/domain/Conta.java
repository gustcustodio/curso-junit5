package br.gustcustodio.barriga.domain;

import br.gustcustodio.barriga.domain.exceptions.ValidationException;

import java.util.Objects;

public class Conta {

    private Long id;
    private String nome;
    private Usuario usuario;

    public Conta(Long id, String nome, Usuario usuario) {
        if (nome == null) throw new ValidationException("Nome é obrigatório");
        if (usuario == null) throw new ValidationException("Usuário é obrigatório");

        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Conta conta = (Conta) o;
        return Objects.equals(getNome(), conta.getNome()) && Objects.equals(getUsuario(), conta.getUsuario());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getNome());
        result = 31 * result + Objects.hashCode(getUsuario());
        return result;
    }

    @Override
    public String toString() {
        return "Conta{" + "id=" + id + ", nome='" + nome + '\'' + ", usuario=" + usuario + '}';
    }

}