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
    }

    public void SelectAllPret(){

        String query = "select * from pret inner join Adherent on Adherent.NUMADHERENT = pret.NumAdherent  Inner join exemplaire on exemplaire.numex = pret.numex inner join livre on livre.NUMLIVRE = exemplaire.numlivre" ;

        Statement state = null;
        ResultSet  rst = null;
        int rowcount = 0;
        try {
           state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
              rst = state.executeQuery(query);

            ResultSetMetaData meta = rst.getMetaData();

            while(rst.next()) rowcount++;

            Object [][] rowData = new Object[meta.getColumnCount()][rowcount];

            rst.isBeforeFirst();
            while (rst.next()) {

                for (int i = 0; i < rowData.length; ++i)
                    for (int j = 0; j < rowcount; j++)
                          rowData[i][j] = rst.getObject(i + 1);


            }



            JTable j = new JTable(rowData,new String[rowData.length]);
          hashCode()

                    j.setVisible(true);
            j.setShowGrid(true);

            Pret.add(j);

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
