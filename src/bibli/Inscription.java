package bibli;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import oracle.jdbc.driver.*;
import oracle.jdbc.pool.*;
/**
 * Created by Razma on 2015-04-18.
 */
public class Inscription {
    public JPanel Inscription;
    private JTextField Tel;
    private JTextField Name;
    private JTextField Prenom;
    private JTextField Add;
    private JButton enregistrerButton;
     public  Connection conn;

    public Inscription(Connection conn) {
        this.conn = conn;

        enregistrerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Validation()) {
                    Insert();
                    ClearBox();
                }
                else
                {
                    JOptionPane.showMessageDialog(Inscription, "Veuiller rentrer tous les champs Svp", "Attention!", JOptionPane.WARNING_MESSAGE);

                }


            }
        });
    }

    public void Insert()
    {
        String query = "Insert into ADHERENT (Nom,Prenom,Adresse,Telephone) values('" + Name.getText()+"', " + "'" + Prenom.getText() + "', '"
                + Add.getText() + "', '" + Tel.getText()+"')";
        try {
            Statement StateInsert = conn.createStatement();
            StateInsert.executeUpdate(query);
        }
        catch (SQLException e) {
            System.out.println( e);
        }
    }

    private boolean Validation(){

        if(!Name.getText().trim().isEmpty() && !Prenom.getText().trim().isEmpty() &&! Add.getText().trim().isEmpty() && !Tel.getText().trim().isEmpty())
            return true;
        else
            return false;


    }

    private void ClearBox(){

        Name.setText("");
        Prenom.setText("");
        Add.setText("");
        Tel.setText("");

    }
}
