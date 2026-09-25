package fr.projetbank.business;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import fr.projetbank.utils.Functions;
import fr.projetbank.daos.BankAccountDao;
import fr.projetbank.daos.OperationDao;
import fr.projetbank.exceptions.BankAccountAlreadyExistsException;
import fr.projetbank.exceptions.BankAccountNoExistsException;
import fr.projetbank.models.BankAccount;
import fr.projetbank.models.Deposit;

public class BankingManagement {
	
	//On initialise le scanner
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) throws ParseException, BankAccountAlreadyExistsException, BankAccountNoExistsException{
		//Dao pour gérer les comptes bancaires en base
		BankAccountDao bkDao = new BankAccountDao();
		
		//Dao pour gérer les opérations en base
		OperationDao opDao = new OperationDao();
		
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
					//Création d'un compte bancaire (ok)
					createBankAccount(bkDao);
					break;
				case 2:				
					//Consultation d'un compte bancaire (ok)
					System.out.println("Consultation d'un compte bancaire");
					showBankAccount(bkDao);
					break;				
				case 3:				
					//Gestion des opérations
					System.out.println("Gestion des opérations");
					gestionOperations(bkDao,opDao);
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
	 * @throws BankAccountNoExistsException 
	 */
	public static void createBankAccount(BankAccountDao bkDao) throws ParseException, BankAccountAlreadyExistsException, BankAccountNoExistsException {
		System.out.println("Création d'un compte bancaire");
		
		String numBankAccount = inputNumBankAccount(bkDao, "Numéro du compte : ",false,true);
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
	 * @param shouldExist : true si il faut que ce compte EXISTE
	 * @param shouldNotExists : true si il faut que ce compte N EXISTE PAS
	 * @return
	 * @throws ParseException
	 * @throws BankAccountAlreadyExistsException
	 * @throws BankAccountNoExistsException 
	 */
	public static String inputNumBankAccount(BankAccountDao bkDao, String prompt, boolean shouldExist, boolean shouldNotExist) throws ParseException, BankAccountAlreadyExistsException, BankAccountNoExistsException {
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
		        //Si ce compte NE DOIT PAS Exister MAIS qu'il existe
				}else if (shouldNotExist && bkDao.isExist(input_user)){
					throw new BankAccountAlreadyExistsException("Ce compte existe déjà!"); 
				//Si ce compte DOIT Exister MAIS qu'il n'existe pas
				}else if (shouldExist && !bkDao.isExist(input_user)){
					throw new BankAccountNoExistsException("Ce compte n'existe pas!"); 
		       	}else {		
					is_input_ok = true;
				}
			}catch(ParseException e) {
				e.printStackTrace();
			}catch(BankAccountAlreadyExistsException e) {
				e.printStackTrace();
			}catch(BankAccountNoExistsException e) {
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
	 * @throws BankAccountNoExistsException 
	 */
	public static void showBankAccount(BankAccountDao bkDao) throws ParseException, BankAccountAlreadyExistsException, BankAccountNoExistsException {
		System.out.println("--Liste des comptes bancaires existants :\n"); 
		List<BankAccount> lstComptes = bkDao.readAll();
		for (BankAccount compte : lstComptes) {
			System.out.println(compte+"\n"); 
		}
		String numBankAccount = inputNumBankAccount(bkDao, "Entrez le numéro du compte que vous souhaitez consulter : ",true,false);
		BankAccount accountToShow = bkDao.readById(numBankAccount);
		//System.out.println(accountToShow+"\n");
		
		String details = accountToShow.getDetailsBankAccount();
		System.out.println(details+"\n");
	}
	
	/**
	 * Méthode pour afficher le menu Gestion des opérations
	 * @param bkDao
	 * @param opDao
	 * @throws BankAccountAlreadyExistsException 
	 * @throws ParseException 
	 * @throws BankAccountNoExistsException 
	 */
	public static void gestionOperations(BankAccountDao bkDao, OperationDao opDao) throws ParseException, BankAccountAlreadyExistsException, BankAccountNoExistsException {
		String[] menu = {
				"Effectuer un dépôt",
				"Effectuer un retrait",
			    "Effectuer un virement"
			};
		
		int choice_user = -1;
		while (choice_user != 0) {
			//On demande à l'utilisateur son choix par rapport au menu proposé
			choice_user = Functions.ask_user_choice(scanner, menu);
			switch(choice_user) {
				case 1:				
					//Effectuer un dépôt
					System.out.println("Effectuer un dépôt");
					
					String numBankAccount = inputNumBankAccount(bkDao, "Entrez le numéro du compte sur lequel vous souhaitez effectuer un dépôt : ",true, false);
					BankAccount bankAccount = bkDao.readById(numBankAccount);
					
					double maxAmount = BankAccount.MAX_BALANCE.doubleValue() - bankAccount.getBalance().doubleValue();
					//System.out.println("Plafond : : "+BankAccount.MAX_BALANCE);
					//System.out.println("Solde actuel : "+bankAccount.getBalance());
					System.out.println("Montant maximal du dépôt : "+maxAmount);
					double amount = Functions.input_double(scanner, "Entrez le montant du dépôt", 1, maxAmount);
					
					if (maxAmount < 1) {
						System.out.println("Le plafond de votre compte est atteint, il ne vous est plus possible d'effectuer un dépôt !");
					}else {
						Deposit op = new Deposit(new Date(), new BigDecimal(amount), numBankAccount);
						opDao.create(op);
					}
					break;
				case 2:				
					//Effectuer un retrait
					System.out.println("Effectuer un retrait");
					System.out.println("Fonctionnalité non implémentée pour l'instant");	
					break;
				case 3:				
					//Effectuer un virement
					System.out.println("Effectuer un virement");
					System.out.println("Fonctionnalité non implémentée pour l'instant");
					break;
				case 0:
					System.out.println("Retour au menu précédent.");
					break;
			}
		}
	}
	
}
