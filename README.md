# Compatability Testing GraalVM Images with GCP Libraries

In this sample we'll try to build a GraalVM native image of a Spring Boot application that uses GRPC Pub/Sub.

## Run Locally

```
mvn package
java -jar target/graal-gcp-experiment-1.0-SNAPSHOT.jar 
```

## Build and run GraalVM Image

Build the image:

```
mvn package -Pgraal
```

Run it:

```
./target/com.example.driver
```

Right now it still doesn't work, you should get the following error:

```
java.lang.UnsatisfiedLinkError: io.grpc.netty.shaded.io.netty.internal.tcnative.NativeStaticallyReferencedJniMethods.sslOpCipherServerPreference()I [symbol: Java_io_grpc_netty_shaded_io_netty_internal_tcnative_NativeStaticallyReferencedJniMethods_sslOpCipherServerPreference or Java_io_grpc_netty_shaded_io_netty_internal_tcnative_NativeStaticallyReferencedJniMethods_sslOpCipherServerPreference__]
	at com.oracle.svm.jni.access.JNINativeLinkage.getOrFindEntryPoint(JNINativeLinkage.java:145) ~[na:na]
	at com.oracle.svm.jni.JNIGeneratedMethodSupport.nativeCallAddress(JNIGeneratedMethodSupport.java:57) ~[na:na]
	at io.grpc.netty.shaded.io.netty.internal.tcnative.NativeStaticallyReferencedJniMethods.sslOpCipherServerPreference(NativeStaticallyReferencedJniMethods.java) ~[na:na]
```
