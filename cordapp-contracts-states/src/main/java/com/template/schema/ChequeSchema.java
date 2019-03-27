package com.template.schema;

import com.google.common.collect.ImmutableList;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;
import java.util.Date;

public class ChequeSchema extends MappedSchema {
    public ChequeSchema() {
        super(IOUSchema.class, 1, ImmutableList.of(ChequeSchema.PersistentIOU.class));
    }

    @Entity
    @Table(name = "cheque_state")
    public static class PersistentIOU extends PersistentState {

        @Column(name = "fromBank") private final String fromBank;
        @Column(name = "toBank") private final String toBank;
        @Column(name = "chequeSerialNumber") private long  chequeSerialNumber;
        @Column(name = "payToUserName") private  String payToUserName;
        @Column(name = "payToUserAccount") private  String payToUserAccount;
        @Column(name = "payToUserBankName") private  String payToUserBankName;
        @Column(name = "chequeAmount") private   long chequeAmount;
        @Column(name = "chequeDueDate") private  Date chequeDueDate;
        @Column(name = "chequeCurrency") private  String chequeCurrency;
        @Column(name = "chequeOwnerAccount") private  String chequeOwnerAccount;
        @Column(name = "chequeOwnerName") private  String chequeOwnerName;
        @Column(name = "chequeOwnerBankName") private  String chequeOwnerBankName;
        @Column(name = "linear_id") private final UUID linearId;


        public PersistentIOU(String fromBank,String toBank,long  chequeSerialNumber,String payToUserName,String payToUserAccount,
                             String payToUserBankName,long chequeAmount,Date chequeDueDate,String chequeCurrency,String chequeOwnerAccount,
                             String chequeOwnerName,String chequeOwnerBankName,UUID linearId) {
            this.fromBank=fromBank;
            this.toBank=toBank;
            this.setChequeSerialNumber(chequeSerialNumber);
            this.setPayToUserName(payToUserName);
            this.setPayToUserAccount(payToUserAccount);
            this.setPayToUserBankName(payToUserBankName);
            this.setChequeAmount(chequeAmount);
            this.setChequeDueDate(chequeDueDate);
            this.setChequeCurrency(chequeCurrency);
            this.setChequeOwnerAccount(chequeOwnerAccount);
            this.setChequeOwnerName(chequeOwnerName);
            this.setChequeOwnerBankName(chequeOwnerBankName);
            this.linearId = linearId;
        }
        // Default constructor required by hibernate.
        public PersistentIOU() {
            this.fromBank=null;
            this.toBank=null;
            this.setChequeSerialNumber(0);
            this.setPayToUserName(null);
            this.setPayToUserAccount(null);
            this.setPayToUserBankName(null);
            this.setChequeAmount(0);
            this.setChequeDueDate(null);
            this.setChequeCurrency(null);
            this.setChequeOwnerAccount(null);
            this.setChequeOwnerName(null);
            this.setChequeOwnerBankName(null);
            this.linearId = null;

        }

        public String getFromBank() {
            return fromBank;
        }

        public String getToBank() {
            return toBank;
        }

        public long getChequeSerialNumber() {
            return chequeSerialNumber;
        }

        public void setChequeSerialNumber(long chequeSerialNumber) {
            this.chequeSerialNumber = chequeSerialNumber;
        }

        public String getPayToUserName() {
            return payToUserName;
        }

        public void setPayToUserName(String payToUserName) {
            this.payToUserName = payToUserName;
        }

        public String getPayToUserAccount() {
            return payToUserAccount;
        }

        public void setPayToUserAccount(String payToUserAccount) {
            this.payToUserAccount = payToUserAccount;
        }

        public String getPayToUserBankName() {
            return payToUserBankName;
        }

        public void setPayToUserBankName(String payToUserBankName) {
            this.payToUserBankName = payToUserBankName;
        }

        public long getChequeAmount() {
            return chequeAmount;
        }

        public void setChequeAmount(long chequeAmount) {
            this.chequeAmount = chequeAmount;
        }

        public Date getChequeDueDate() {
            return chequeDueDate;
        }

        public void setChequeDueDate(Date chequeDueDate) {
            this.chequeDueDate = chequeDueDate;
        }

        public String getChequeCurrency() {
            return chequeCurrency;
        }

        public void setChequeCurrency(String chequeCurrency) {
            this.chequeCurrency = chequeCurrency;
        }

        public String getChequeOwnerAccount() {
            return chequeOwnerAccount;
        }

        public void setChequeOwnerAccount(String chequeOwnerAccount) {
            this.chequeOwnerAccount = chequeOwnerAccount;
        }

        public String getChequeOwnerName() {
            return chequeOwnerName;
        }

        public void setChequeOwnerName(String chequeOwnerName) {
            this.chequeOwnerName = chequeOwnerName;
        }

        public String getChequeOwnerBankName() {
            return chequeOwnerBankName;
        }

        public void setChequeOwnerBankName(String chequeOwnerBankName) {
            this.chequeOwnerBankName = chequeOwnerBankName;
        }
    }
}