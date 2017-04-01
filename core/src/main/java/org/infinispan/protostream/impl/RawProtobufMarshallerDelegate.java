package org.infinispan.protostream.impl;

import java.io.IOException;

import org.infinispan.protostream.MessageMarshaller;
import org.infinispan.protostream.RawProtoStreamReader;
import org.infinispan.protostream.RawProtoStreamWriter;
import org.infinispan.protostream.RawProtobufMarshaller;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.descriptors.FieldDescriptor;

/**
 * @author anistor@redhat.com
 * @since 1.0
 */
final class RawProtobufMarshallerDelegate<T> implements BaseMarshallerDelegate<T> {

   private final RawProtobufMarshaller<T> marshaller;

   private final SerializationContext ctx;

   public RawProtobufMarshallerDelegate(SerializationContext ctx, RawProtobufMarshaller<T> marshaller) {
      this.ctx = ctx;
      this.marshaller = marshaller;
   }

   @Override
   public RawProtobufMarshaller<T> getMarshaller() {
      return marshaller;
   }

   @Override
   public void marshall(FieldDescriptor fieldDescriptor, T value, MessageMarshaller.ProtoStreamWriter writer, RawProtoStreamWriter out) throws IOException {
      marshaller.writeTo(ctx, out, value);
   }

   @Override
   public T unmarshall(FieldDescriptor fieldDescriptor, MessageMarshaller.ProtoStreamReader reader, RawProtoStreamReader in) throws IOException {
      return marshaller.readFrom(ctx, in);
   }
}
