package exodecorateur_angryballs.maladroit.vues;

import java.awt.*;
import java.awt.event.ItemListener;
import java.util.Vector;

import exodecorateur_angryballs.maladroit.modele.decorateur.Bille;
import musique.SonLong;
import outilsvues.EcouteurTerminaison;

import outilsvues.Outils;

/**
 * Vue dessinant les billes et contenant les 3 boutons de contr�le (arr�t du programme, lancer les billes, arr�ter les billes) 
 * et contenant la ligne des boutons de choix des sons pour la bille hurlante
 * 
 *  ICI : IL N'Y A RIEN A CHANGER 
 *  
 * 
 * */
public class CadreAngryBalls extends Frame implements VueBillard
{
TextField pr�sentation;
public Billard billard;
public Button lancerBilles, arr�terBilles;
Panel haut, centre, bas, ligneBoutonsLancerArr�t;
PanneauChoixHurlement ligneBoutonsChoixHurlement;

EcouteurTerminaison ecouteurTerminaison;

public CadreAngryBalls(String titre, String message, Vector<Bille> billes, SonLong [] hurlements, int choixHurlementInitial) throws HeadlessException
{
super(titre);
Outils.place(this, 0.33, 0.33, 0.5, 0.5);
this.ecouteurTerminaison = new EcouteurTerminaison(this);


this.haut = new Panel(); this.haut.setBackground(Color.LIGHT_GRAY);
this.add(this.haut,BorderLayout.NORTH);

this.centre = new Panel();
this.add(this.centre,BorderLayout.CENTER);

this.bas = new Panel(); this.bas.setBackground(Color.LIGHT_GRAY);
this.add(this.bas,BorderLayout.SOUTH);

this.pr�sentation = new TextField(message, 100); this.pr�sentation.setEditable(false);
this.haut.add(this.pr�sentation);

this.billard = new Billard(billes);
this.add(this.billard);

//------------------- placement des composants du bas du cadre -------------------------------

int nombreLignes = 2, nombreColonnes = 1;

this.bas.setLayout(new GridLayout(nombreLignes, nombreColonnes));

//---------------- placement des boutons lancer - arr�ter ------------------------------------

this.ligneBoutonsLancerArr�t = new Panel(); this.bas.add(this.ligneBoutonsLancerArr�t);


this.lancerBilles = new Button("lancer les billes"); this.ligneBoutonsLancerArr�t.add(this.lancerBilles);
this.arr�terBilles = new Button("arr�ter les billes"); this.ligneBoutonsLancerArr�t.add(this.arr�terBilles);

//---------------- placement de la ligne de boutons de choix des sons pour le hurlement ------

this.ligneBoutonsChoixHurlement = new PanneauChoixHurlement(hurlements, choixHurlementInitial); this.bas.add(this.ligneBoutonsChoixHurlement);

}

public double largeurBillard() 
{
return this.billard.getWidth();
}

public double hauteurBillard()
{
return this.billard.getHeight();
}

@Override
public void miseAJour()
{
this.billard.repaint();
}

/* (non-Javadoc)
 * @see exodecorateur.vues.VueBillard#montrer()
 */
@Override
public void montrer()
{
this.setVisible(true);
}

public void addChoixHurlementListener(ItemListener �couteurChoixHurlant)
{
int i;

for ( i = 0; i < this.ligneBoutonsChoixHurlement.boutons.length; ++i) this.ligneBoutonsChoixHurlement.boutons[i].addItemListener(�couteurChoixHurlant);

}

}