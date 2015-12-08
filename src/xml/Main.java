package xml;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Main {
	static org.jdom2.Document document;
	static Element racine;
	//static String listDossier[] = {"words", "topic", "dossierTruc" , "DossierDia"}; //Stock la liste des dossier � traduire

	
	public static void main(String[] args) {
		//On cr�e une instance de SAXBuilder
	      SAXBuilder sxb = new SAXBuilder();
	      try
	      {
	         //On cr�e un nouveau document JDOM avec en argument le fichier XML
	         //Le parsing est termin� 
	         document = sxb.build(new File("sourceXML/words/EN2001a.A.words.xml"));
	      }
	      catch(Exception e){
	    	  System.out.println("Erreur lors du chargement du fichier XML :'(");
	      }

	      //On initialise un nouvel �l�ment racine avec l'�l�ment racine du document.
	      racine = document.getRootElement();

	      //Affiche l'ensemble des donn�es du fichier XML
	      afficheXML();
	}
	
	static void afficheXML()
	{
		System.out.println("Affichage");
	   //On cr�e une List contenant tous les noeuds "nite" de l'Element racine
	   List listNite = racine.getChildren("w");

	   //On cr�e un Iterator sur notre liste
	   Iterator i = listNite.iterator();
	   while(i.hasNext())
	   {
	      //On recr�e l'Element courant � chaque tour de boucle
	      Element courant = (Element)i.next();
	      //On affiche le nom de l��l�ment courant
	      System.out.println(courant.getText());
	   }
	}

}
