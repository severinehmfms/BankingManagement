package fr.projetbank.exceptions;

public class BankAccountAlreadyExistsException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public BankAccountAlreadyExistsException(String msg) {
		super(msg);
	}
	public BankAccountAlreadyExistsException() {
		super("Il existe déjà un compte associé à ce numéro de compte");
	}
}
