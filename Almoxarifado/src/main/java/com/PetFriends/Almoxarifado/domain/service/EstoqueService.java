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
        logger.info("Iniciando processamento de reserva para o Pedido ID: {}", evento.getDados().getPedidoId());

        for (PedidoCriadoEvent.ItemPedido item : evento.getDados().getItens()) {

            EstoqueProduto estoque = estoqueRepository.findByProdutoId(item.getProdutoId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Produto ID " + item.getProdutoId() + " não possui controle de estoque cadastrado."
                    ));
            estoque.reservarParaPedido(item.getQuantidade());

            estoqueRepository.save(estoque);

            logger.debug("Reservadas {} unidades do Produto ID: {}", item.getQuantidade(), item.getProdutoId());
        }

        logger.info("Reserva concluída com sucesso para o Pedido ID: {}", evento.getDados().getPedidoId());
    }
}