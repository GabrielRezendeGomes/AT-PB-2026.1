package com.PetFriends.Transporte.Domain.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "entrega")
public class Entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pedido_id", nullable = false, unique = true)
    private Long pedidoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEntrega status;

    @Embedded
    private MoradaEntrega moradaDestino;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_entrega_efetiva")
    private LocalDateTime dataEntregaEfetiva;

    protected Entrega() {}

    public Entrega(Long pedidoId, MoradaEntrega moradaDestino) {
        if (pedidoId == null) throw new IllegalArgumentException("O ID do pedido é obrigatório.");
        if (moradaDestino == null) throw new IllegalArgumentException("A morada de destino é obrigatória.");

        this.pedidoId = pedidoId;
        this.moradaDestino = moradaDestino;
        this.status = StatusEntrega.PENDENTE;
        this.dataCriacao = LocalDateTime.now();
    }
    public void atualizarMoradaDestino(MoradaEntrega novaMorada) {
        if (this.status != StatusEntrega.PENDENTE) {
            throw new IllegalStateException("Não é possível alterar a morada de uma entrega que já foi enviada.");
        }
        if (novaMorada == null) throw new IllegalArgumentException("A nova morada não pode ser nula.");
        this.moradaDestino = novaMorada;
    }
    public void despachar() {
        if (this.status != StatusEntrega.PENDENTE) {
            throw new IllegalStateException("Apenas entregas pendentes podem ser despachadas.");
        }
        this.status = StatusEntrega.EM_TRANSITO;
    }
    public void finalizarEntrega() {
        if (this.status != StatusEntrega.EM_TRANSITO) {
            throw new IllegalStateException("Apenas entregas em trânsito podem ser finalizadas.");
        }
        this.status = StatusEntrega.ENTREGUE;
        this.dataEntregaEfetiva = LocalDateTime.now();
    }
    public Long getId() { return id; }
    public Long getPedidoId() { return pedidoId; }
    public StatusEntrega getStatus() { return status; }
    public MoradaEntrega getMoradaDestino() { return moradaDestino; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataEntregaEfetiva() { return dataEntregaEfetiva; }
}
