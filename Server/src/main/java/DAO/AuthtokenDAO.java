package DAO;

import Model.Authtoken;
import Model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Authtokn Data Access Object that is used to access the Authtoken table
 */
public class AuthtokenDAO {

    private Connection conn;

    public AuthtokenDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Used to insert an authtoken into the Authtoken table
     * @param authtoken used to specify the authtoken
     */
    public void insert(Authtoken authtoken) throws DataAccessException {
        String sql = "INSERT INTO Authtoken (authtoken, username) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, authtoken.getAuthtoken());
            stmt.setString(2, authtoken.getUsername());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting authtoken into the database");
        }
    }

    /**
     * Used to find an authtoken into the Authtoken table
     * @param the_authtoken used to specify the authtoken
     * @return the authtoken if found and null if not
     */
    public Authtoken find(String the_authtoken) throws DataAccessException{
        Authtoken authtoken;
        ResultSet rs;
        String sql = "SELECT * FROM Authtoken WHERE authtoken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, the_authtoken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authtoken = new Authtoken(rs.getString("authtoken"), rs.getString("username"));
                return authtoken;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authtoken in the database");
        }
    }

    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Authtoken";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the Authtoken table");
        }
    }


    /**
     * Used to delete an authtoken into the Authtoken table
     * @param authtoken used to specify the authtoken
     */
    public void delete(String authtoken) {

    }

}
