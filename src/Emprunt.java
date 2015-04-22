import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by Razma on 2015-04-21.
 */
public class Emprunt {
    public JPanel Emprunt;
     private JList list1;
    private JTextField AdBox;
    private JTextField NumBox;
    private JComboBox CbExDispo;
    private JButton BtnEmp;
    private JList list2;
    private JButton Dispo;
    Connection conn;


    public Emprunt(Connection conn){
        this.conn = conn;

        Dispo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetDispo();
            }
        });
        BtnEmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InsertPret();
            }
        });
    }
    private int SelectNumAd(){
         ResultSet rst = null;
        Statement state= null;
        int num = 0;
        String query = "select NumAdherent from Adherent where Prenom ='"+ AdBox.getText()+"'";

        try {
            state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rst = state.executeQuery(query);

            while(rst.next())  num = rst.getInt(1) ;


        } catch (SQLException e) {
        //    System.out.println("Numero de livre invalide...");

        }
        catch (NullPointerException e) {
           // System.out.println("Numero de livre invalide...");

        }
        finally {
            try {
                rst.close();
                state.close();
            } catch (SQLException e) {


            }
        }
       return num;
    }

    private void SetDispo() {

        ResultSet rst = null;
        Statement state= null;
        CbExDispo.removeAllItems();
        String query = "SELECT exemplaire.NUMEX FROM EXEMPLAIRE left JOIN pret on pret.numex = exemplaire.numex where Pret.numex not in (select numex from pret where Fin > '2015-04-21') and NumLivre = " +NumBox.getText();

        try {
            state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rst = state.executeQuery(query);
            while(rst.next()) CbExDispo.addItem(rst.getInt(1));


        } catch (SQLException e) {
            System.out.println("Numero de livre invalide...");
        }
        catch (NullPointerException e) {
            System.out.println("Numero de livre invalide...");

        }finally {
            try {
                rst.close();
                state.close();
            } catch (SQLException e) {

            } catch (NullPointerException e) {
               //System.out.println("Numero de livre invalide...");

            }
        }
    }

    private void InsertPret(){

        String query = "Insert into Pret values(" + SelectNumAd() +"," +CbExDispo.getSelectedItem().toString()+","+"'2015-04-21','2015-05-21')";
        try {
            Statement state = conn.createStatement();
            state.executeUpdate(query);

        } catch (SQLException e) {
           System.out.print(e);
        }


    }

}
