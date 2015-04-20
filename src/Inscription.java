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
                Insert();
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



}
