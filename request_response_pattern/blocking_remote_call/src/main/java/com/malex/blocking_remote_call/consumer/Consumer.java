package com.malex.blocking_remote_call.consumer;

import com.malex.blocking_remote_call.model.MessageRequest;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = "${custom.rabbitmq.queue.request}")
public class Consumer {

  @RabbitHandler
  public MessageRequest handle(
      MessageRequest event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {

    log.info("  <<< Received message: {}", event);
    log.info("  <<< Delivery tag: {}", tag);
    log.info("  <<< Channel channel: {}", channel);

    sleep();

    return new MessageRequest(event.id(), "Replay: " + event.text());
  }

  @SneakyThrows
  private void sleep() {
    Thread.sleep(3000);
  }
}
