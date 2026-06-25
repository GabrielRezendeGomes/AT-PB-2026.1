package com.PetFriends.Almoxarifado.domain.infra.messaging.event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PedidoCriadoEvent {

    private String eventoId;
    private String tipoEvento;
    private LocalDateTime dataHora;
    private DadosPedido dados;

    // Construtor vazio exigido pelo Jackson para desserialização
    public PedidoCriadoEvent() {}

    // Getters e Setters
    public String getEventoId() { return eventoId; }
    public void setEventoId(String eventoId) { this.eventoId = eventoId; }

    public String getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(String tipoEvento) { this.tipoEvento = tipoEvento; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public DadosPedido getDados() { return dados; }
    public void setDados(DadosPedido dados) { this.dados = dados; }

    // --- Classes Internas para mapear a estrutura do payload ---

    public static class DadosPedido {
        private Long pedidoId;
        private List<ItemPedido> itens;

        public DadosPedido() {}

        public Long getPedidoId() { return pedidoId; }
        public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

        public List<ItemPedido> getItens() { return itens; }
        public void setItens(List<ItemPedido> itens) { this.itens = itens; }
    }

    public static class ItemPedido {
        private Long produtoId;
        private Integer quantidade;

        public ItemPedido() {}

        public Long getProdutoId() { return produtoId; }
        public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }

        public Integer getQuantidade() { return quantidade; }
        public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    }
}
