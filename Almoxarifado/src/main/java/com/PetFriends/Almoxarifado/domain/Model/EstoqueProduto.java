package com.PetFriends.Almoxarifado.domain.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "estoque_produto")
public class EstoqueProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "produto_id", nullable = false, unique = true)
    private Long produtoId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "valor", column = @Column(name = "quantidade_disponivel", nullable = false))
    })
    private Quantidade quantidadeDisponivel;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "valor", column = @Column(name = "quantidade_reservada", nullable = false))
    })
    private Quantidade quantidadeReservada;

    protected EstoqueProduto() {}

    public EstoqueProduto(Long produtoId, Integer quantidadeInicial) {
        this.produtoId = produtoId;
        this.quantidadeDisponivel = new Quantidade(quantidadeInicial);
        this.quantidadeReservada = new Quantidade(0); // Inicia sempre zerada
    }

    public void adicionarEstoque(Integer quantidade) {
        Quantidade qtdAdicionar = new Quantidade(quantidade);
        // Atribui uma nova instância, respeitando a imutabilidade
        this.quantidadeDisponivel = this.quantidadeDisponivel.somar(qtdAdicionar);
    }

    public void reservarParaPedido(Integer quantidade) {
        Quantidade qtdReservar = new Quantidade(quantidade);

        if (!this.quantidadeDisponivel.isMaiorOuIgual(qtdReservar)) {
            throw new IllegalStateException("Estoque insuficiente para o produto solicitado.");
        }

        this.quantidadeDisponivel = this.quantidadeDisponivel.subtrair(qtdReservar);
        this.quantidadeReservada = this.quantidadeReservada.somar(qtdReservar);
    }

    public void efetivarSaida(Integer quantidade) {
        Quantidade qtdSaida = new Quantidade(quantidade);

        if (!this.quantidadeReservada.isMaiorOuIgual(qtdSaida)) {
            throw new IllegalStateException("A quantidade de saída é maior do que a reservada.");
        }

        this.quantidadeReservada = this.quantidadeReservada.subtrair(qtdSaida);
    }

    public void cancelarReserva(Integer quantidade) {
        Quantidade qtdCancelar = new Quantidade(quantidade);

        if (!this.quantidadeReservada.isMaiorOuIgual(qtdCancelar)) {
            throw new IllegalStateException("A quantidade a liberar é maior do que a reservada.");
        }

        this.quantidadeReservada = this.quantidadeReservada.subtrair(qtdCancelar);
        this.quantidadeDisponivel = this.quantidadeDisponivel.somar(qtdCancelar);
    }

    public Long getId() { return id; }
    public Long getProdutoId() { return produtoId; }
    public Integer getQuantidadeDisponivel() { return quantidadeDisponivel.getValor(); }
    public Integer getQuantidadeReservada() { return quantidadeReservada.getValor(); }
}

