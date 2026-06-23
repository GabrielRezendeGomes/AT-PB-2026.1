package com.PetFriends.Transporte.Domain.infra.messaging.event;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PedidoProntoParaEnvioEvent {

    private String eventoId;
    private String tipoEvento;
    private LocalDateTime dataHora;
    private DadosEnvio dados;

    public PedidoProntoParaEnvioEvent() {}

    public static class DadosEnvio {
        private Long pedidoId;
        private MoradaDto moradaDestino;

        public DadosEnvio() {}

        public Long getPedidoId() { return pedidoId; }
        public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
        public MoradaDto getMoradaDestino() { return moradaDestino; }
        public void setMoradaDestino(MoradaDto moradaDestino) { this.moradaDestino = moradaDestino; }
    }

    public static class MoradaDto {
        private String rua;
        private String numero;
        private String cidade;
        private String codigoPostal;

        public MoradaDto() {}

        public String getRua() { return rua; }
        public void setRua(String rua) { this.rua = rua; }
        public String getNumero() { return numero; }
        public void setNumero(String numero) { this.numero = numero; }
        public String getCidade() { return cidade; }
        public void setCidade(String cidade) { this.cidade = cidade; }
        public String getCodigoPostal() { return codigoPostal; }
        public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
    }
}
