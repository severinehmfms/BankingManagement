package fr.projetbank.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {
	
	/**
	 * Compte bancaire
	 */
	
	//Constante : Solde maximal autorisé sur le compte
	public static final BigDecimal MAX_BALANCE = new BigDecimal("500000");
	
	private String numBankAccount;				//Numéro de compte sous la forme FR-XXXX-XXXX
	private String holder;						//Nom du titulaire du compte
	private BigDecimal balance;					//Solde du compte (mis à jour après chaque opération)
	private BigDecimal maximumBalance;			//Solde maximal autorisé sur le compte
	private List<Operation> lstOperations;	//Liste des opérations associées à ce compte
	
	/**
	 * Constructeur simple pour création
	 * @param numBankAccount
	 * @param holder	
	 */
	public BankAccount(String numBankAccount, String holder) {
		this.numBankAccount = numBankAccount;
		this.holder = holder;
		this.balance = new BigDecimal("0");
		this.maximumBalance = MAX_BALANCE;
		this.lstOperations = new ArrayList<Operation>();
	}

	/**
	 * Constructeur complet
	 * @param numBankAccount
	 * @param holder
	 * @param balance
	 * @param maximumBalance
	 * @param lstOperations
	 */
	public BankAccount(String numBankAccount, String holder, BigDecimal balance, BigDecimal maximumBalance, List<Operation> lstOperations) {
		this.numBankAccount = numBankAccount;
		this.holder = holder;
		this.balance = balance;
		this.maximumBalance = maximumBalance;
		this.lstOperations = lstOperations;
	}	
	
	/**
	 * Méthode toString
	 */
	public String toString() {
		return "Numéro de compte : " + this.numBankAccount + " - Titulaire : " + this.holder + " - Solde : " + this.balance + "€";
	}
	
	public String getDetailsBankAccount() {
		String details =  "Numéro de compte : " + this.numBankAccount + " - Titulaire : " + this.holder + " - Solde : " + this.balance + "€\n";
		
		details += "Liste des opérations associées à ce compte : \n";
		for (Operation op : this.lstOperations) {
			details += op;
		}
		
		
		return details;
	}

	public String getNumBankAccount() {
		return numBankAccount;
	}


	public void setNumBankAccount(String numBankAccount) {
		this.numBankAccount = numBankAccount;
	}


	public String getHolder() {
		return holder;
	}


	public void setHolder(String holder) {
		this.holder = holder;
	}


	public BigDecimal getBalance() {
		return balance;
	}


	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}


	public BigDecimal getMaximumBalance() {
		return maximumBalance;
	}


	public void setMaximumBalance(BigDecimal maximumBalance) {
		this.maximumBalance = maximumBalance;
	}


	public List<Operation> getLstOperations() {
		return lstOperations;
	}


	public void setLstOperations(ArrayList<Operation> lstOperations) {
		this.lstOperations = lstOperations;
	}
	
	
	
	
}
