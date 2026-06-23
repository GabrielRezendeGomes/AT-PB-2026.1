package com.PetFriends.Almoxarifado.domain.infra.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String FILA_PEDIDO_CRIADO = "almoxarifado.pedido.criado.fila";
    public static final String EXCHANGE_PEDIDOS = "pedidos.exchange";
    public static final String ROUTING_KEY_PEDIDO_CRIADO = "pedido.criado";

    @Bean
    public Queue pedidoCriadoQueue() {
        return new Queue(FILA_PEDIDO_CRIADO, true);
    }
    @Bean
    public TopicExchange pedidosExchange() {
        return new TopicExchange(EXCHANGE_PEDIDOS);
    }

    @Bean
    public Binding bindingPedidoCriado(Queue pedidoCriadoQueue, TopicExchange pedidosExchange) {
        return BindingBuilder
                .bind(pedidoCriadoQueue)
                .to(pedidosExchange)
                .with(ROUTING_KEY_PEDIDO_CRIADO);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}