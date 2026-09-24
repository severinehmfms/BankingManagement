package fr.projetbank.business;

import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.projetbank.daos.BankAccountDao;
import fr.projetbank.daos.OperationDao;
import fr.projetbank.models.BankAccount;
import fr.projetbank.models.Operation;
import fr.projetbank.models.Deposit;
import fr.projetbank.models.Transfer;
import fr.projetbank.models.Withdrawal;

/**
 * Classe TestBankingManagement pour tester l'application de gestion bancaire
 */
public class TestBankingManagement {

	public static void main(String[] args){
		testBankAccount();
		
		testOperation();
		
	}
	
	public static void testOperation() {
		System.out.println("\n\nTest pour les opérations");
		
		//TODO Si je veux supprimer les opérations créées après je met à true
		boolean wanttosup = true;
		
		//Dao pour gérer les opérations en base
		OperationDao opDao = new OperationDao();
		
		//Dao pour gérer les comptes bancaires en base
		BankAccountDao bkDao = new BankAccountDao();
		
		
		//Test affichage de toutes les opérations
		System.out.println("--Test readAll : Liste de toutes les opérations existantes :\n"); 
		List<Operation> lstOperations = opDao.readAll();
		for (Operation op : lstOperations) {
			
			System.out.println(op + "\n"); 
		}
				
		BankAccount bk = bkDao.readById("FR-1234-5679");		
		
		if (bk == null) {
			System.out.println("Il faut obligatoirement un compte lié pour pouvoir créer une opération");
		}else {		
			//Création d'un dépôt (ok)
			Deposit op = new Deposit(new Date(), new BigDecimal("500000"), bk);
			//opDao.create(op);
			//System.out.println(op.toString());
			
			//Création d'un retrait (ok)
			Withdrawal wd = new Withdrawal(new Date(), new BigDecimal("20000.20"), bk);
			//opDao.create(wd);			
			//System.out.println(wd.toString());
		}
		
		BankAccount bkDest = bkDao.readById("FR-1234-5678");
		if (bk == null || bkDest == null) {
			System.out.println("Il faut obligatoirement un compte lié ET un compte destinataire pour pouvoir créer un virement");
		}else {
			Transfer op = new Transfer(new Date(), new BigDecimal("20000.20"), bk, bkDest);
			//opDao.create(op);
			
			//System.out.println(op.toString());
		}
	
		//Test affichage de toutes les opérations DU COMPTE FR-1234-5679
		System.out.println("\n\n--Test readAll : Liste des opérations pour le compte FR-1234-5679 :\n"); 
		List<Operation> lstOperations2 = opDao.readOperationsByBankAccount("FR-1234-5679");
		for (Operation op : lstOperations2) {
			System.out.println(op+"\n"); 
		}
		
		
		//Test modification
		//Test : On récupère une opération existante
		System.out.println("--Test readById existant\n");
		Operation opToTest = opDao.readById(2);
		System.out.println("On va modifier cette opération : " + opToTest + "\n");
		
		if (opToTest != null) {
			System.out.println("--Test update\n");
			//Test : On modifie ce compte bancaire (ok)
			opToTest.setAmountTransaction(new BigDecimal(2000));
			if (opDao.update(opToTest)) {
				System.out.println("Modification bien effectuée");
			}else {
				System.out.println("ERREUR lors de la Modification");
			}
		}
				
		//Test : On supprime cette opération
		if (wanttosup) {
			System.out.println("--Test delete\n");
			if (opDao.delete(7)) {
				System.out.println("Suppression de l'opération bien effectuée");
			}else{
				System.out.println("ERREUR lors de la Suppression");
			}
		}
		
		
	}
	
	public static void testBankAccount() {
		System.out.println("\n\nTest pour les comptes bancaires");
		
		//Dao pour gérer les comptes bancaires en base
		BankAccountDao bkDao = new BankAccountDao();	
		
		//TODO Si je veux supprimer le compte après je met à true
		boolean wanttosup = false;
		
		//Test affichage de tous les comptes bancaires
		System.out.println("--Test readAll : Liste des comptes bancaires existants :\n"); 
		List<BankAccount> lstComptes = bkDao.readAll();
		for (BankAccount compte : lstComptes) {
			System.out.println(compte+"\n"); 
		}
		
		//Test création/lire un compte/modif/suppression d'un compte bancaire		
		String numCompteToCreate = "FR-1234-5679";
		BankAccount testAccount = new BankAccount(numCompteToCreate, "Antoine DUPONT");		
		
		System.out.println("--Test readById\n"); 
		//On vérifie la non existence d'un compte bancaire pour ce numéro de compte
		//if (bkDao.readById(numCompteToCreate) == null) {
		if (!bkDao.isExist(numCompteToCreate)) {			
			System.out.println("Ce compte bancaire n'existe pas, on le crée !");
			//Test : On crée ce compte bancaire (ok)
			System.out.println("--Test create\n"); 
			testAccount = bkDao.create(testAccount);
		}else {
			System.out.println("ERREUR - Ce compte bancaire existe déjà !");
		}
				
		//Test : On affiche ce compte bancaire (ok)
		System.out.println("--Test readById après création\n");
		BankAccount testAccount2 = bkDao.readById(numCompteToCreate);
		System.out.println(testAccount+"\n");
		
		if (testAccount2 != null) {
			System.out.println("--Test update\n");
			//Test : On modifie ce compte bancaire (ok)
			testAccount2.setHolder("Ann SMITH");
			testAccount2.setBalance(new BigDecimal("500000"));
			if (bkDao.update(testAccount2)) {
				System.out.println("Modification bien effectuée");
			}else {
				System.out.println("ERREUR lors de la Modification");
			}
		}
				
		//Test : On supprime ce compte bancaire	(ok)
		if (wanttosup) {
			System.out.println("--Test delete\n");
			if (bkDao.delete(numCompteToCreate)) {
				System.out.println("Suppression du compte bien effectuée");
			}else{
				System.out.println("ERREUR lors de la Suppression");
			}
		}
	}
	
	
}
