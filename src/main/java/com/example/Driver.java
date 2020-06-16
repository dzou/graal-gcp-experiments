package com.example;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PublishRequest;
import com.google.pubsub.v1.PublishResponse;
import com.google.pubsub.v1.PublisherGrpc;
import com.google.pubsub.v1.PublisherGrpc.PublisherStub;
import com.google.pubsub.v1.PubsubMessage;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.auth.MoreCallCredentials;
import io.grpc.stub.StreamObserver;
import java.io.IOException;

public class Driver {

  static ManagedChannel channel;

  static PublisherStub publisherStub;

  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("Hello world.");

    channel = ManagedChannelBuilder.forTarget("dns:///pubsub.googleapis.com:443").build();
    publisherStub = PublisherGrpc.newStub(channel)
        .withCallCredentials(
            MoreCallCredentials.from(GoogleCredentials.getApplicationDefault()));

    PublishRequest publishRequest =
        PublishRequest.newBuilder()
            .setTopic("projects/my-kubernetes-codelab-217414/topics/exampleTopic")
            .addMessages(
                PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8("main Methddddod")).build())
            .build();

    publisherStub.publish(publishRequest, new UnaryStreamObserver());

    Thread.sleep(5000);
  }

  private static class UnaryStreamObserver implements StreamObserver<PublishResponse> {

    @Override
    public void onNext(PublishResponse publishResponse) {
      System.out.println("Publish completed, response received.");
    }

    @Override
    public void onError(Throwable throwable) {
      System.err.println(throwable);
    }

    @Override
    public void onCompleted() {
      System.out.println("Done!!");
    }
  }

}
