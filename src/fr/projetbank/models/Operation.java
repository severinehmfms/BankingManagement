package fr.projetbank.models;

import java.math.BigDecimal;
import java.util.Date;

public abstract class Operation {
	
	/**
	 * Opération bancaire
	 */
	
	public static enum typesOperations {
		DEPOSIT, WITHDRAWAL, TRANSFER
	}
	
	protected int idOperation;
	protected Date dateOperation;
	protected BigDecimal amountTransaction;
	protected String numBankAccount;
	
	/**
	 * Constructeur quand l'identifiant n'est pas encore connu
	 * @param dateOperation
	 * @param amountTransaction
	 * @param bankAccount
	 */
	public Operation(Date dateOperation, BigDecimal amountTransaction, String numBankAccount) {
		this.idOperation = 0;
		this.dateOperation = dateOperation;
		this.amountTransaction = amountTransaction;
		this.numBankAccount = numBankAccount;
	}
	
	/**
	 * Constructeur quand l'identifiant est connu
	 * @param identifiant
	 * @param dateOperation
	 * @param amountTransaction
	 * @param bankAccount
	 */
	public Operation(int idOperation, Date dateOperation, BigDecimal amountTransaction, String numBankAccount) {
		this.idOperation = idOperation;
		this.dateOperation = dateOperation;
		this.amountTransaction = amountTransaction;
		this.numBankAccount = numBankAccount;
	}	
	
	/**
	 * Méthode toString abstraite
	 */
	public abstract String toString();

	public int getIdOperation() {
		return idOperation;
	}

	public void setIdOperation(int idOperation) {
		this.idOperation = idOperation;
	}

	public Date getDateOperation() {
		return dateOperation;
	}
	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}
	public BigDecimal getAmountTransaction() {
		return amountTransaction;
	}
	public void setAmountTransaction(BigDecimal amountTransaction) {
		this.amountTransaction = amountTransaction;
	}
	public String getNumBankAccount() {
		return numBankAccount;
	}
	public void setNumBankAccount(String numBankAccount) {
		this.numBankAccount = numBankAccount;
	}

	//Permet à chaque classe fille de connaître le type d'opération
	public abstract typesOperations getTypeOperation();
	
	//Renvoie null si la méthode est pas redéfinie par la classe fille (pour la classe fille Transfer elle ne renverra pas null)
	public String getNumBankAccountDestination() {
	    return null;
	}
	
}
