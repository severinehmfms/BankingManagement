package fr.projetbank.models;

import java.math.BigDecimal;
import java.util.Date;

import fr.projetbank.models.Operation.typesOperations;

public class Transfer extends Operation{
	
	/**
	 * Virement bancaire
	 */
	
	private String numBankAccountDestination;
	
	public Transfer(Date dateOperation, BigDecimal amountTransaction, String numBankAccount, String numBankAccountDestination) {
		super(dateOperation, amountTransaction, numBankAccount);
		this.numBankAccountDestination = numBankAccountDestination;
	}

	@Override
    public typesOperations getTypeOperation() {
        return typesOperations.TRANSFER;
    }	
	
	public String getNumBankAccountDestination() {
		return numBankAccountDestination;
	}

	public void setNumBankAccountDestination(String numBankAccountDestination) {
		this.numBankAccountDestination = numBankAccountDestination;
	}

	@Override
	public String toString() {
		return "Compte lié " + super.getNumBankAccount() + " - " + this.getTypeOperation() + " - Date : " + super.dateOperation
				+ " - Montant : " + super.amountTransaction + "€ - Destinataire : " + this.getNumBankAccountDestination() + "\n";
	}
}
