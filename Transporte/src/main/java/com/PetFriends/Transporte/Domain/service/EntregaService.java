package com.PetFriends.Transporte.Domain.service;

import com.PetFriends.Transporte.Domain.Model.Entrega;
import com.PetFriends.Transporte.Domain.Model.MoradaEntrega;
import com.PetFriends.Transporte.Domain.Repository.EntregaRepository;
import com.PetFriends.Transporte.Domain.infra.messaging.event.PedidoProntoParaEnvioEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntregaService {

    private static final Logger logger = LoggerFactory.getLogger(EntregaService.class);
    private final EntregaRepository entregaRepository;

    public EntregaService(EntregaRepository entregaRepository) {
        this.entregaRepository = entregaRepository;
    }
    @Transactional
    public void registrarNovaEntrega(PedidoProntoParaEnvioEvent evento) {
        Long pedidoId = evento.getDados().getPedidoId();
        logger.info("Iniciando o registo de entrega para o Pedido ID: {}", pedidoId);

        if (entregaRepository.findByPedidoId(pedidoId).isPresent()) {
            logger.warn("A entrega para o Pedido ID {} já se encontra registada. Ignorando evento duplicado.", pedidoId);
            return;
        }

        var moradaDto = evento.getDados().getMoradaDestino();

        MoradaEntrega morada = new MoradaEntrega(
                moradaDto.getRua(),
                moradaDto.getNumero(),
                moradaDto.getCidade(),
                moradaDto.getCodigoPostal()
        );

        Entrega novaEntrega = new Entrega(pedidoId, morada);

        entregaRepository.save(novaEntrega);

        logger.info("Entrega registada com sucesso para o Pedido ID: {}. Status atual: {}",
                pedidoId, novaEntrega.getStatus());
    }
}