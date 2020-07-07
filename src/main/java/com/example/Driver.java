package com.example;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;

public class Driver {

  public static void main(String[] args) throws Exception {
    System.out.println("Hello world.");

    Publisher publisher =
        Publisher
            .newBuilder("projects/my-kubernetes-codelab-217414/topics/exampleTopic")
            .build();

    String message = "HELLO FROM CLIENT LIBS";
    ByteString data = ByteString.copyFromUtf8(message);
    PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

    publisher.publish(pubsubMessage);

    ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
    String messageId = messageIdFuture.get();
    System.out.println("Published message ID: " + messageId);

    System.out.println("Good bye world.");
  }
}
