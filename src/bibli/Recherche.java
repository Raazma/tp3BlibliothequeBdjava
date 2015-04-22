package bibli;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Razma on 2015-04-18.
 */
public class Recherche {
    public JPanel Recherche;
    private JTextField Maisont;
    private JTextField Titret;
    private JTextField Genret;
    private JTextField Datet;
    private JTextField Ateurt;
    private JButton RechercheTirebtn;
    private JTextField Rtitre;
    private JLabel NumLivre;
    private JButton Precedent;
    private JButton Suivant;
    public ResultSet rst = null;
    public Statement state = null;
    Connection conn;

    public Recherche(Connection conn) {

       this.conn = conn;
        RechercheTirebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SetResearchT();
            }
        });
        Suivant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rst.next();
                    NumLivre.setText(Integer.toString(rst.getInt(1)));
                    Titret.setText(rst.getString(2));
                    Ateurt.setText(rst.getString(3));
                    Genret.setText(rst.getString(8));
                    Datet.setText(rst.getString(5));
                    Maisont.setText((rst.getString(6)));
                    Rtitre.setText(rst.getString(2));
                }
                catch (SQLException u){}
            }
        });
        Precedent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rst.previous();
                    NumLivre.setText(Integer.toString(rst.getInt(1)));
                    Titret.setText(rst.getString(2));
                    Ateurt.setText(rst.getString(3));
                    Genret.setText(rst.getString(8));
                    Datet.setText(rst.getString(5));
                    Maisont.setText((rst.getString(6)));
                    Rtitre.setText(rst.getString(2));
                }
                catch (SQLException u){}
            }
        });
    }



    private void SetResearchT(){
        String query = "select * from livre inner join genre on livre.codegenre = genre.codegenre where Titre like '%" + Rtitre.getText() + "%' or Auteur like '%" + Rtitre.getText() + "%'";

        try {
            state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rst = state.executeQuery(query);
            rst.next();
            NumLivre.setText(Integer.toString(rst.getInt(1)));
            Titret.setText(rst.getString(2));
            Ateurt.setText(rst.getString(3));
            Genret.setText(rst.getString(8));
            Datet.setText(rst.getString(5));
            Maisont.setText((rst.getString(6)));
            Rtitre.setText(rst.getString(2));

        } catch (SQLException e) {

        } finally {
           // try {
              //  rst.close();
              //  state.close();
         //   } catch (SQLException e) {

           // }
        }


    }
}
