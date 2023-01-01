package exodecorateur_angryballs.maladroit;

import java.awt.Color;
import java.io.File;
import java.util.Vector;

import exodecorateur_angryballs.maladroit.modele.decorateur.Bille;
import exodecorateur_angryballs.maladroit.modele.decorateur.BilleNue;
import exodecorateur_angryballs.maladroit.modele.decorateur.acceleration.BilleFrottement;
import exodecorateur_angryballs.maladroit.modele.decorateur.acceleration.BilleNewton;
import exodecorateur_angryballs.maladroit.modele.decorateur.acceleration.BillePesanteur;
import exodecorateur_angryballs.maladroit.modele.decorateur.collision.BilleBloquee;
import exodecorateur_angryballs.maladroit.modele.decorateur.collision.BillePasseMuraille;
import exodecorateur_angryballs.maladroit.modele.decorateur.collision.BilleRebond;
import exodecorateur_angryballs.maladroit.modele.decorateur.hurlante.BilleHurlante;
import exodecorateur_angryballs.maladroit.modele.state.ManagerEtat;
import mesmaths.geometrie.base.Vecteur;
import musique.SonLong;
import exodecorateur_angryballs.maladroit.vues.CadreAngryBalls;


/**
 * Gestion d'une liste de billes en mouvement ayant toutes un comportement diff�rent
 * 
 * Id�al pour mettre en place le DP decorator
 * */
public class TestAngryBalls
{

/**
 * @param args
 */
public static void main(String[] args)
{
//---------------------- gestion des bruitages : param�trage du chemin du dossier contenant les fichiers audio --------------------------

File file = new File(""); // l� o� la JVM est lanc�e : racine du projet

File r�pertoireSon = new File(file.getAbsoluteFile(),
    "exodecorateur_angryballs"+File.separatorChar+
    "maladroit"+File.separatorChar+"bruits");

//-------------------- chargement des sons pour les hurlements --------------------------------------

Vector<SonLong> sonsLongs = OutilsConfigurationBilleHurlante.chargeSons(r�pertoireSon, "config_audio_bille_hurlante.txt");
 
SonLong hurlements[] = SonLong.toTableau(sonsLongs);                // on obtient un tableau de SonLong

//------------------- cr�ation de la liste (pour l'instant vide) des billes -----------------------

Vector<Bille> billes = new Vector<Bille>();

//---------------- cr�ation de la vue responsable du dessin des billes -------------------------

int choixHurlementInitial = 0;
CadreAngryBalls cadre = new CadreAngryBalls("Angry balls",
                                        "Animation de billes ayant des comportements diff�rents. Situation id�ale pour mettre en place le DP Decorator",
                                        billes,hurlements, choixHurlementInitial);

cadre.montrer(); // on rend visible la vue

//------------- remplissage de la liste avec 5 billes -------------------------------



double xMax, yMax;
double vMax = 0.1;
xMax = cadre.largeurBillard();      // abscisse maximal
yMax = cadre.hauteurBillard();      // ordonn�e maximale

double rayon = 0.05*Math.min(xMax, yMax); // rayon des billes : ici toutes les billes ont le m�me rayon, mais ce n'est pas obligatoire

Vecteur p0, p1, p2, p3, p4,  v0, v1, v2, v3, v4;    // les positions des centres des billes et les vecteurs vitesse au d�marrage. 
                                                    // Elles vont �tre choisies al�atoirement

//------------------- cr�ation des vecteurs position des billes ---------------------------------

p0 = Vecteur.cr�ationAl�atoire(0, 0, xMax, yMax);
p1 = Vecteur.cr�ationAl�atoire(0, 0, xMax, yMax);
p2 = Vecteur.cr�ationAl�atoire(0, 0, xMax, yMax);
p3 = Vecteur.cr�ationAl�atoire(0, 0, xMax, yMax);
p4 = Vecteur.cr�ationAl�atoire(0, 0, xMax, yMax);

//------------------- cr�ation des vecteurs vitesse des billes ---------------------------------

v0 = Vecteur.cr�ationAl�atoire(-vMax, -vMax, vMax, vMax);
v1 = Vecteur.cr�ationAl�atoire(-vMax, -vMax, vMax, 0);
v2 = Vecteur.cr�ationAl�atoire(-vMax, -vMax, vMax, vMax);
v3 = Vecteur.cr�ationAl�atoire(-vMax, -vMax, vMax, vMax);
v4 = Vecteur.cr�ationAl�atoire(-vMax, -vMax, vMax, vMax);

//--------------- ici commence la partie � changer ---------------------------------

    billes.add(new BilleRebond(new BilleNue(p0, rayon, v0, Color.red)));
    billes.add(new BillePesanteur(new BilleFrottement(new BilleRebond(new BilleNue(p1, rayon,v1, Color.yellow))),new Vecteur(0, 0.001)));
    billes.add(new BilleNewton(new BilleFrottement(new BilleRebond(new BilleNue(p2, rayon,v2, Color.green)))));
    billes.add(new BillePasseMuraille(new BilleNue(p3, rayon, v3, Color.cyan)));
    ManagerEtat managerEtat = new ManagerEtat(billes, cadre.billard);


    BilleHurlante billeNoire;         // cas particulier de la bille qui hurle

    billes.add(billeNoire = new BilleHurlante(new BilleRebond(new BilleNue(p4, rayon, v4,  Color.black)),hurlements[choixHurlementInitial], cadre));

    cadre.addChoixHurlementListener(billeNoire) ; // � pr�sent on peut changer le son de la bille qui hurle

//---------------------- ici finit la partie � changer -------------------------------------------------------------

System.out.println("billes = " + billes);


//-------------------- cr�ation de l'objet responsable de l'animation (c'est un thread s�par�) -----------------------

AnimationBilles animationBilles = new AnimationBilles(billes, cadre);

//----------------------- mise en place des �couteurs de boutons qui permettent de contr�ler (un peu...) l'application -----------------

EcouteurBoutonLancer �couteurBoutonLancer = new EcouteurBoutonLancer(animationBilles);
EcouteurBoutonArreter �couteurBoutonArr�ter = new EcouteurBoutonArreter(animationBilles); 

//------------------------- activation des �couteurs des boutons et �a tourne tout seul ------------------------------


cadre.lancerBilles.addActionListener(�couteurBoutonLancer);             // pourrait �tre remplac� par Observable - Observer 
cadre.arr�terBilles.addActionListener(�couteurBoutonArr�ter);           // pourrait �tre remplac� par Observable - Observer



}

}
