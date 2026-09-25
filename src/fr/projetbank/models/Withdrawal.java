package fr.projetbank.models;

import java.math.BigDecimal;
import java.util.Date;

import fr.projetbank.models.Operation.typesOperations;

public class Withdrawal extends Operation{

	
	
	/**
	 * Retrait
	 */
	public Withdrawal(Date dateOperation, BigDecimal amountTransaction,  String numBankAccount) {
		super(dateOperation, amountTransaction, numBankAccount);
	}
	
	@Override
    public typesOperations getTypeOperation() {
        return typesOperations.WITHDRAWAL;
    }
	
	@Override
	public String toString() {
		return super.getNumBankAccount() + " - " + this.getTypeOperation() 
				+ " - Date : " + super.dateOperation + " - Montant : " + super.amountTransaction + "€\n";
	}
}
