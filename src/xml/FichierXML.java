package xml;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.rusticisoftware.tincan.RemoteLRS;

public class FichierXML {
	private org.jdom2.Document document;
	private Element racine;
	private HashMap<String, Integer> occurence = new HashMap<String, Integer>(); //Stockera les occurences des mots
	private String filtreMot[] = { "." };
	private SAXBuilder sxb = new SAXBuilder();
	
	public FichierXML(String nomFichier) {
		ouvrirFichier(nomFichier);
	}
	
	public void ouvrirFichier(String nomFichier) {	      
	      try
	      {
	         //On crée un nouveau document JDOM avec en argument le fichier XML
	         //Le parsing est terminé 
	         this.document = sxb.build(new File(nomFichier));
	      }
	      catch(Exception e){
	    	  System.out.println("Erreur lors du chargement du fichier XML :'(");
	      }
	    //On initialise un nouvel élément racine avec l'élément racine du document.
	      racine = document.getRootElement();
	}
	
	
	
	public HashMap<String, Integer> occurence()
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
		   }
		   else {
		      //On affiche le nom de l’élément courant
		      System.out.println(courant.getText());
		      occurence.put(courant.getText(), 1);
		   }
	   }
	   return occurence;
	}
	
	public boolean rechercheTab(String recherche) {
		for(int i = 0; i < filtreMot.length; i++){
			if(recherche.equals(filtreMot[i]))
				return true;				
		}
		return false;
	}
}
