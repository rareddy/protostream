package org.infinispan.protostream.annotations;

import org.infinispan.protostream.descriptors.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

/**
 * Defines a Protocol Buffers message field. A class must have at least one field/property annotated with {@link
 * ProtoField} in order to be considered a Protocol Buffers message type.
 *
 * @author anistor@redhat.com
 * @since 3.0
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProtoField {

   /**
    * The Protocol Buffers tag number.
    */
   int number();

   Type type() default Type.MESSAGE;

   boolean required() default false;

   String name() default "";

   String defaultValue() default "";

   Class<?> javaType() default UNSPECIFIED_TYPE.class;

   Class<? extends Collection> collectionImplementation() default UNSPECIFIED_COLLECTION.class;

   // dummy class to be used as 'unspecified' marker only
   public static abstract class UNSPECIFIED_COLLECTION implements Collection {
      private UNSPECIFIED_COLLECTION() {
      }
   }

   // dummy class to be used as 'unspecified' marker only
   public static abstract class UNSPECIFIED_TYPE {
      private UNSPECIFIED_TYPE() {
      }
   }
}