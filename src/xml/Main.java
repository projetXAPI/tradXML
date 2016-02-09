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
		//On crée une instance de SAXBuilde
		FichierXML fichierMot = new FichierXML("sourceXML/words/EN2001a.A.words.xml");
	      
	    Xapi xAPI = new Xapi();
	    RemoteLRS lrs = xAPI.initXapi();
	    xAPI.envoieOccurence(lrs, fichierMot.occurence());
	}
	
	

}
