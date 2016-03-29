package xml;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.rusticisoftware.tincan.Activity;
import com.rusticisoftware.tincan.Agent;
import com.rusticisoftware.tincan.RemoteLRS;
import com.rusticisoftware.tincan.Statement;
import com.rusticisoftware.tincan.TCAPIVersion;
import com.rusticisoftware.tincan.Verb;
import com.rusticisoftware.tincan.lrsresponses.StatementLRSResponse;

public class Main {
	//static String listDossier[] = {"words", "topic", "dossierTruc" , "DossierDia"}; //Stock la liste des dossier à traduire

	
	public static void main(String[] args) throws MalformedURLException, URISyntaxException {
		if(args[0] == null || args[1] == null){
			System.out.println("Il faut passer des arguments à la commande : leCheminVersLeCorpus leNomDeLaReu");
			System.exit(1);
		}
		String chemin = args[0];
		String nomReu = args[1];
		FichierXML fichierMot = null;
		FichierXML fichierInfoReu = null;
		String[] infoReu = null;
		RemoteLRS lrs = null;
		//System.out.println(chemin);
	
		//On crée une instance de SAXBuilde
		try {
			System.out.println("Récupération de la réunion...");
			//fichierMot = new FichierXML("sourceXML/words/EN2001a.A.words.xml");
			//System.out.println("/home/vincent/tradXML/sourceXML/words/EN2001a.A.words.xml");
			fichierMot = new FichierXML(chemin + "/words/"+ nomReu +".A.words.xml");
			fichierInfoReu = new FichierXML(chemin + "/corpusResources/meetings.xml");
		} catch (Exception e) {
			System.out.println("Le chemin du corpus ou le nom de la réunion est invalide");
			System.exit(1);
		}
		System.out.println("Réunion récupérée");
		
		System.out.println("Conversion des données en statements...");	      
	    Xapi xAPI = new Xapi();
	    try {
	    	lrs = xAPI.initXapi();
	    } catch(Exception e) {
	    	System.out.println("Les données du corpus ne peuvent pas être traduites");
	    	System.exit(1);
	    }
	    System.out.println("Données converties en statements");
	    
	    System.out.println("Envoie des statements sur le serveur...");
	    try {
	    	//xAPI.envoieOccurence(lrs, fichierMot.occurence());
	    	xAPI.envoieInfoReu(lrs, fichierInfoReu.infoReu(nomReu), nomReu);
	    } catch(Exception e) {
	    	System.out.println("L'envoi des statements sur le serveur est impossible, veuillez réessayer ultérieurement");
	    	System.exit(1);
	    }
	    System.out.println("Statements envoyés sur le serveur");
	    System.out.println("Fin de conversion");
	}
	
	

}
