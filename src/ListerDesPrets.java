import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.sql.ResultSet;

/**
 * Created by Razma on 2015-04-18.
 */
public class ListerDesPrets {
     public JPanel Pret;
      private JTable ListePret;
     Connection conn;

    public ListerDesPrets(Connection conn){
        this.conn = conn;
    }

    public void SelectAllPret(){

        String query = "select * from pret inner join Adherent on Adherent.NUMADHERENT = pret.NumAdherent  Inner join exemplaire on exemplaire.numex = pret.numex inner join livre on livre.NUMLIVRE = exemplaire.numlivre" ;

        Statement state = null;
        ResultSet  rst = null;
        try {
           state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
              rst = state.executeQuery(query);
           // QueryTableModel qtm = new QueryTableModel();
          // ListePret.setModel(DbUtils.resultSetToTableModel(rst));
        //  ListePret.



        } catch (SQLException e) {

        } finally {
            try {
               rst.close();
               state.close();
             } catch (SQLException e) {

             }
        }

    }

}
