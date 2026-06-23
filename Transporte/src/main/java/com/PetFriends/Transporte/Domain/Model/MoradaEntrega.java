package com.PetFriends.Transporte.Domain.Model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class MoradaEntrega {
    private String rua;
    private String numero;
    private String cidade;
    private String codigoPostal;

    protected MoradaEntrega() {}

    public MoradaEntrega(String rua, String numero, String cidade, String codigoPostal) {
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.codigoPostal = codigoPostal;
    }
    public String getRua() { return rua; }
    public String getNumero() { return numero; }
    public String getCidade() { return cidade; }
    public String getCodigoPostal() { return codigoPostal; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoradaEntrega that = (MoradaEntrega) o;
        return Objects.equals(rua, that.rua) &&
                Objects.equals(numero, that.numero) &&
                Objects.equals(cidade, that.cidade) &&
                Objects.equals(codigoPostal, that.codigoPostal);
    }
    @Override
    public int hashCode() {
        return Objects.hash(rua, numero, cidade, codigoPostal);
    }
}
