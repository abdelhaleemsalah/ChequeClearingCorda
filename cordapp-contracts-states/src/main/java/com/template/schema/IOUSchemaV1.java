
package com.template.schema;
import com.google.common.collect.ImmutableList;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

/**
 * An IOUState schema.
 */
public class IOUSchemaV1 extends MappedSchema {
    public IOUSchemaV1() {
        super(IOUSchema.class, 1, ImmutableList.of(PersistentIOU.class));
    }

    @Entity
    @Table(name = "iou_states")
    public static class PersistentIOU extends PersistentState {
        @Column(name = "registerBank") private final String registerBank;
        @Column(name = "cbeBank") private final String cbeBank;
        @Column(name = "chequeBookSerialNo") private final long chequeBookSerialNo;
        @Column(name = "chequeCurrency") private final String chequeCurrency;
        @Column(name = "bankId") private final String bankId;
        @Column(name = "branchCode") private final long branchCode;
        @Column(name = "customerName") private final String customerName;
        @Column(name = "customerId") private final long customerId;
        @Column(name = "accountNumber") private final String accountNumber;
        @Column(name = "chequeSerialNoTo") private final long chequeSerialNoTo;
        @Column(name = "chequeSerialNofrom") private final long chequeSerialNofrom;
        @Column(name = "linear_id") private final UUID linearId;



        public PersistentIOU(String registerBank, String cbeBank,
                            long chequeBookSerialNo, String chequeCurrency,String bankId,
                             long branchCode,String customerName,long customerId,String accountNumber,
                             long chequeSerialNoTo,long chequeSerialNofrom,UUID linearId) {
            this.registerBank = registerBank;
            this.cbeBank = cbeBank;
            this.chequeBookSerialNo=chequeBookSerialNo;
            this.chequeCurrency=chequeCurrency;
            this.bankId=bankId;
            this.branchCode=branchCode;
            this.customerName=customerName;
            this.customerId=customerId;
            this.accountNumber=accountNumber;
            this.chequeSerialNoTo=chequeSerialNoTo;
            this.chequeSerialNofrom=chequeSerialNofrom;
            this.linearId = linearId;
        }

        // Default constructor required by hibernate.
        public PersistentIOU() {
            this.registerBank = null;
            this.cbeBank = null;
            this.chequeBookSerialNo = 0;
            this.chequeCurrency=null;
            this.bankId=null;
            this.branchCode=0;
            this.customerName=null;
            this.customerId=0;
            this.accountNumber=null;
            this.chequeSerialNoTo=0;
            this.chequeSerialNofrom=0;
            this.linearId = null;
        }



        public UUID getId() {
            return linearId;
        }

        public long getChequeSerialNofrom() {
            return chequeSerialNofrom;
        }

        public long getChequeSerialNoTo() {
            return chequeSerialNoTo;
        }

         public String getAccountNumber() {
            return accountNumber;
        }

        public long getCustomerId() {
            return customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

       public long getBranchCode() {
            return branchCode;
        }

       public String getBankId() {
            return bankId;
        }

       public String getChequeCurrency() {
            return chequeCurrency;
        }

       public long getChequeBookSerialNo() {
            return chequeBookSerialNo;
        }

       public String getRegisterBank() {
            return registerBank;
        }

        public String getCbeBank() {
            return cbeBank;
        }


    }
}