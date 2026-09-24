package fr.projetbank.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import fr.projetbank.daos.BankAccountDao;
import fr.projetbank.models.BankAccount;

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
		
	}
	
	public static void testBankAccount() {
		System.out.println("\n\nTest pour les comptes bancaires");
		
		//Dao pour gérer les comptes bancaires en base
		BankAccountDao dao = new BankAccountDao();	
		
		//TODO Si je veux supprimer le compte après je met à true
		boolean wanttosup = true;
		
		//Test affichage de tous les comptes bancaires
		System.out.println("--Test readAll : Liste des comptes bancaires existants :\n"); 
		List<BankAccount> lstComptes = dao.readAll();
		for (BankAccount compte : lstComptes) {
			System.out.println(compte+"\n"); 
		}
		
		//Test création/lire un compte/modif/suppression d'un compte bancaire		
		String numCompteToCreate = "FR-1234-5679";
		BankAccount testAccount = new BankAccount(numCompteToCreate, "Antoine DUPONT");		
		
		System.out.println("--Test readById\n"); 
		//On vérifie la non existence d'un compte bancaire pour ce numéro de compte
		if (dao.readById(numCompteToCreate) == null) {
			System.out.println("Ce compte bancaire n'existe pas, on le crée !");
			//Test : On crée ce compte bancaire (ok)
			System.out.println("--Test create\n"); 
			testAccount = dao.create(testAccount);
		}else {
			System.out.println("ERREUR - Ce compte bancaire existe déjà !");
		}
				
		//Test : On affiche ce compte bancaire (ok)
		System.out.println("--Test readById après création\n");
		BankAccount testAccount2 = dao.readById(numCompteToCreate);
		System.out.println(testAccount+"\n");
		
		if (testAccount2 != null) {
			System.out.println("--Test update\n");
			//Test : On modifie ce compte bancaire (ok)
			testAccount2.setHolder("Ann SMITH");
			testAccount2.setBalance(new BigDecimal("500000"));
			if (dao.update(testAccount2)) {
				System.out.println("Modification bien effectuée");
			}else {
				System.out.println("ERREUR lors de la Modification");
			}
		}
				
		//Test : On supprime ce compte bancaire	(ok)
		if (wanttosup) {
			System.out.println("--Test delete\n");
			if (dao.delete(numCompteToCreate)) {
				System.out.println("Suppression du compte bien effectuée");
			}else{
				System.out.println("ERREUR lors de la Suppression");
			}
		}
	}
	
	
}
