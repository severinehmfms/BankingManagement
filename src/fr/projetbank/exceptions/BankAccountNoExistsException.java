package fr.projetbank.exceptions;

public class BankAccountNoExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public BankAccountNoExistsException(String msg) {
		super(msg);
	}
	public BankAccountNoExistsException() {
		super("Aucun compte n'est associé à ce numéro de compte");
	}
}
