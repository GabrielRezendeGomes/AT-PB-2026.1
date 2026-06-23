package com.PetFriends.Almoxarifado.domain.infra.messaging;

import com.PetFriends.Almoxarifado.domain.infra.messaging.event.PedidoCriadoEvent;
import com.PetFriends.Almoxarifado.domain.service.EstoqueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoCriadoListener {

    private static final Logger logger = LoggerFactory.getLogger(PedidoCriadoListener.class);
    private final EstoqueService estoqueService;


    public PedidoCriadoListener(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }
    @RabbitListener(queues = "almoxarifado.pedido.criado.fila")
    public void receberEventoPedidoCriado(PedidoCriadoEvent evento) {
        try {

            estoqueService.processarReservaDePedido(evento);

        } catch (IllegalStateException | IllegalArgumentException e) {

            logger.error("Erro de validação ao processar o Pedido {}: {}",
                    evento.getDados().getPedidoId(), e.getMessage());


        } catch (Exception e) {
            logger.error("Erro inesperado ao processar o Pedido {}: {}",
                    evento.getDados().getPedidoId(), e.getMessage());
            throw e;
        }
    }
}