package xml;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
	static org.jdom2.Document document;
	static Element racine;
	static HashMap<String, Integer> occurence = new HashMap<String, Integer>(); //Stockera les occurences des mots
	static String filtreMot[] = { "." };
	//static String listDossier[] = {"words", "topic", "dossierTruc" , "DossierDia"}; //Stock la liste des dossier à traduire

	
	public static void main(String[] args) throws MalformedURLException, URISyntaxException {
		//On crée une instance de SAXBuilder
	      SAXBuilder sxb = new SAXBuilder();

	      initXapi();
	      
	      try
	      {
	         //On crée un nouveau document JDOM avec en argument le fichier XML
	         //Le parsing est terminé 
	         document = sxb.build(new File("sourceXML/words/EN2001a.A.words.xml"));
	      }
	      catch(Exception e){
	    	  System.out.println("Erreur lors du chargement du fichier XML :'(");
	      }

	      //On initialise un nouvel élément racine avec l'élément racine du document.
	      racine = document.getRootElement();

	      //Affiche l'ensemble des données du fichier XML
	      occurence();
	}
	
	static void occurence()
	{
		System.out.println("Compte le nombre d'occurence...");
	   //On crée une List contenant tous les noeuds "nite" de l'Element racine
	   List<Element> listNite = racine.getChildren("w");

	   //On crée un Iterator sur notre liste
	   Iterator i = listNite.iterator();
	   while(i.hasNext())
	   {
		   //On recrée l'Element courant à chaque tour de boucle
		   Element courant = (Element)i.next();
		   
		   if(rechercheTab(courant.getText())){
			   continue;			  
		   }
		   
		   
		   if(occurence.containsKey(courant.getText()))
		   {
			   occurence.replace(courant.getText(), occurence.get(courant.getText()) + 1);
			   System.out.println("Le mot "+courant.getText() + " est présent " + occurence.get(courant.getText()) + " !");
		   }
		   else {
		      //On affiche le nom de l’élément courant
		      System.out.println(courant.getText());
		      occurence.put(courant.getText(), 1);
		   }
	   }
	}
	
	static void initXapi() throws MalformedURLException, URISyntaxException
	{
		 RemoteLRS lrs = new RemoteLRS();

	      lrs.setEndpoint("https://cloud.scorm.com/tc/public/");
	      lrs.setVersion(TCAPIVersion.V100);
	      lrs.setUsername("<Test User>");
	      lrs.setPassword("<Test User's Password>");

	      Agent agent = new Agent();
	      agent.setMbox("mailto:info@tincanapi.com");

	      Verb verb = new Verb("http://adlnet.gov/expapi/verbs/attempted");

	      Activity activity = new Activity("http://rusticisoftware.github.com/TinCanJava");

	      Statement st = new Statement();
	      st.setActor(agent);
	      st.setVerb(verb);
	      st.setObject(activity);

	      StatementLRSResponse lrsRes = lrs.saveStatement(st);
	      if (lrsRes.getSuccess()) {
	          // success, use lrsRes.getContent() to get the statement back
	      }
	      else {
	          // failure, error information is available in lrsRes.getErrMsg()
	      }
	}
	
	static boolean rechercheTab(String recherche) {
		for(int i = 0; i < filtreMot.length; i++){
			if(recherche.equals(filtreMot[i]))
				return true;				
		}
		return false;
	}

}
