package com.example;

import com.oracle.svm.core.annotate.Alias;
import com.oracle.svm.core.annotate.RecomputeFieldValue;
import com.oracle.svm.core.annotate.RecomputeFieldValue.Kind;
import com.oracle.svm.core.annotate.TargetClass;

@TargetClass(className = "io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueueProducerFields")
final class Target_io_grpc_netty_shaded_io_netty_util_internal_shaded_org_jctools_queues_BaseMpscLinkedArrayQueueProducerFields {

  @Alias @RecomputeFieldValue(kind = Kind.FieldOffset, name = "producerIndex")
  private static long P_INDEX_OFFSET;
}

@SuppressWarnings("unused")
class GrpcNettySubstitutions {}
