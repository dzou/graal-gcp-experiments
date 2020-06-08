package com.example;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PublishRequest;
import com.google.pubsub.v1.PublishResponse;
import com.google.pubsub.v1.PublisherGrpc;
import com.google.pubsub.v1.PublisherGrpc.PublisherStub;
import com.google.pubsub.v1.PubsubMessage;
import io.grpc.CallCredentials;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.auth.MoreCallCredentials;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";

	private final AtomicLong counter = new AtomicLong();

	private final ManagedChannel channel;

	private PublisherStub publisherStub;

	public GreetingController() throws IOException {
		this.channel = ManagedChannelBuilder.forTarget("dns:///pubsub.googleapis.com:443").build();
		this.publisherStub = PublisherGrpc.newStub(this.channel)
				.withCallCredentials(
						MoreCallCredentials.from(GoogleCredentials.getApplicationDefault()));
	}

	@GetMapping("/")
	public String index() {
		return "Hello!";
	}

	@GetMapping("/blah")
	public String blah() {
		return "";
	}

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {

		PublishRequest publishRequest =
				PublishRequest.newBuilder()
						.setTopic("projects/my-kubernetes-codelab-217414/topics/exampleTopic")
						.addMessages(
								PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8("Hello world!")).build())
						.build();

		this.publisherStub.publish(publishRequest, new UnaryStreamObserver());

		return new Greeting(counter.incrementAndGet(), String.format(template, name));
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
