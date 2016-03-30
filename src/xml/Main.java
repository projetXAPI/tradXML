package xml;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.rusticisoftware.tincan.Activity;
import com.rusticisoftware.tincan.Agent;
import com.rusticisoftware.tincan.RemoteLRS;
import com.rusticisoftware.tincan.Statement;
import com.rusticisoftware.tincan.TCAPIVersion;
import com.rusticisoftware.tincan.Verb;
import com.rusticisoftware.tincan.lrsresponses.StatementLRSResponse;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

public class Main {
	//static String listDossier[] = {"words", "topic", "dossierTruc" , "DossierDia"}; //Stock la liste des dossier à traduire

	
	public static void main(String[] args) throws MalformedURLException, URISyntaxException {
		/*if(args[0] == null || args[1] == null){
			System.out.println("Il faut passer des arguments à la commande : leCheminVersLeCorpus leNomDeLaReu");
			System.exit(1);
		}*/
		
	//rojet10.jar ../tradXML/sourceXML/ ES2002d

		String chemin = "../tradXML/sourceXML/";  //args[0];
		String nomReu = "ES2002d"; //args[1];
		FichierXML fichierMot = null;
		FichierXML fichierInfoReu = null;
		
		FichierXML fichierPhraseA = null;
		FichierXML fichierPhraseB = null;
		FichierXML fichierPhraseC = null;
		FichierXML fichierPhraseD = null;
		
		FichierXML fichierRoleA = null;
		FichierXML fichierRoleB = null;
		FichierXML fichierRoleC = null;
		FichierXML fichierRoleD = null;
		String[] infoReu = null;
		RemoteLRS lrs = null;
		//System.out.println(chemin);
	
		//On crée une instance de SAXBuilde
		try {
			System.out.println("Récupération de la réunion...");
			//fichierMot = new FichierXML("sourceXML/words/EN2001a.A.words.xml");
			//System.out.println("/home/vincent/tradXML/sourceXML/words/EN2001a.A.words.xml");
			//fichierMot = new FichierXML(chemin + "/words/"+ nomReu +".A.words.xml");
			fichierInfoReu = new FichierXML(chemin + "/corpusResources/meetings.xml");
			fichierPhraseA = new FichierXML(chemin + "/words/"+nomReu+".A.words.xml");
			fichierPhraseB = new FichierXML(chemin + "/words/"+nomReu+".B.words.xml");
			fichierPhraseC = new FichierXML(chemin + "/words/"+nomReu+".C.words.xml");
			fichierPhraseD = new FichierXML(chemin + "/words/"+nomReu+".D.words.xml");
			fichierRoleA = new FichierXML(chemin + "/participantRoles/"+nomReu+".A.role.xml");
			fichierRoleB = new FichierXML(chemin + "/participantRoles/"+nomReu+".B.role.xml");
			fichierRoleC = new FichierXML(chemin + "/participantRoles/"+nomReu+".C.role.xml");
			fichierRoleD = new FichierXML(chemin + "/participantRoles/"+nomReu+".D.role.xml");
		} catch (Exception e) {
			System.out.println("Le chemin du corpus ou le nom de la réunion est invalide");
			System.exit(1);
		}
		System.out.println("Réunion récupérée");
		
		System.out.println("Conversion des données en statements...");	      
	    Xapi xAPI = new Xapi();
	    List<Statement> listStatements = new LinkedList();
	    try {
	    	lrs = xAPI.initXapi();
	    	
	    	listStatements.addAll(xAPI.creerStatementsPhrases(fichierPhraseA.creationPhrase(), nomReu, "A"));
	    	listStatements.addAll(xAPI.creerStatementsPhrases(fichierPhraseB.creationPhrase(), nomReu, "B"));
	    	listStatements.addAll(xAPI.creerStatementsPhrases(fichierPhraseC.creationPhrase(), nomReu, "C"));
	    	listStatements.addAll(xAPI.creerStatementsPhrases(fichierPhraseD.creationPhrase(), nomReu, "D"));
	    	listStatements.addAll(xAPI.creerStatementsRoleTemps(fichierRoleA.creationRoleTemps(), nomReu, "A"));
	    	listStatements.addAll(xAPI.creerStatementsRoleTemps(fichierRoleB.creationRoleTemps(), nomReu, "B"));
	    	listStatements.addAll(xAPI.creerStatementsRoleTemps(fichierRoleC.creationRoleTemps(), nomReu, "C"));
	    	listStatements.addAll(xAPI.creerStatementsRoleTemps(fichierRoleD.creationRoleTemps(), nomReu, "D"));
	    		    	
	    	listStatements.addAll(xAPI.creerStatementsRole(fichierRoleA.creationRole(), nomReu, "A"));
	    	listStatements.addAll(xAPI.creerStatementsRole(fichierRoleB.creationRole(), nomReu, "B"));
	    	listStatements.addAll(xAPI.creerStatementsRole(fichierRoleC.creationRole(), nomReu, "C"));
	    	listStatements.addAll(xAPI.creerStatementsRole(fichierRoleD.creationRole(), nomReu, "D"));
	    	
	    	listStatements.addAll(xAPI.creationStatementInfoReu(fichierInfoReu.infoReu(nomReu), nomReu));
	    	
	    	
	    	HashMap<String, String> test = fichierInfoReu.infoReu(nomReu);

	    	for(Map.Entry mapentry : test.entrySet()){
	    		System.out.println("Key " + mapentry.getKey() + " Value : " + mapentry.getValue());
	    	}
	    	
	    	//System.exit(1);
	    	
	    } catch(Exception e) {
	    	System.out.println("Les données du corpus ne peuvent pas être traduites");
	    	System.out.println(e.getMessage());
	    	System.exit(1);
	    }
	    System.out.println("Données converties en statements");
	    
	    System.out.println("Envoi des statements sur le serveur...");
	    try {
	    	//xAPI.envoieOccurence(lrs, fichierMot.occurence());
	    	//xAPI.envoieInfoReu(lrs, fichierInfoReu.infoReu(nomReu), nomReu);
	    	xAPI.envoieStatements(lrs, listStatements);
	    	
	    } catch(Exception e) {
	    	System.out.println("L'envoi des statements sur le serveur est impossible, veuillez réessayer ultérieurement");
	    	System.out.println(e.getMessage());
	    	System.exit(1);
	    }
	    System.out.println("Statements envoyés sur le serveur");
	    System.out.println("Fin de conversion");
	    System.out.println(listStatements.size());
	}
	
	

}
