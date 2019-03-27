package com.template.flow;

import co.paralleluniverse.fibers.Suspendable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.corda.core.contracts.Amount;
import net.corda.core.flows.*;
import net.corda.core.identity.AnonymousParty;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.utilities.ProgressTracker;
import net.corda.core.utilities.ProgressTracker.Step;
import com.template.state.ChequeState;
import java.security.PublicKey;
import java.time.Duration;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import net.corda.core.contracts.ContractState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.contracts.Command;
import com.template.contract.ChequeSubmitContract;
import static com.template.contract.ChequeSubmitContract.ID;
import static net.corda.core.contracts.ContractsDSL.requireThat;

    public class ChequeSubmitFlow {


        @InitiatingFlow
        @StartableByRPC
        public static class Initiator extends FlowLogic<SignedTransaction> {

            private final ChequeState iouValue;
            private final Party otherParty;

            private final ProgressTracker.Step GENERATING_TRANSACTION = new ProgressTracker.Step("Generating transaction based on new IOU.");
            private final ProgressTracker.Step VERIFYING_TRANSACTION = new ProgressTracker.Step("Verifying contract constraints.");
            private final ProgressTracker.Step SIGNING_TRANSACTION = new ProgressTracker.Step("Signing transaction with our private key.");
            private final ProgressTracker.Step GATHERING_SIGS = new ProgressTracker.Step("Gathering the counterparty's signature.") {
                @Override
                public ProgressTracker childProgressTracker() {
                    return CollectSignaturesFlow.Companion.tracker();
                }
            };
            private final ProgressTracker.Step FINALISING_TRANSACTION = new ProgressTracker.Step("Obtaining notary signature and recording transaction.") {
                @Override
                public ProgressTracker childProgressTracker() {
                    return FinalityFlow.Companion.tracker();
                }
            };

            // The progress tracker checkpoints each stage of the flow and outputs the specified messages when each
            // checkpoint is reached in the code. See the 'progressTracker.currentStep' expressions within the call()
            // function.
            private final ProgressTracker progressTracker = new ProgressTracker(
                    GENERATING_TRANSACTION,
                    VERIFYING_TRANSACTION,
                    SIGNING_TRANSACTION,
                    GATHERING_SIGS,
                    FINALISING_TRANSACTION
            );

            public Initiator(ChequeState iouValue, Party otherParty) {
                this.iouValue = iouValue;
                this.otherParty = otherParty;
            }

            @Override
            public ProgressTracker getProgressTracker() {
                return progressTracker;
            }

            /**
             * The flow logic is encapsulated within the call() method.
             */
            @Suspendable
            @Override
            public SignedTransaction call() throws FlowException {
                // Obtain a reference to the notary we want to use.
                final Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);

                // Stage 1.
                progressTracker.setCurrentStep(GENERATING_TRANSACTION);
                // Generate an unsigned transaction.
                Party me = getOurIdentity();



                ChequeState iouState = new ChequeState(me, otherParty,iouValue.getChequeSerialNumber(),
                        iouValue.getPayToUserName(),iouValue.getPayToUserAccount(),iouValue.getPayToUserBankName(),
                        iouValue.getChequeAmount(),iouValue.getChequeDueDate(),iouValue.getChequeCurrency(),
                        iouValue.getChequeOwnerAccount(),iouValue.getChequeOwnerName(),iouValue.getChequeOwnerBankName(),new UniqueIdentifier());
                final Command<ChequeSubmitContract.Commands.Create> txCommand = new Command<>(
                        new ChequeSubmitContract.Commands.Create(),
                        ImmutableList.of(iouState.getFromBank().getOwningKey(), iouState.getToBank().getOwningKey()));
                final TransactionBuilder txBuilder = new TransactionBuilder(notary)
                        .addOutputState(iouState, ID)
                        .addCommand(txCommand);

                // Stage 2.
                progressTracker.setCurrentStep(VERIFYING_TRANSACTION);
                // Verify that the transaction is valid.
                txBuilder.verify(getServiceHub());

                // Stage 3.
                progressTracker.setCurrentStep(SIGNING_TRANSACTION);
                // Sign the transaction.
                final SignedTransaction partSignedTx = getServiceHub().signInitialTransaction(txBuilder);

                // Stage 4.
                progressTracker.setCurrentStep(GATHERING_SIGS);
                // Send the state to the counterparty, and receive it back with their signature.
                FlowSession otherPartySession = initiateFlow(otherParty);
                final SignedTransaction fullySignedTx = subFlow(
                        new CollectSignaturesFlow(partSignedTx, ImmutableSet.of(otherPartySession), CollectSignaturesFlow.Companion.tracker()));

                // Stage 5.
                progressTracker.setCurrentStep(FINALISING_TRANSACTION);
                // Notarise and record the transaction in both parties' vaults.
                return subFlow(new FinalityFlow(fullySignedTx));
            }
        }

        @InitiatedBy(com.template.flow.ChequeSubmitFlow.Initiator.class)
        public static class Acceptor extends FlowLogic<SignedTransaction> {

            private final FlowSession otherPartyFlow;

            public Acceptor(FlowSession otherPartyFlow) {
                this.otherPartyFlow = otherPartyFlow;
            }

            @Suspendable
            @Override
            public SignedTransaction call() throws FlowException {
                class SignTxFlow extends SignTransactionFlow {
                    private SignTxFlow(FlowSession otherPartyFlow, ProgressTracker progressTracker) {
                        super(otherPartyFlow, progressTracker);
                    }

                    @Override
                    protected void checkTransaction(SignedTransaction stx) {
                        requireThat(require -> {
                            ContractState output = stx.getTx().getOutputs().get(0).getData();
                            require.using("This must be an IOU transaction.", output instanceof ChequeState);
                            ChequeState iou = (ChequeState) output;
                            // require.using("I won't accept IOUs with a value over 100.", iou.getValue() <= 100);
                            return null;
                        });
                    }
                }

                return subFlow(new SignTxFlow(otherPartyFlow, SignTransactionFlow.Companion.tracker()));
            }
        }

    }

