# Compatability Testing GraalVM Images with GCP Libraries

In this sample we'll try to build a GraalVM native image of a Spring Boot application that uses GRPC Pub/Sub.

## Run Locally

```
mvn package
java -jar target/graal-gcp-experiment-1.0-SNAPSHOT.jar 
```

## Build and run GraalVM Image

Instructions for building the image. This requires some minor patches to netty to work.

0. In a new terminal, create a new directory to hold the repos that you will clone.

    ```
    $ mkdir tmp
    $ cd tmp/
    ```

1. In a new terminal, run the commands to install the patched version of netty:

    ```
    $ git clone https://github.com/dzou/netty.git
    $ cd netty
    $ ./mvnw clean install -DskipTests
    $ cd ..
    ```

2. Then we will rebuild `grpc-netty-shaded`.

    ```
    $ git clone https://github.com/dzou/grpc-java
    $ cd grpc-java
    $ ./gradlew publishMavenPublicationToMavenLocal -PskipCodegen=true -PskipAndroid=true
    $ cd ..
    ```

3. Then clone this project.

    ```
    $ git clone https://github.com/dzou/graal-gcp-experiments.git
    $ cd graal-gcp-experiments
    ```

4. Edit `com.example.Driver` and modify the `setTopic(...)` call in main() to a Pub/Sub topic for your own project.

    ```
        PublishRequest publishRequest =
            PublishRequest.newBuilder()
                .setTopic("projects/my-kubernetes-codelab-217414/topics/exampleTopic")
                .addMessages(
                    PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8("This was published from the native image.")).build())
                .build();
    ```
    
5. Run `mvn package -Pgraal` to build the image.

6. Run `./target/com.example.driver` to run the image.

7. Check the [Cloud Console Pub/Sub UI](https://console.cloud.google.com/cloudpubsub/topic/list) to see the newly sent message.
