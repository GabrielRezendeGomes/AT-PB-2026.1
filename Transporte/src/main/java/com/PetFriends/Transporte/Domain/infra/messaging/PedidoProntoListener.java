package com.PetFriends.Transporte.Domain.infra.messaging;

import com.PetFriends.Transporte.Domain.infra.config.RabbitMQConfig;
import com.PetFriends.Transporte.Domain.infra.messaging.event.PedidoProntoParaEnvioEvent;
import com.PetFriends.Transporte.Domain.service.EntregaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoProntoListener {

    private static final Logger logger = LoggerFactory.getLogger(PedidoProntoListener.class);
    private final EntregaService entregaService;

    public PedidoProntoListener(EntregaService entregaService) {
        this.entregaService = entregaService;
    }

    @RabbitListener(queues = RabbitMQConfig.FILA_PEDIDO_PRONTO)
    public void processarEventoPedidoPronto(PedidoProntoParaEnvioEvent evento) {
        try {
            entregaService.registrarNovaEntrega(evento);

        } catch (IllegalArgumentException e) {

            logger.error("Erro de validação ao registar entrega do Pedido {}: {}",
                    evento.getDados().getPedidoId(), e.getMessage());

        } catch (Exception e) {
            logger.error("Erro inesperado ao processar o registo de entrega do Pedido {}: {}",
                    evento.getDados().getPedidoId(), e.getMessage());
            throw e;
        }
    }
}
