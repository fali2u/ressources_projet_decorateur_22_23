package exodecorateur_angryballs.maladroit.vues;

import java.io.File;
import java.util.Vector;

import exodecorateur_angryballs.maladroit.OutilsConfigurationBilleHurlante;
import exodecorateur_angryballs.maladroit.modele.decorateur.Bille;
import musique.SonLong;


public class TestCadreAngryBallsSeul
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
   
SonLong hurlements[] = SonLong.toTableau(sonsLongs); // on obtient un tableau de SonLong
  
Vector<Bille> billes = new Vector<Bille>();
int choixHurlementInitial = 0;
CadreAngryBalls cadre =new CadreAngryBalls("angry balls", "animation de billes marrantes", billes, hurlements, choixHurlementInitial);
cadre.montrer();
}

}
