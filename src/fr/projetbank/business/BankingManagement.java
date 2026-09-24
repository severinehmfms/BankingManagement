package fr.projetbank.business;

import java.util.Scanner;

import fr.projetbank.utils.Functions;
import fr.projetbank.daos.BankAccountDao;
import fr.projetbank.models.BankAccount;

public class BankingManagement {
	
	//On initialise le scanner
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args){
		
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
					createBankAccount();
					break;
				case 2:				
					//Consultation d'un compte bancaire
					System.out.println("Consultation d'un compte bancaire");
					System.out.println("Fonctionnalité non implémentée pour l'instant");
					//showBankAccount();
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
	 */
	public static void createBankAccount() {
		System.out.println("Création d'un compte bancaire");
		
		String numBankAccount = Functions.input_string(scanner, "Numéro du compte : ");
		String holder = Functions.input_string(scanner, "Titulaire du compte : ");
		
		BankAccount bankAccount = new BankAccount(numBankAccount,holder);		
		BankAccountDao bkDao = new BankAccountDao();
		bankAccount = bkDao.create(bankAccount);
		if (bankAccount == null) {
			System.out.println("ERREUR lors de la création du compte");
		}else {
			System.out.println("Création de ce compte bien effectuée : ");
			System.out.println(bankAccount);
		}		
	}
	
	
}
