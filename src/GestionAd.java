import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import oracle.jdbc.driver.*;
import oracle.jdbc.pool.*;
/**
/**
 * Created by 201037629 on 2015-04-20.
 */
public class GestionAd {
    public JPanel Gestion;
    private JLabel NumAd;
    private JTextField TelephoneBox;
    private JTextField AdresseBox;
    private JTextField PrenomBox;
    private JTextField NomBox;
    private JButton Precedent;
    private JButton Suivant;
    private JButton Modifier;
    private JButton Suprimer;
    private JLabel Num;
    Connection conn;
    Statement state = null;
    ResultSet rst = null;

    public GestionAd(Connection conn)
    {
        this.conn = conn;
        SelectAllEmploye();

        Suivant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rst.next();
                    NumAd.setText(Integer.toString(rst.getInt(1)));
                    NomBox.setText(rst.getString(2));
                    PrenomBox.setText(rst.getString(3));
                    AdresseBox.setText(rst.getString(4));
                    TelephoneBox.setText(rst.getString(5));
                }
                catch (SQLException u){}
            }
        });
        Precedent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rst.previous();
                    NumAd.setText(Integer.toString(rst.getInt(1)));
                    NomBox.setText(rst.getString(2));
                    PrenomBox.setText(rst.getString(3));
                    AdresseBox.setText(rst.getString(4));
                    TelephoneBox.setText(rst.getString(5));
                }
                catch (SQLException u){}
            }
        });
        Suprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Delete();
                SelectAllEmploye();
            }
        });
        Modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Validation()) {
                    Update();
                    SelectAllEmploye();
                }
                else
                {

                    JOptionPane.showMessageDialog(Gestion, "Veuiller rentrer tous les champs Svp", "Attention!", JOptionPane.WARNING_MESSAGE);
                    SelectAllEmploye();
                }
            }
        });
    }
   private void Delete(){

         String query = "delete from Adherent where NumAdherent = " + NumAd.getText();
       try {
           Statement StateInsert = conn.createStatement();
           StateInsert.executeUpdate(query);
       }
       catch (SQLException e) {
           System.out.println( e);
       }
   }

    private void SelectAllEmploye() {

        String query = "select * from Adherent";

        try {
            state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rst = state.executeQuery(query);
            rst.next();
            NumAd.setText(Integer.toString(rst.getInt(1)));
            NomBox.setText(rst.getString(2));
            PrenomBox.setText(rst.getString(3));
            AdresseBox.setText(rst.getString(4));
            TelephoneBox.setText(rst.getString(5));


        } catch (SQLException e) {

        } finally {
            // try {
            //   rst.close();
            //   state.close();
            // } catch (SQLException e) {

            // }
        }
    }
    private void Update(){
        String query = "Update  Adherent Set Nom ='"+NomBox.getText()+"', " + "Prenom ='"+ PrenomBox.getText()+"', Adresse='" +AdresseBox.getText()+"',"+"Telephone ='"+TelephoneBox.getText()+"' where NumAdherent = " + NumAd.getText();
        try {
            Statement StateInsert = conn.createStatement();
            StateInsert.executeUpdate(query);
        }
        catch (SQLException e) {
            System.out.println( e);
        }
    }

    private boolean Validation(){

      if(!NomBox.getText().trim().isEmpty()&& !PrenomBox.getText().trim().isEmpty() && !AdresseBox.getText().trim().isEmpty()&& !TelephoneBox.getText().trim().isEmpty())
          return true;
          else
          return false;
    }

}
