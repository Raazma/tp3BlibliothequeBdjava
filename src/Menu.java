import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import oracle.jdbc.driver.*;
import oracle.jdbc.pool.*;
import java.util.Vector;
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
    private JButton BtnRanking;
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

                JFrame frame = new JFrame("Emprunt");
                frame.setContentPane(new Emprunt(conn).Emprunt);
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
        BtnMustEmprunt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MakePretTable();
            }
        });
        BtnRanking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SetRanking();
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



    private void MakePretTable(){
        Vector rows = new Vector();

     String query = " SELECT livre.titre, genre.description, pret.debut, pret.fin ,adherent.nom, adherent.prenom  FROM EXEMPLAIRE " +
       " inner join livre on livre.numlivre = exemplaire.numlivre " +
        " inner join pret on pret.numex = Exemplaire.numex " +
        " inner join adherent on adherent.NUMADHERENT = pret.NUMADHERENT " +
       " inner join genre on genre.CODEGENRE = livre.codegenre " +
       " WHERE ( exemplaire.numex  IN (SELECT NUMEX FROM PRET WHERE FIN >(SELECT SYSDATE FROM DUAL)))";

        try {
            Statement state = conn.createStatement();
            ResultSet rst = state.executeQuery(query);

            while(rst.next()){
                Vector line = new Vector();
                line.add(rst.getString(1));
                line.add(rst.getString(2));
                line.add(rst.getString(3));
                line.add(rst.getString(4));
                line.add(rst.getString(5));
                line.add(rst.getString(6));
                rows.add(line);
            }
            Vector headers = new Vector();
            headers.add("Titre");
            headers.add("Genre");
            headers.add("Debut");
            headers.add("Fin");
            headers.add("Nom");
            headers.add("Prenom");

            JTable tabDonnees = new JTable(rows,headers);
            JScrollPane  JScrollPane1 = new JScrollPane(tabDonnees);
            JScrollPane1.setViewportView(tabDonnees);
            JFrame frameemp = new JFrame("Emprunts");

            frameemp.getContentPane().add(JScrollPane1);
            frameemp.setVisible(true);
            frameemp.setSize(600,200);
            JScrollPane1.validate();
        }
        catch (SQLException e){

        }



    }

    private void SetRanking(){

        Vector rows = new Vector();



        try {
            CallableStatement Callist = conn.prepareCall("{ call  Biblio.Ranking(?)}");
            Callist.registerOutParameter(1,OracleTypes.CURSOR);
             Callist.execute();
            ResultSet rst = (ResultSet)Callist.getObject(1);

            while(rst.next()){
                Vector line = new Vector();
                line.add(rst.getInt(1));
                line.add(rst.getString(2));
                rows.add(line);
            }
            Vector headers = new Vector();
            headers.add("Nombre de fois preter");
            headers.add("Titre");


            JTable tabDonnees = new JTable(rows,headers);
            JScrollPane  JScrollPane1 = new JScrollPane(tabDonnees);
            JScrollPane1.setViewportView(tabDonnees);
            JFrame frameemp = new JFrame("Ranking");

            frameemp.getContentPane().add(JScrollPane1);
            frameemp.setVisible(true);
            frameemp.setSize(300,200);
            JScrollPane1.validate();
        }
        catch (SQLException e){
           System.out.println(e);
        }



    }
}
