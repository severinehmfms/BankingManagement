package fr.projetbank.models;

import java.math.BigDecimal;
import java.util.Date;

public class Deposit extends Operation{

	/**
	 * Dépôt
	 */

	public Deposit(Date dateOperation, BigDecimal amountTransaction, BankAccount bankAccount) {
		super(dateOperation, amountTransaction, bankAccount);
	}
	
	@Override
    public typesOperations getTypeOperation() {
        return typesOperations.DEPOSIT;
    }
}
