import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.sql.*;
import oracle.jdbc.driver.*;
import oracle.jdbc.pool.*;
/**
 * Created by Razma on 2015-04-18.
 */
public class ListerLivres {
    public JPanel Liste;
    private JTextField Titre;
    private JTextField Edition;
    private JTextField Date;
    private JTextField Genre;
    private JTextField Auteur;
    private JButton BtnPrecedent;
    private JButton BtnSuivant;
    private JComboBox GenreB;
    public Connection conn;
   public ResultSet rst = null;
  public  Statement state = null;
    public ListerLivres(Connection conn) {
        this.conn = conn;


        GenreB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                //System.out.print( GenreB.getSelectedItem().toString());
                SetTextBox();

            }
        });


        SetCombobox();


        BtnSuivant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // try {
                    //rst.next();
                  //  Titre.setText(rst.getString(2));
                 //   Auteur.setText(rst.getString(3));
                  //  Genre.setText(rst.getString(8));
                 //   Date.setText(rst.getString(5));
                 //   Edition.setText((rst.getString(6)));
               // }
               // catch (SQLException u)
               // {}
            }
        });
    }

    private void SetCombobox() {
        ResultSet rst = null;
        Statement state = null;
        String query = "Select * from Genre";
        try {
            state = conn.createStatement();
            rst = state.executeQuery(query);
            while (rst.next())
                GenreB.addItem(rst.getString("Description"));

        } catch (SQLException e) {

        } finally {
            try {
                rst.close();
                state.close();
            } catch (SQLException e) {

            }
        }
    }



    private void SetTextBox() {

        String query = "select * from livre inner join genre on livre.codegenre = genre.codegenre where genre.description = '" + GenreB.getSelectedItem().toString() + "'";



        try {
            state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rst = state.executeQuery(query);
               rst.next();
                Titre.setText(rst.getString(2));
                Auteur.setText(rst.getString(3));
                Genre.setText(rst.getString(8));
                Date.setText(rst.getString(5));
                Edition.setText((rst.getString(6)));

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