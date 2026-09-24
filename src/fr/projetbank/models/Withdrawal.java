package fr.projetbank.models;

import java.math.BigDecimal;
import java.util.Date;

import fr.projetbank.models.Operation.typesOperations;

public class Withdrawal extends Operation{

	
	
	/**
	 * Retrait
	 */
	public Withdrawal(Date dateOperation, BigDecimal amountTransaction, BankAccount bankAccount) {
		super(dateOperation, amountTransaction, bankAccount);
	}
	
	@Override
    public typesOperations getTypeOperation() {
        return typesOperations.WITHDRAWAL;
    }
}
