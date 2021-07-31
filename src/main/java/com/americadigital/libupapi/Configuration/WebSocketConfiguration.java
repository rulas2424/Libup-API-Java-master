package com.americadigital.libupapi.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/socket")
                .setAllowedOrigins("*")
                .withSockJS();

        stompEndpointRegistry.addEndpoint("/socket")
                .setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");// including /user also works
        registry.setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker("/chat");
//        registry.setApplicationDestinationPrefixes("/conv")
//                .enableSimpleBroker("/conversations");
    }
}