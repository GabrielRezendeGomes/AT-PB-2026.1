package com.PetFriends.Almoxarifado.domain.Model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Quantidade {
    private Integer valor;

    protected Quantidade() {}

    public Quantidade(Integer valor) {
        if (valor == null || valor < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser nula ou negativa.");
        }
        this.valor = valor;
    }
    public Quantidade somar(Quantidade outra) {
        return new Quantidade(this.valor + outra.valor);
    }

    public Quantidade subtrair(Quantidade outra) {
        if (this.valor < outra.valor) {
            throw new IllegalStateException("A subtração resultaria em uma quantidade negativa.");
        }
        return new Quantidade(this.valor - outra.valor);
    }

    public boolean isMaiorOuIgual(Quantidade outra) {
        return this.valor >= outra.valor;
    }

    public Integer getValor() {
        return valor;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantidade that = (Quantidade) o;
        return Objects.equals(valor, that.valor);
    }
    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
