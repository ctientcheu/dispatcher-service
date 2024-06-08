package com.polarbookshop.dispatcherservice;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

/**
 * @author clement.tientcheu@cerebrau.com
 * @project dispatcher-service
 * @org Cerebrau
 */
@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class FunctionsStreamIntegrationTests {

  @Autowired private InputDestination input;
  @Autowired private OutputDestination output;
  @Autowired private ObjectMapper objectMapper;

  @Test
  void whenOrderAccepted_thenDispatched() throws IOException {
    long orderId = 121L;
    Message<OrderAcceptedMessage> inputMessage =
        MessageBuilder.withPayload(new OrderAcceptedMessage(orderId)).build();
    Message<OrderDispatchedMessage> expectedOutputMessage =
        MessageBuilder.withPayload(new OrderDispatchedMessage(orderId)).build();
    this.input.send(inputMessage);

    assertThat(objectMapper.readValue(output.receive().getPayload(), OrderDispatchedMessage.class))
        .isEqualTo(expectedOutputMessage.getPayload());
  }
}
