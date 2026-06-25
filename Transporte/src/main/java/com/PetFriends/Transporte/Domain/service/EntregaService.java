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
            // 1. Validação defensiva (Evita o NullPointerException)
            if (evento == null || evento.getDados() == null || evento.getDados().getMoradaDestino() == null) {
                // Logue o erro ou lance uma exceção apropriada para o RabbitMQ tratar (ex: mandar para DLQ)
                System.err.println("Erro: Evento recebido com dados de endereço (moradaDestino) ausentes.");
                throw new IllegalArgumentException("Não é possível registrar a entrega: dados de endereço incompletos.");
            }

            // 2. Extração segura dos dados (Linha 34 original)
            var dadosEnvio = evento.getDados();
            var moradaDto = dadosEnvio.getMoradaDestino();

            String rua = moradaDto.getRua();
            String numero = moradaDto.getNumero();
            String cidade = moradaDto.getCidade();
            String codigoPostal = moradaDto.getCodigoPostal();
            Long pedidoId = dadosEnvio.getPedidoId();

            // 3. Continuação da sua lógica de negócio para salvar a entrega...
            System.out.println("Registrando entrega do pedido " + pedidoId + " para a rua: " + rua);
        }
    }