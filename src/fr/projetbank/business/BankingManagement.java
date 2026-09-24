package fr.projetbank.business;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import fr.projetbank.utils.Functions;
import fr.projetbank.daos.BankAccountDao;
import fr.projetbank.exceptions.BankAccountAlreadyExistsException;
import fr.projetbank.models.BankAccount;

public class BankingManagement {
	
	//On initialise le scanner
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) throws ParseException, BankAccountAlreadyExistsException{
		//Dao pour gérer les comptes bancaires en base
		BankAccountDao bkDao = new BankAccountDao();
		
		String[] menu = {
				"Création d'un compte bancaire",
				"Consultation d'un compte bancaire",
			    "Gestion des opérations",
			    "Historique des opérations"
			};
		
		int choice_user = -1;
		while (choice_user != 0) {
			//On demande à l'utilisateur son choix par rapport au menu proposé
			choice_user = Functions.ask_user_choice(scanner, menu);
			switch(choice_user) {
				case 1:				
					//Création d'un compte bancaire
					createBankAccount(bkDao);
					break;
				case 2:				
					//Consultation d'un compte bancaire
					System.out.println("Consultation d'un compte bancaire");
					showBankAccount(bkDao);
					break;				
				case 3:				
					//Gestion des opérations
					System.out.println("Gestion des opérations");
					System.out.println("Fonctionnalité non implémentée pour l'instant");
					//gestionOperations();
					break;
				case 4:
					//Historique des opérations
					System.out.println("Historique des opérations");
					System.out.println("Fonctionnalité non implémentée pour l'instant");
					//historiqueOperations();
					break;
				case 0:
					System.out.println("Au-revoir et à bientôt !");
					break;
			}
		}
		
		//On referme le scanner
		scanner.close();
	}
	
	/**
	 * Méthode pour créer un compte bancaire
	 * @param bkDao Dao qui permet de gérer en base les BankAccount
	 * @throws ParseException 
	 * @throws BankAccountAlreadyExistsException 
	 */
	public static void createBankAccount(BankAccountDao bkDao) throws ParseException, BankAccountAlreadyExistsException {
		System.out.println("Création d'un compte bancaire");
		
		String numBankAccount = inputNumBankAccount(bkDao, "Numéro du compte : ",true);
		String holder = Functions.input_string(scanner, "Titulaire du compte : ");
		
		BankAccount bankAccount = new BankAccount(numBankAccount,holder);		
		
		bankAccount = bkDao.create(bankAccount);
		if (bankAccount == null) {
			System.out.println("ERREUR lors de la création du compte");
		}else {
			System.out.println("Création de ce compte bien effectuée : ");
			System.out.println(bankAccount);
		}
	}
	
	/**
	 * Fonction qui permet de demander un numéro de compte au format FR-XXXX-XXXX
	 * @param bkDao Dao qui permet de gérer en base les BankAccount
	 * @param prompt Prompt qui demande à l'utilisateur de saisir 
	 * @param verifExist : true si il faut vérifier si ce compte existe déjà
	 * @return
	 * @throws ParseException
	 * @throws BankAccountAlreadyExistsException
	 */
	public static String inputNumBankAccount(BankAccountDao bkDao, String prompt, boolean verifExist) throws ParseException, BankAccountAlreadyExistsException {
		boolean is_input_ok = false;
		String input_user = "";
		while (!is_input_ok) {
			System.out.println(prompt);
			input_user = scanner.nextLine();
			
			try {
				if (input_user.trim().isEmpty()) {
					System.out.println("ERREUR - La saisie ne peut pas être à vide");
					is_input_ok = false;
				}else if (!input_user.matches("^FR-\\d{4}-\\d{4}$")) {
		            throw new ParseException("La saisie doit être au format FR-XXXX-XXXX", 0); 
				}else if (verifExist && bkDao.isExist(input_user)){
					throw new BankAccountAlreadyExistsException("Ce numéro de compte existe déjà"); 
		       	}else {		
					is_input_ok = true;
				}
			}catch(ParseException e) {
				e.printStackTrace();
			}catch(BankAccountAlreadyExistsException e) {
				e.printStackTrace();
			}
		}
		return input_user;
	}
	

	/**
	 * Méthode pour consulter un compte bancaire
	 * @param bkDao Dao qui permet de gérer en base les BankAccount
	 * @throws ParseException
	 * @throws BankAccountAlreadyExistsException
	 */
	public static void showBankAccount(BankAccountDao bkDao) throws ParseException, BankAccountAlreadyExistsException {
		System.out.println("--Liste des comptes bancaires existants :\n"); 
		List<BankAccount> lstComptes = bkDao.readAll();
		for (BankAccount compte : lstComptes) {
			System.out.println(compte+"\n"); 
		}
		String numBankAccount = inputNumBankAccount(bkDao, "Entrez le numéro du compte que vous souhaitez consulter : ",false);
		BankAccount accountToShow = bkDao.readById(numBankAccount);
		System.out.println(accountToShow+"\n");
	}
	
	
	
	
}
