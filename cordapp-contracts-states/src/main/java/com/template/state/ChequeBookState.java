package com.template.state;

import net.corda.core.contracts.ContractState;

import java.util.Arrays;
import java.util.List;

// *********
// * State *
// *********
import com.template.schema.IOUSchemaV1;
import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.schemas.QueryableState;
import net.corda.core.identity.Party;

import java.util.Date;

/**
 * The state object recording IOU agreements between two parties.
 *
 * A state must implement [ContractState] or one of its descendants.
 */
public class ChequeBookState implements LinearState, QueryableState {

    private final Party registerBank;
    private final Party cbeBank;
    private long chequeSerialNofrom;
    private long chequeSerialNoTo;
    private String accountNumber;
    private long customerId;
    private String customerName;
    private long branchCode;
    private String bankId;
    private String chequeCurrency;
    private long chequeBookSerialNo;
    private final UniqueIdentifier linearId;

    /**
     * @param value    the value of the IOU.
     * @param lender   the party issuing the IOU.
     * @param borrower the party receiving and approving the IOU.
     */
    public ChequeBookState(
                           Party registerBank,
                           Party cbeBank, long chequeSerialNofrom, long chequeSerialNoTo, String accountNumber, long customerId,
                           String customerName, long branchCode, String bankId, String chequeCurrency, long chequeBookSerialNo,
                           UniqueIdentifier linearId) {

        this.registerBank = registerBank;
        this.cbeBank = cbeBank;
        this.linearId = new UniqueIdentifier();
        this.chequeSerialNofrom = chequeSerialNofrom;
        this.chequeSerialNoTo = chequeSerialNoTo;
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.customerName = customerName;
        this.branchCode = branchCode;
        this.bankId = bankId;
        this.chequeCurrency = chequeCurrency;
        this.chequeBookSerialNo = chequeBookSerialNo;

    }


    public long getChequeSerialNofrom() {
        return chequeSerialNofrom;
    }

    public void setChequeSerialNofrom(long chequeSerialNofrom) {
        this.chequeSerialNofrom = chequeSerialNofrom;
    }

    public long getChequeSerialNoTo() {
        return chequeSerialNoTo;
    }

    public void setChequeSerialNoTo(long chequeSerialNoTo) {
        this.chequeSerialNoTo = chequeSerialNoTo;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(long branchCode) {
        this.branchCode = branchCode;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getChequeCurrency() {
        return chequeCurrency;
    }

    public void setChequeCurrency(String chequeCurrency) {
        this.chequeCurrency = chequeCurrency;
    }

    public long getChequeBookSerialNo() {
        return chequeBookSerialNo;
    }

    public void setChequeBookSerialNo(long chequeBookSerialNo) {
        this.chequeBookSerialNo = chequeBookSerialNo;
    }


    public Party getRegisterBank() {
        return registerBank;
    }

    public Party getCbeBank() {
        return cbeBank;
    }

    @Override
    public UniqueIdentifier getLinearId() {
        return linearId;
    }

    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(registerBank, cbeBank);
    }

    @Override
    public PersistentState generateMappedObject(MappedSchema schema) {
        if (schema instanceof IOUSchemaV1) {
            return new IOUSchemaV1.PersistentIOU(
                    this.registerBank.getName().toString(),
                    this.cbeBank.getName().toString(),
                    this.chequeBookSerialNo, this.chequeCurrency, this.bankId, this.branchCode, this.customerName, this.customerId, this.accountNumber,
                    this.chequeSerialNoTo, this.chequeSerialNofrom,  this.linearId.getId());
        } else {
            throw new IllegalArgumentException("Unrecognised schema $schema");
        }
    }

    @Override
    public Iterable<MappedSchema> supportedSchemas() {
        return ImmutableList.of(new IOUSchemaV1());
    }

    @Override
    public String toString() {
        return String.format("IOUState( registerBank=%s, cbeBank=%s, linearId=%s chequeSerialNofrom=%s chequeCurrency=%s bankId=%s branchCode=%s customerName=%s customerId=%s accountNumber=%s chequeSerialNoFrom=%s chequeSerialNoto=%s)",
                 registerBank, cbeBank, linearId, chequeSerialNofrom, chequeCurrency, bankId, branchCode, customerName, customerId, accountNumber, chequeSerialNofrom, chequeSerialNoTo);
    }
}