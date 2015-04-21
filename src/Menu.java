import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import oracle.jdbc.driver.*;
import oracle.jdbc.pool.*;

/**
 * Created by Razma on 2015-04-18.
 */
public class Menu {
    private JPanel Menu;
    private JButton BtnInscription;
    private JButton BtnRecherche;
    private JButton BtnListerPret;
    private JButton BtnListeLivre;
    private JButton BtnGestion;
    private JButton BtnMustEmprunt;
    public   Connection conn = null;

    public Menu() {

        BtnInscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame("Inscription");
                frame.setContentPane(new Inscription(conn).Inscription);
              //  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        BtnRecherche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                JFrame frame = new JFrame("Recherche");
                frame.setContentPane(new Recherche(conn).Recherche);
              //  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

            }
        });
        BtnListerPret.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame("Pret");
                frame.setContentPane(new ListerDesPrets(conn).Pret);
              //  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        BtnListeLivre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame("Liste");
                frame.setContentPane(new ListerLivres(conn).Liste);
              //  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });

        //Call Connection
        Connection();

        BtnGestion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Gestion");
                frame.setContentPane(new GestionAd(conn).Gestion);
             //   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public void Connection()
    {
        String user ="Paquette";
        String mdep ="ORACLE2";
        String url="jdbc:oracle:thin:@205.237.244.251:1521:orcl";
        try {
            OracleDataSource ods = new OracleDataSource();
            ods.setURL(url);
            ods.setUser(user);
            ods.setPassword(mdep);
            conn = ods.getConnection();
            //System.out.print("Work");
        }
        catch (SQLException e) {
            System.out.print("Connection Didnt Work");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu");
        frame.setContentPane(new Menu().Menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
