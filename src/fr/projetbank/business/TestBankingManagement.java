package fr.projetbank.business;

import java.math.BigDecimal;

import fr.projetbank.daos.BankAccountDao;
import fr.projetbank.models.BankAccount;

/**
 * Classe TestBankingManagement pour tester l'application de gestion bancaire
 */
public class TestBankingManagement {

	public static void main(String[] args){
		//Dao pour gérer les comptes bancaires en base
		BankAccountDao bDao = new BankAccountDao();	
		
		//BankAccount testAccount = new BankAccount("FR-1234-5678", "Antoine DUPONT");
		
		//Test : On crée ce compte bancaire
		//testAccount = bDao.create(testAccount);
		

		//Test : On affiche ce compte bancaire 
		BankAccount testAccount = bDao.readById("FR-1234-5678");
		System.out.println(testAccount);
		
		//Test : On modifie ce compte bancaire
		testAccount.setHolder("Ann SMITH");
		testAccount.setBalance(new BigDecimal("500000"));
		if (bDao.update(testAccount)) {
			System.out.println("Modification bien effectuée");
		}else {
			System.out.println("ERREUR lors de la Modification");
		}
				
		//Test : On supprime ce compte bancaire		
		//bDao.delete("FR-1234-5678");
		
	}
}
