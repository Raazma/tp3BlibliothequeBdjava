CREATE
  TABLE Adherent
  (
    NumAdherent NUMBER (6) NOT NULL ,
    Nom         VARCHAR2 (40) ,
    Prenom      VARCHAR2 (40) ,
    Adresse     VARCHAR2 (50) ,
    Telephone   VARCHAR2 (20)
  ) ;
ALTER TABLE Adherent ADD CONSTRAINT Adherent_PK PRIMARY KEY ( NumAdherent ) ;

CREATE
  TABLE Exemplaire
  (
    NumEx    NUMBER NOT NULL ,
    NumLivre NUMBER (4) NOT NULL
  ) ;
ALTER TABLE Exemplaire ADD CONSTRAINT Exemplaire_PK PRIMARY KEY ( NumEx ) ;

CREATE
  TABLE Genre
  (
    CodeGenre   NUMBER (4) NOT NULL ,
    Description VARCHAR2 (40)
  ) ;
ALTER TABLE Genre ADD CONSTRAINT Genre_PK PRIMARY KEY ( CodeGenre ) ;

CREATE
  TABLE Livre
  (
    NumLivre      NUMBER (4) NOT NULL ,
    Titre         VARCHAR2 (40) ,
    Auteur        VARCHAR2 (40) ,
    CodeGenre     NUMBER (4) NOT NULL ,
    DateParution  DATE ,
    MaisonEdition VARCHAR2 (60)
  ) ;
ALTER TABLE Livre ADD CONSTRAINT Livre_PK PRIMARY KEY ( NumLivre ) ;

CREATE
  TABLE Pret
  (
    "NumAdherent " NUMBER (6) NOT NULL ,
    NumEx          NUMBER NOT NULL ,
    Debut          DATE ,
    Fin            DATE
  ) ;
ALTER TABLE Pret ADD CONSTRAINT Pret_PK PRIMARY KEY ( "NumAdherent ", NumEx ) ;

-- Error - Foreign Key Exemplaire_Exemplaire_FK has no columns

ALTER TABLE Exemplaire ADD CONSTRAINT Exemplaire_Livre_FK FOREIGN KEY (
NumLivre ) REFERENCES Livre ( NumLivre ) ;

ALTER TABLE Livre ADD CONSTRAINT Livre_Genre_FK FOREIGN KEY ( CodeGenre )
REFERENCES Genre ( CodeGenre ) ;

ALTER TABLE Pret ADD CONSTRAINT Pret_Adherent_FK FOREIGN KEY ( "NumAdherent " )
REFERENCES Adherent ( NumAdherent ) ;

ALTER TABLE Pret ADD CONSTRAINT Pret_Exemplaire_FK FOREIGN KEY ( NumEx )
REFERENCES Exemplaire ( NumEx ) ;
