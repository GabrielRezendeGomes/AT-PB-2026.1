package com.PetFriends.Almoxarifado.domain.service;

import com.PetFriends.Almoxarifado.domain.Model.EstoqueProduto;
import com.PetFriends.Almoxarifado.domain.Repository.EstoqueProdutoRepository;
import com.PetFriends.Almoxarifado.domain.infra.messaging.event.PedidoCriadoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstoqueService {

    private static final Logger logger = LoggerFactory.getLogger(EstoqueService.class);
    private final EstoqueProdutoRepository estoqueRepository;

    public EstoqueService(EstoqueProdutoRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    @Transactional
    public void processarReservaDePedido(PedidoCriadoEvent evento) {

        if (evento == null || evento.getDados() == null || evento.getDados().getItens() == null) {
            logger.error("Falha Crítica: Evento PedidoCriadoEvent recebido com estrutura ou lista de itens nula.");
            throw new IllegalArgumentException("O payload do evento está incompleto ou inválido.");
        }

        logger.info("Iniciando processamento de reserva para o Pedido ID: {}", evento.getDados().getPedidoId());


        for (PedidoCriadoEvent.ItemPedido item : evento.getDados().getItens()) {

            if (item.getProdutoId() == null || item.getQuantidade() == null) {
                logger.warn("Item do pedido ignorado por falta de dados básicos (produtoId ou quantidade nulos).");
                continue;
            }

            EstoqueProduto estoque = estoqueRepository.findByProdutoId(item.getProdutoId())
                    .orElseThrow(() -> {
                        logger.error("Erro de Consistência: Produto ID {} não possui controle de estoque cadastrado.", item.getProdutoId());
                        return new IllegalArgumentException("Produto ID " + item.getProdutoId() + " não possui controle de estoque cadastrado.");
                    });

            try {
                estoque.reservarParaPedido(item.getQuantidade());

                estoqueRepository.save(estoque);

                logger.debug("Reservadas {} unidades do Produto ID: {}", item.getQuantidade(), item.getProdutoId());

            } catch (IllegalStateException e) {
                logger.error("Falha ao reservar estoque para o Produto ID {}: {}", item.getProdutoId(), e.getMessage());


                throw e;
            }
        }

        logger.info("Reserva concluída com sucesso para o Pedido ID: {}", evento.getDados().getPedidoId());
    }
}