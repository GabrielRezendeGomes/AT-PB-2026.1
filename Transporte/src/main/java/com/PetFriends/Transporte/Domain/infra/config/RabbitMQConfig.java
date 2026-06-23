package com.PetFriends.Transporte.Domain.infra.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQConfig {

    public static final String FILA_PEDIDO_PRONTO = "transporte.pedido.pronto.fila";

    public static final String EXCHANGE_PEDIDOS = "pedidos.exchange";

    public static final String ROUTING_KEY_PEDIDO_PRONTO = "pedido.pronto.envio";

    @Bean
    public Queue pedidoProntoQueue() {
        return new Queue(FILA_PEDIDO_PRONTO, true);
    }

    @Bean
    public TopicExchange pedidosExchange() {
        return new TopicExchange(EXCHANGE_PEDIDOS);
    }


    @Bean
    public Binding bindingPedidoPronto(Queue pedidoProntoQueue, TopicExchange pedidosExchange) {
        return BindingBuilder
                .bind(pedidoProntoQueue)
                .to(pedidosExchange)
                .with(ROUTING_KEY_PEDIDO_PRONTO);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

}
