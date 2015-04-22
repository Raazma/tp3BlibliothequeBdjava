import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
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
    Connection conn;

    public ListerDesPrets(Connection conn){
        this.conn = conn;
        SelectAllPret();
    }

    public void SelectAllPret(){

       // String query = "select * from pret inner join Adherent on Adherent.NUMADHERENT = pret.NumAdherent  Inner join exemplaire on exemplaire.numex = pret.numex inner join livre on livre.NUMLIVRE = exemplaire.numlivre" ;
       String query = "select * from adherent";
        Statement state = null;
        ResultSet  rst = null;
        int rowcount = 0;
        try {
           state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
              rst = state.executeQuery(query);

            ResultSetMetaData meta = rst.getMetaData();

            while(rst.next()) rowcount++;

         Object [][] rowData = new Object[3][3];


            rst.isBeforeFirst();
            while (rst.next()) {

                for (int i = 0; i < rowData.length; ++i)
                   for (int j = 0; j < rowcount; j++)
                          rowData[i][j] = rst.getObject(i + 1);


            }

          String[] p =  new String[rowData.length];
            p[0] = "FF";


            JTable j = new JTable(rowData,p);

          JScrollPane pan = new JScrollPane(j);

            Pret.add(pan);

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
