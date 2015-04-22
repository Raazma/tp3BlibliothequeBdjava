import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


/**
 * Created by Razma on 2015-04-21.
 */
public class Emprunt {
    public JPanel Emprunt;
    private JTextField AdBox;
    private JTextField NumBox;
    private JComboBox CbExDispo;
    private JButton BtnEmp;
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
                if(Validation())
                InsertPret();
                else
                {

                    JOptionPane.showMessageDialog(Emprunt, "Veuiller rentrer tous les champs Svp", "Attention!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        ListePret();
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
        String query = "SELECT NumEx FROM EXEMPLAIRE WHERE (NUMLIVRE = "+NumBox.getText()+" AND numex NOT IN (SELECT NUMEX FROM PRET WHERE FIN >(SELECT SYSDATE FROM DUAL)))";

        try {
            state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rst = state.executeQuery(query);
            while(rst.next()) CbExDispo.addItem(rst.getInt(1));


        } catch (SQLException e) {
            System.out.println(e);
        }
        catch (NullPointerException e) {
            System.out.println(e);

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

        String query = "Insert into Pret values(" + SelectNumAd() +"," +CbExDispo.getSelectedItem().toString()+","+"(Select Sysdate from dual),(Select Sysdate + 14 from dual))";
        try {
            Statement state = conn.createStatement();
            state.executeUpdate(query);

        } catch (SQLException e) {
           System.out.print(e);
        }
    }

    private void ListePret()
    {
        DefaultListModel mod = new DefaultListModel();
        String query = "SELECT * FROM EXEMPLAIRE WHERE (NUMLIVRE ="+NumBox.getText()+"+ AND numex NOT IN (SELECT NUMEX FROM PRET WHERE FIN >(SELECT SYSDATE FROM DUAL)))";

        try {
            Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rst = state.executeQuery(query);
             ResultSetMetaData meta = rst.getMetaData();
            String[] Data = new String[meta.getColumnCount()];
            int i = 0;
            while(rst.next()){

               Data[i] = rst.getString(1);
               Data[i] = Integer.toString(rst.getInt(2));
            }

        }
        catch (SQLException e){


        }

    }

    private boolean Validation(){
        if(!NumBox.getText().trim().isEmpty() && !AdBox.getText().trim().isEmpty() && CbExDispo.getSelectedIndex() != -1)
        return true;
        else return false;


    }
}
