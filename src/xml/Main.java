package xml;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

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
	      afficheXML();
	}
	
	static void afficheXML()
	{
		System.out.println("Affichage");
	   //On crée une List contenant tous les noeuds "nite" de l'Element racine
	   List listNite = racine.getChildren("w");

	   //On crée un Iterator sur notre liste
	   Iterator i = listNite.iterator();
	   while(i.hasNext())
	   {
	      //On recrée l'Element courant à chaque tour de boucle
	      Element courant = (Element)i.next();
	      //On affiche le nom de l’élément courant
	      System.out.println(courant.getText());
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

}
