package fr.projetbank.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.projetbank.models.BankAccount;
import fr.projetbank.database.DatabaseConnection;

public class BankAccount implements Dao<BankAccount> {

	/**
	 * Méthode readById pour retourner l'objet Article correspondant à l'id
	 */
	@Override
	public BankAccount readById(int NumBankAccount) {
		BankAccount bankAccount = null;
		try (Connection connection = DatabaseConnection.getConnection()) {
			String strSql = "SELECT * FROM T_Articles WHERE IdArticle = ?";
			try (PreparedStatement ps = connection.prepareStatement(strSql)){
				ps.setInt(1, idArticle);
				//System.out.println(strSql);
	        	try(ResultSet resultSet = ps.executeQuery()){
	        		
	        		if (resultSet.next()) { // On lit la première (et unique) ligne
	        			int rsIdUser = resultSet.getInt("idArticle"); 
	        			String rsDescription = resultSet.getString("Description");
	        			String rsMarque = resultSet.getString("Brand");
	        			double rsPrixUnitaire = resultSet.getDouble("UnitaryPrice");
	        			
	        			article = new Article(rsIdUser, rsDescription, rsMarque, rsPrixUnitaire);
	                } else {
	                    System.out.println("Aucun résultat trouvé.");
	                }
	        	}	        	
	        }
		} catch (Exception e) {
            e.printStackTrace();
        }
		return article;
	}

	/**
	 * Méthode qui renvoie la liste de tous les articles
	 */
	@Override
	public List<Article> readAll() {
		ArrayList<Article> articles = new ArrayList<Article>();
		try (Connection connection = DatabaseConnection.getConnection()) {
			String strSql = "SELECT * FROM T_Articles";
	        try(Statement statement = connection.createStatement()){
	        	try(ResultSet resultSet = statement.executeQuery(strSql)){
	        		while(resultSet.next()) {
	        			int rsIdUser = resultSet.getInt(1); 
	        			String rsDescription = resultSet.getString(2);
	        			String rsMarque = resultSet.getString(3);
	        			double rsPrixUnitaire = resultSet.getDouble(4);
	        			articles.add((new Article(rsIdUser, rsDescription, rsMarque, rsPrixUnitaire)));
	        		}
	        	}
	        }
		} catch (Exception e) {
            e.printStackTrace();
        }
		return articles;
	}

	/**
	 * Méthode pour créer un article dans la base
	 */
	@Override
	public Article create(Article obj) {
		try (Connection connection = DatabaseConnection.getConnection()) {
			String str = "INSERT INTO T_Articles (Description, Brand, UnitaryPrice) VALUES (?,?,?)";
			try (PreparedStatement ps = connection.prepareStatement(str, Statement.RETURN_GENERATED_KEYS)){
				ps.setString(1, obj.getDescription());
				ps.setString(2, obj.getBrand());
				ps.setDouble(3, obj.getPrice());
				if( ps.executeUpdate() == 0)
					throw new SQLException("Échec de l'insertion, aucune ligne affectée.");
				
	            // Récupération de l'ID généré
	            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    int id = generatedKeys.getInt(1);
	                    System.out.println("Nouvel ID généré : " + id);
	                    
	                    obj.setIdentifiant(id);
	                } else {
	                    throw new SQLException("Échec de la récupération de l'ID généré.");
	                }
	            }
	            
			}catch (SQLException e) {
				e.printStackTrace();
			}

        } catch (Exception e) {
            e.printStackTrace();
        }
		return obj;
	}

	/**
	 * Méthode qui modifie un article dans la base
	 */
	@Override
	public boolean update(Article obj) {
		try (Connection connection = DatabaseConnection.getConnection()) {
			String str = "UPDATE T_Articles SET Description=?, Brand=?, UnitaryPrice=? WHERE IdArticle=?";
			
			try (PreparedStatement ps = connection.prepareStatement(str)){
				ps.setString(1, obj.getDescription());
				ps.setString(2, obj.getBrand());
				ps.setDouble(3, obj.getPrice());
				ps.setInt(4, obj.getIdentifiant());
				
				// On récupère le nombre de lignes affectées par la requête
				int nbLignes = ps.executeUpdate(); 
				
				if (nbLignes == 0) { 
					throw new SQLException("Échec de la mise à jour, aucune ligne affectée."); 
				}
				
				return true;
	            
			}catch (SQLException e) {
				e.printStackTrace();
			}

        } catch (Exception e) {
        	System.out.println("ERREUR lors de la connexion à la base de données");
            e.printStackTrace();
        }
		return false;
	}

	/**
	 * Méthode qui supprime un article de la base
	 */
	@Override
	public boolean delete(int idArticle) {

	    String strSql = "DELETE FROM T_Articles WHERE IdArticle=?";

	    try (Connection connection = DatabaseConnection.getConnection()){
	    		
	    	try(PreparedStatement ps = connection.prepareStatement(strSql)){

	        	ps.setInt(1, idArticle);

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
