package fr.projetbank.models;

import java.math.BigDecimal;
import java.util.Date;

import fr.projetbank.models.Operation.typesOperations;

public class Transfer extends Operation{
	
	/**
	 * Virement bancaire
	 */
	
	private BankAccount bankAccountDestination;
	
	public Transfer(Date dateOperation, BigDecimal amountTransaction, BankAccount bankAccount, BankAccount bankAccountDestination) {
		super(dateOperation, amountTransaction, bankAccount);
		this.bankAccountDestination = bankAccountDestination;
	}

	@Override
    public typesOperations getTypeOperation() {
        return typesOperations.TRANSFER;
    }
	
	public BankAccount getBankAccountDestination() {
		return bankAccountDestination;
	}

	public void setBankAccountDestination(BankAccount bankAccountDestination) {
		this.bankAccountDestination = bankAccountDestination;
	}	
	
	@Override
	public String getNumBankAccountDestination() {
	    return bankAccountDestination.getNumBankAccount();
	}
	
	@Override
	public String toString() {
		return "Compte lié " + super.getBankAccount().getNumBankAccount() + " - " + this.getTypeOperation() + " - Date : " + super.dateOperation
				+ " - Montant : " + super.amountTransaction + "€ - Destinataire : " + this.getBankAccountDestination().getNumBankAccount() + "\n";
	}
}
