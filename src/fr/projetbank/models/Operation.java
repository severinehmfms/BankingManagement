package fr.projetbank.models;

import java.util.Date;

public class Operation {
	
	/**
	 * Opération bancaire
	 */
	
	public static enum TypeTransaction {
		DEPOSIT, WITHDRAWAL, TRANSFER
	}
	
	private Date dateTransaction;
	private double amountTransaction;
	private BankAccount bankAccount;

}
