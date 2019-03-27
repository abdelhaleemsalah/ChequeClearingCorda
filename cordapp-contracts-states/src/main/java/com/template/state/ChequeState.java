package com.template.state;

import com.google.common.collect.ImmutableList;
import com.template.schema.IOUSchemaV1;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.schemas.QueryableState;
import com.template.schema.ChequeSchema;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ChequeState implements LinearState, QueryableState {


    private final Party fromBank;
    private final Party toBank;
    private  long chequeSerialNumber;
    private  String payToUserName;
    private  String payToUserAccount;
    private  String payToUserBankName;
    private  long chequeAmount;
    private  Date chequeDueDate;
    private  String chequeCurrency;
    private  String chequeOwnerAccount;
    private  String chequeOwnerName;
    private  String chequeOwnerBankName;
    private  UniqueIdentifier linearId;




    public ChequeState(
            Party fromBank,
            Party toBank, long chequeSerialNumber, String payToUserName, String payToUserAccount, String payToUserBankName,
            long chequeAmount, Date chequeDueDate, String chequeCurrency, String chequeOwnerAccount, String chequeOwnerName,
            String chequeOwnerBankName,UniqueIdentifier linearId) {

        this.fromBank = fromBank;
        this.toBank = toBank;
        this.linearId = new UniqueIdentifier();
        this.chequeSerialNumber = chequeSerialNumber;
        this.payToUserName = payToUserName;
        this.payToUserAccount = payToUserAccount;
        this.payToUserBankName = payToUserBankName;
        this.chequeAmount = chequeAmount;
        this.chequeDueDate = chequeDueDate;
        this.chequeCurrency = chequeCurrency;
        this.chequeOwnerAccount = chequeOwnerAccount;
        this.chequeOwnerName = chequeOwnerName;
        this.chequeOwnerBankName=chequeOwnerBankName;

    }


    public Party getFromBank() {
        return fromBank;
    }


    public Party getToBank() {
        return toBank;
    }



    public long getChequeSerialNumber() {
        return chequeSerialNumber;
    }

    public void setChequeSerialNumber(long chequeSerialNumber) {
        this.chequeSerialNumber=chequeSerialNumber;
    }
    public String getPayToUserName() {
        return payToUserName;
    }

    public void setPayToUserName(String payToUserName) {
        this.payToUserName=payToUserName;
    }

    public String getPayToUserAccount() {
        return payToUserAccount;
    }

    public void setPayToUserAccount() {
        this.payToUserAccount=payToUserAccount;
    }


    public String getPayToUserBankName() {
        return payToUserBankName;
    }

    public void setPayToUserBankName() {
        this.payToUserBankName=payToUserBankName;
    }

    public long getChequeAmount() {
        return chequeAmount;
    }

    public void setChequeAmount(long chequeAmount) {
        this.chequeAmount=chequeAmount;
    }
    public Date getChequeDueDate() {
        return chequeDueDate;
    }
    public void setChequeDueDate(Date chequeDueDate) {
        this.chequeDueDate=chequeDueDate;
    }
    public String getChequeCurrency() {
        return chequeCurrency;
    }

    public void setChequeCurrency(String chequeCurrency) {
        this.chequeCurrency= chequeCurrency;
    }
    public String getChequeOwnerAccount() {
        return chequeOwnerAccount;
    }
    public void setChequeOwnerAccount(String chequeOwnerAccount) {
        this.chequeOwnerAccount= chequeOwnerAccount;
    }
    public String getChequeOwnerName() {
        return chequeOwnerName;
    }
    public void setChequeOwnerName(String chequeOwnerName) {
        this.chequeOwnerName=chequeOwnerName;
    }

    public String getChequeOwnerBankName() {
        return chequeOwnerBankName;
    }
    public void getChequeOwnerBankName(String chequeOwnerBankName) {
        this.chequeOwnerBankName= chequeOwnerBankName;
    }
    @Override
    public UniqueIdentifier getLinearId() {
        return linearId;
    }

    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(fromBank, toBank);
    }



    @Override
    public PersistentState generateMappedObject(MappedSchema schema) {
        if (schema instanceof ChequeSchema) {
            return new ChequeSchema.PersistentIOU(
                    this.fromBank.getName().toString(),
                    this.toBank.getName().toString(),
                    this.chequeSerialNumber, this.payToUserName, this.payToUserAccount, this.payToUserBankName,
                    this.chequeAmount, this.chequeDueDate, this.chequeCurrency,
                    this.chequeOwnerAccount, this.chequeOwnerName, this.chequeOwnerBankName, this.linearId.getId());
        } else {
            throw new IllegalArgumentException("Unrecognised schema $schema");
        }
    }

    @Override
    public Iterable<MappedSchema> supportedSchemas() {
        return ImmutableList.of(new ChequeSchema());
    }

    @Override
    public String toString() {
        return String.format("chequeState( fromBank=%s, toBank=%s, linearId=%s chequeSerialNumber=%s payToUserName=%s payToUserAccount=%s payToUserBankName=%s chequeAmount=%s chequeDueDate=%s chequeCurrency=%s chequeOwnerAccount=%s chequeOwnerName=%s chequeOwnerBankName=%s)",
                fromBank, toBank, linearId, chequeSerialNumber, payToUserName, payToUserAccount, payToUserBankName, chequeAmount, chequeDueDate, chequeCurrency, chequeOwnerAccount, chequeOwnerName,chequeOwnerBankName);
    }
}



