package org.infinispan.protostream.domain.marshallers;

import java.io.IOException;
import java.util.Date;

import org.infinispan.protostream.MessageMarshaller;
import org.infinispan.protostream.domain.Transaction;

/**
 * @author anistor@redhat.com
 */
public class TransactionMarshaller implements MessageMarshaller<Transaction> {

   @Override
   public String getTypeName() {
      return "sample_bank_account.Transaction";
   }

   @Override
   public Class<? extends Transaction> getJavaClass() {
      return Transaction.class;
   }

   @Override
   public Transaction readFrom(ProtoStreamReader reader) throws IOException {
      int id = reader.readInt("id");
      String description = reader.readString("description");
      String longDescription = reader.readString("longDescription");
      int accountId = reader.readInt("accountId");
      Date date = reader.readDate("date");
      double amount = reader.readDouble("amount");
      boolean isDebit = reader.readBoolean("isDebit");
      boolean isValid = reader.readBoolean("isValid");

      Transaction transaction = new Transaction();
      transaction.setId(id);
      transaction.setDescription(description);
      transaction.setLongDescription(longDescription);
      transaction.setAccountId(accountId);
      transaction.setDate(date);
      transaction.setAmount(amount);
      transaction.setDebit(isDebit);
      transaction.setValid(isValid);
      return transaction;
   }

   @Override
   public void writeTo(ProtoStreamWriter writer, Transaction transaction) throws IOException {
      writer.writeInt("id", transaction.getId());
      writer.writeString("description", transaction.getDescription());
      writer.writeString("longDescription", transaction.getLongDescription());
      writer.writeInt("accountId", transaction.getAccountId());
      writer.writeDate("date", transaction.getDate());
      writer.writeDouble("amount", transaction.getAmount());
      writer.writeBoolean("isDebit", transaction.isDebit());
      writer.writeBoolean("isValid", transaction.isValid());
   }
}
