package org.infinispan.protostream;

import java.io.IOException;

import org.infinispan.protostream.descriptors.FieldDescriptor;
import org.infinispan.protostream.impl.BaseMarshallerDelegate;
import org.infinispan.protostream.impl.SerializationContextImpl;

public class ProxyMarshallerDelegate<T> implements BaseMarshallerDelegate<T> {
	private BaseMarshaller<T> base;
	private SerializationContext ctx;
	
	public ProxyMarshallerDelegate(BaseMarshaller<T> base, SerializationContext ctx){
		this.base = base;
		this.ctx = ctx;
	}
	
	@Override
	public void marshall(FieldDescriptor fd, T value, MessageMarshaller.ProtoStreamWriter writer,
			RawProtoStreamWriter out) throws IOException {
		if (this.base instanceof RawProtobufMarshaller){
			RawProtobufMarshaller<T> marshaller = (RawProtobufMarshaller<T>)this.base;
			marshaller.writeTo(this.ctx, out, value);
		} else if (this.base instanceof MessageMarshaller){
			MessageMarshaller<T> marshaller = (MessageMarshaller<T>)this.base;
			marshaller.writeTo(writer, value);
		} else if (this.base instanceof EnumMarshaller){
	          EnumMarshaller marshaller = (EnumMarshaller)this.base;
	          int enumValue = marshaller.encode((Enum)value);
	          out.writeEnum(fd.getNumber(), enumValue);
		} else {
			throw new IllegalArgumentException("unknown marshaller registered"); 
		}
	}

	@Override
	public T unmarshall(FieldDescriptor fd, MessageMarshaller.ProtoStreamReader reader, RawProtoStreamReader in)
			throws IOException {
		if (this.base instanceof RawProtobufMarshaller){
			RawProtobufMarshaller<T> marshaller = (RawProtobufMarshaller<T>)this.base;
			return marshaller.readFrom(this.ctx, in);
		} else if (this.base instanceof MessageMarshaller){
			MessageMarshaller<T> marshaller = (MessageMarshaller<T>)this.base;
			return marshaller.readFrom(reader);
		} else if (this.base instanceof EnumMarshaller){
	          EnumMarshaller marshaller = (EnumMarshaller)this.base;
	          return (T)marshaller.decode(in.readEnum());
		} else {
			throw new IllegalArgumentException("unknown marshaller registered"); 
		}
	}
	
	public static <T> BaseMarshallerDelegate<T> getMarshallerDelegate(SerializationContext ctx, Class<T> clazz){
		if (ctx instanceof SerializationContextImpl){
			return ((SerializationContextImpl)ctx).getMarshallerDelegate(clazz);
		} else {
			return new ProxyMarshallerDelegate<T>(ctx.getMarshaller(clazz), ctx);
		}
	}

	public static <T> BaseMarshallerDelegate<T> getMarshallerDelegate(SerializationContext ctx, String descriptorFullName){
		if (ctx instanceof SerializationContextImpl){
			return ((SerializationContextImpl)ctx).getMarshallerDelegate(descriptorFullName);
		} else {
			return new ProxyMarshallerDelegate<T>(ctx.getMarshaller(descriptorFullName), ctx);
		}
	}
	
	@Override
	public BaseMarshaller<T> getMarshaller() {
		return this.base;
	}
}
