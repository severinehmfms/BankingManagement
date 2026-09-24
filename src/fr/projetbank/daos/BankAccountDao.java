package fr.projetbank.daos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.projetbank.database.DatabaseConnection;
import fr.projetbank.models.BankAccount;
import fr.projetbank.models.Operation;

public class BankAccountDao implements Dao<BankAccount, String> {
	
	public BankAccount getBankAccountFromDb(ResultSet resultSet) throws SQLException {
		BankAccount bankAccount = null;
		try {
			String rsNumBankAccount = resultSet.getString("numBankAccount"); 
			String rsHolder = resultSet.getString("Holder");
			BigDecimal rsBalance = resultSet.getBigDecimal("Balance");
			BigDecimal rsMaximumBalance = resultSet.getBigDecimal("MaximumBalance");
			
			
			//TODO On va récupérer la liste des opérations correspondant à ce compte bancaire
			//En appelant le dao Operation
			
			
			bankAccount = new BankAccount(rsNumBankAccount, rsHolder, rsBalance, rsMaximumBalance, new ArrayList<Operation>());
	   
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return bankAccount;
	}
	
	
	/**
	 * Méthode readById pour retourner l'objet BankAccount correspondant à l'id
	 */
	@Override
	public BankAccount readById(String numBankAccount) {
		BankAccount bankAccount = null;
		try (Connection connection = DatabaseConnection.getConnection()) {
			String strSql = "SELECT * FROM Bank_Account WHERE NumBankAccount = ?";
			try (PreparedStatement ps = connection.prepareStatement(strSql)){
				ps.setString(1, numBankAccount);
				//System.out.println(strSql);
	        	try(ResultSet resultSet = ps.executeQuery()){
	        		
	        		if (resultSet.next()) { // On lit la première (et unique) ligne
	        			bankAccount = getBankAccountFromDb(resultSet);
	        			 
	        			/*if (resultSet != null) {
	        			String rsNumBankAccount = resultSet.getString("numBankAccount"); 
	        			String rsHolder = resultSet.getString("Holder");
	        			BigDecimal rsBalance = resultSet.getBigDecimal("Balance");
	        			BigDecimal rsMaximumBalance = resultSet.getBigDecimal("MaximumBalance");
	        			
	        			
	        			//TODO On va récupérer la liste des opérations correspondant à ce compte bancaire
	        			//En appelant le dao Operation
	        			
	        			
	        			bankAccount = new BankAccount(rsNumBankAccount, rsHolder, rsBalance, rsMaximumBalance, new ArrayList<Operation>());
	                	*/
	                } else {
	                    System.out.println("Aucun résultat trouvé.");
	                }
	        	}	        	
	        }
		} catch (Exception e) {
            e.printStackTrace();
        }
		return bankAccount;
	}

	/**
	 * Méthode qui renvoie la liste de tous les comptes bancaires
	 */
	@Override
	public List<BankAccount> readAll() {
		List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
		try (Connection connection = DatabaseConnection.getConnection()) {
			String strSql = "SELECT * FROM Bank_Account";
	        try(Statement statement = connection.createStatement()){
	        	try(ResultSet resultSet = statement.executeQuery(strSql)){
	        		while(resultSet.next()) {
	        			/*String rsNumBankAccount = resultSet.getString("numBankAccount"); 
	        			String rsHolder = resultSet.getString("Holder");
	        			BigDecimal rsBalance = resultSet.getBigDecimal("Balance");
	        			BigDecimal rsMaximumBalance = resultSet.getBigDecimal("MaximumBalance");
	        			bankAccounts.add(new BankAccount(rsNumBankAccount, rsHolder, rsBalance, rsMaximumBalance, new ArrayList<Operation>()));
	        			 */
	        			
	        			BankAccount bankAccount = getBankAccountFromDb(resultSet);
	        			
	        			if (bankAccount != null) {
	        				bankAccounts.add(bankAccount);
	        			}
	        			
	        		}
	        	}
	        }
		} catch (Exception e) {
            e.printStackTrace();
        }
		return bankAccounts;
	}

	/**
	 * Méthode pour créer un compte bancaire dans la base
	 */
	@Override
	public BankAccount create(BankAccount obj) {
		try (Connection connection = DatabaseConnection.getConnection()) {
			//TODO Gérer le cas ou ce numéro de compte existe déjà !!!! Renvoyer une exception !!!! 
			String str = "INSERT INTO Bank_Account (NumBankAccount, Holder, Balance, MaximumBalance) VALUES (?,?,?,?)";
			try (PreparedStatement ps = connection.prepareStatement(str)){
				ps.setString(1, obj.getNumBankAccount());
				ps.setString(2, obj.getHolder());
				ps.setBigDecimal(3, obj.getBalance());
				ps.setBigDecimal(4, obj.getMaximumBalance());
				if( ps.executeUpdate() == 0)
					throw new SQLException("Échec de l'insertion, aucune ligne affectée.");	            
			}catch (SQLException e) {
				e.printStackTrace();
			}

        } catch (Exception e) {
            e.printStackTrace();
        }
		return obj;
	}

	/**
	 * Méthode qui modifie un compte bancaire dans la base
	 */
	@Override
	public boolean update(BankAccount obj) {
		try (Connection connection = DatabaseConnection.getConnection()) {
			String str = "UPDATE Bank_Account SET Holder=?, Balance=?, MaximumBalance=? WHERE NumBankAccount=?";
			
			try (PreparedStatement ps = connection.prepareStatement(str)){
				ps.setString(1, obj.getHolder());
				ps.setBigDecimal(2, obj.getBalance());
				ps.setBigDecimal(3, obj.getMaximumBalance());
				ps.setString(4, obj.getNumBankAccount());
				
				// On récupère le nombre de lignes affectées par la requête
				int nbLignes = ps.executeUpdate(); 
				
				if (nbLignes == 0) { 
					throw new SQLException("Échec de la mise à jour, aucune ligne affectée."); 
				}
				
				return true;
	            
			}catch (SQLException e) {
				e.printStackTrace();
				return false;
			}

        } catch (Exception e) {
        	System.out.println("ERREUR lors de la connexion à la base de données");
            e.printStackTrace();
        }
		return false;
	}

	/**
	 * Méthode qui supprime un compte bancaire de la base
	 */
	public boolean delete(String numBankAccount) {

	    String strSql = "DELETE FROM Bank_Account WHERE NumBankAccount=?";

	    try (Connection connection = DatabaseConnection.getConnection()){
	    		
	    	try(PreparedStatement ps = connection.prepareStatement(strSql)){

	        	ps.setString(1, numBankAccount);

	        	// ps.executeUpdate() = nombre de lignes affectées par la requête
	        	return ps.executeUpdate() > 0;
	        	
		    }catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
	        	
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
