package com.example;

import java.security.Provider;

import com.oracle.svm.core.annotate.Alias;
import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;

import io.grpc.netty.shaded.io.netty.handler.ssl.SslContextBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslProvider;
import java.util.logging.Level;

//@TargetClass(className = "io.grpc.netty.ProtocolNegotiators")
//final class Target_io_grpc_netty_ProtocolNegotiators {
//
//  @Substitute
//  static void logSslEngineDetails(Level level, ChannelHandlerContext ctx, String msg, Throwable t) {
//    Logger log = Logger.getLogger("io.grpc.netty.ProtocolNegotiators");
//    if (log.isLoggable(level)) {
//      log.log(level, msg + "\nNo SSLEngine details available!", t);
//    }
//  }
//}

@TargetClass(className = "io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts")
final class Target_io_grpc_netty_GrpcSslContexts {

  @Substitute
  public static SslContextBuilder configure(SslContextBuilder builder, SslProvider provider) {
    switch (provider) {
      case JDK: {
        Provider jdkProvider = findJdkProvider();
        if (jdkProvider == null) {
          throw new IllegalArgumentException(
              "Could not find Jetty NPN/ALPN or Conscrypt as installed JDK providers");
        }
        return configure(builder, jdkProvider);
      }
      default:
        throw new IllegalArgumentException("Unsupported provider: " + provider);
    }
  }

  @Alias
  private static Provider findJdkProvider() {
    return null;
  }

  @Alias
  public static SslContextBuilder configure(SslContextBuilder builder, Provider jdkProvider) {
    return null;
  }

}

@SuppressWarnings("unused")
class GrpcNettySubstitutions {
}