package fr.projetbank.daos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.projetbank.database.DatabaseConnection;
import fr.projetbank.models.BankAccount;
import fr.projetbank.models.Deposit;
import fr.projetbank.models.Operation;
import fr.projetbank.models.Transfer;
import fr.projetbank.models.Withdrawal;

public class OperationDao implements Dao<Operation, Integer> {
	
	/**
	 * Création d'un objet Operation à partir d'un ResultSet
	 * @param resultSet
	 * @return
	 * @throws Exception 
	 */
	public Operation getOperationFromDb(ResultSet resultSet) throws Exception {
		//Dao pour gérer les comptes bancaires en base
		BankAccountDao dao = new BankAccountDao();
				
		Operation operation = null;
		try {
			//Date rsDateOperation = resultSet.getDate("DateOperation"); 
			java.sql.Date rsDateOperation = resultSet.getDate("DateOperation");
			java.util.Date utilDateOperation = new java.util.Date(rsDateOperation.getTime());
			
			BigDecimal rsAmountTransaction = resultSet.getBigDecimal("AmountTransaction");
			String rsTypeOperation = resultSet.getString("TypeOperation");
			String rsNumBankAccount = resultSet.getString("NumBankAccount");
			String rsNumBankAccountDestination = resultSet.getString("NumBankAccountDestination");
					
			//On va récupérer le compte bancaire correspondant au numéro de compte
			BankAccount bankAccount = dao.readById(rsNumBankAccount);
			
			//On va créer le bon objet en fonction du type d'opération en base
			Operation.typesOperations type = Operation.typesOperations.valueOf(rsTypeOperation);
			switch (type) {
			    case DEPOSIT:
			    	operation = new Deposit(utilDateOperation, rsAmountTransaction, bankAccount);
			        break;
	
			    case WITHDRAWAL:
			    	operation = new Withdrawal(utilDateOperation, rsAmountTransaction, bankAccount);
			        break;
	
			    case TRANSFER:
			        if (rsNumBankAccountDestination == null) {
			            throw new Exception("Un transfert doit obligatoirement comporter un compte destinataire.");
			        } 
			        BankAccount bankAccountDestinataire = dao.readById(rsNumBankAccountDestination);
					operation = new Transfer(utilDateOperation, rsAmountTransaction, bankAccount, bankAccountDestinataire);
			}
			
			//On rajoute l'id
			operation.setIdOperation(resultSet.getInt("idOperation"));
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return operation;
	}
	
	
	/**
	 * Méthode readById pour retourner l'objet Operation correspondant à l'id
	 * @param idOperation
	 * @return
	 */
	@Override
	public Operation readById(Integer idOperation) {
		Operation operation = null;
		try (Connection connection = DatabaseConnection.getConnection()) {
			String strSql = "SELECT * FROM Operations WHERE IdOperation = ?";
			try (PreparedStatement ps = connection.prepareStatement(strSql)){
				ps.setInt(1, idOperation);
				//System.out.println(strSql);
	        	try(ResultSet resultSet = ps.executeQuery()){
	        		
	        		if (resultSet.next()) { // On lit la première (et unique) ligne
	        			operation = getOperationFromDb(resultSet);
	                } else {
	                    System.out.println("Aucun résultat trouvé.");
	                }
	        	}	        	
	        }
		} catch (Exception e) {
            e.printStackTrace();
        }
		return operation;
	}

	/**
	 * Méthode qui renvoie la liste de toutes les opérations pour un compte bancaire
	 */
	@Override
	public List<Operation> readAll() {
		return this.readOperationsByBankAccount("");
	}
	
	/**
	 * Méthode qui renvoie la liste des opérations concernant un compte précis
	 * @param numBankAccount
	 * @return
	 */
	public List<Operation> readOperationsByBankAccount(String numBankAccount) {
		List<Operation> lstOperations = new ArrayList<Operation>();
		try (Connection connection = DatabaseConnection.getConnection()) {
			String strSql;

			if (numBankAccount.equals("")) {
			    strSql = "SELECT * FROM Operations";
			} else {
			    strSql = "SELECT * FROM Operations WHERE NumBankAccount = ?";
			}

			try (PreparedStatement ps = connection.prepareStatement(strSql)) {

			    if (!numBankAccount.equals("")) {
			        ps.setString(1, numBankAccount);
			    }
			    
	        	try(ResultSet resultSet = ps.executeQuery()){
			
	           		while(resultSet.next()) {
	        			
	        			Operation operation = getOperationFromDb(resultSet);
	        			
	        			if (operation != null) {
	        				lstOperations.add(operation);
	        			}
	        			
	        		}
	        	}
			}
		} catch (Exception e) {
            e.printStackTrace();
        }
		return lstOperations;
		
	}
	

	/**
	 * Méthode pour créer une opération dans la base
	 */
	@Override
	public Operation create(Operation obj) {
		try (Connection connection = DatabaseConnection.getConnection()) {
			String str = "INSERT INTO Operations (DateOperation, AmountTransaction, TypeOperation, NumBankAccount, NumBankAccountDestination) VALUES (?,?,?,?,?)";
			try (PreparedStatement ps = connection.prepareStatement(str)){
				
				ps.setDate(1, new java.sql.Date(obj.getDateOperation().getTime()));
				ps.setBigDecimal(2, obj.getAmountTransaction());				
				//Operation.typeTransaction typeOperation = obj.getTypeOperation();
				//ps.setString(3, typeOperation.toString());
				ps.setString(3, obj.getTypeOperation().name());				
				ps.setString(4, obj.getBankAccount().getNumBankAccount());
				
				String numBankAccountDestination = obj.getNumBankAccountDestination();
				if (numBankAccountDestination != null) {
				    ps.setString(5, numBankAccountDestination);
				} else {
				    ps.setNull(5, java.sql.Types.VARCHAR);
				}
				
				//Normalement cette version là devrait fonctionner aussi :
				//ps.setString(5, obj.getNumBankAccountDestination());
				
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
	//Champs DateOperation AmountTransaction TypeOperation NumBankAccount NumBankAccountDestination 
	
	
	/**
	 * Méthode qui modifie une opération dans la base
	 */
	@Override
	public boolean update(Operation obj) {
		try (Connection connection = DatabaseConnection.getConnection()) {
			String strSql = "UPDATE Operations SET DateOperation=?, AmountTransaction=?, TypeOperation=?, NumBankAccount=?, NumBankAccountDestination=? WHERE IdOperation=?";
			
			try (PreparedStatement ps = connection.prepareStatement(strSql)){
				ps.setDate(1, new java.sql.Date(obj.getDateOperation().getTime()));
				ps.setBigDecimal(2, obj.getAmountTransaction());				
				//Operation.typeTransaction typeOperation = obj.getTypeOperation();
				//ps.setString(3, typeOperation.toString());
				ps.setString(3, obj.getTypeOperation().name());				
				ps.setString(4, obj.getBankAccount().getNumBankAccount());
				
				String numBankAccountDestination = obj.getNumBankAccountDestination();
				if (numBankAccountDestination != null) {
				    ps.setString(5, numBankAccountDestination);
				} else {
				    ps.setNull(5, java.sql.Types.VARCHAR);
				}
				//Normalement cette version là devrait fonctionner aussi :
				//ps.setString(5, obj.getNumBankAccountDestination());
				
				ps.setInt(6, obj.getIdOperation());				
				
				System.out.println(strSql);
				
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
	 * Méthode qui supprime une opération de la base
	 */
	public boolean delete(Integer idOperation) {

	    String strSql = "DELETE FROM Operations WHERE IdOperation=?";

	    try (Connection connection = DatabaseConnection.getConnection()){
	    		
	    	try(PreparedStatement ps = connection.prepareStatement(strSql)){

	        	ps.setInt(1, idOperation);

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
