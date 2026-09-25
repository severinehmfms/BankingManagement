package fr.projetbank.models;

import java.math.BigDecimal;
import java.util.Date;

public class Deposit extends Operation{

	/**
	 * Dépôt
	 */

	public Deposit(Date dateOperation, BigDecimal amountTransaction, String numBankAccount) {
		super(dateOperation, amountTransaction, numBankAccount);
	}
	
	@Override
    public typesOperations getTypeOperation() {
        return typesOperations.DEPOSIT;
    }

	@Override
	public String toString() {
		return "Compte lié " + super.getNumBankAccount() + " - " + this.getTypeOperation() 
				+ " - Date : " + super.dateOperation + " - Montant : " + super.amountTransaction + "€\n";
	}
}
