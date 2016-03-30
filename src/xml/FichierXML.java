package xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.w3c.dom.NodeList;

import com.rusticisoftware.tincan.RemoteLRS;

public class FichierXML {
	private org.jdom2.Document document;
	private Element racine;
	private HashMap<String, Integer> occurence = new HashMap<String, Integer>(); //Stockera les occurences des mots
	private String filtreMot[] = { "." };
	private SAXBuilder sxb = new SAXBuilder();
	
	public FichierXML(String nomFichier) throws Exception {
		ouvrirFichier(nomFichier);
	}
	
	public void ouvrirFichier(String nomFichier) throws Exception {	
	      try
	      {
	         //On crée un nouveau document JDOM avec en argument le fichier XML
	         //Le parsing est terminé 
	         this.document = sxb.build(new File(nomFichier));
	      }
	      catch(Exception e){
	    	  throw new Exception();
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
			   //occurence.replace(courant.getText(), occurence.get(courant.getText()) + 1);
		   }
		   else {
		      //On affiche le nom de l’élément courant
		      System.out.println(courant.getText());
		      occurence.put(courant.getText(), 1);
		   }
	   }
	   return occurence;
	}
	
	public HashMap<String, String> infoReu(String nomReu) {
		HashMap<String, String> info = new HashMap<String, String>(); 
		List<Element> listNite = racine.getChildren("meeting");

		//On crée un Iterator sur notre liste
		Iterator i = listNite.iterator();
		while(i.hasNext())
		{
			//On récupère les infos que pour note réunion
			Element courant = (Element)i.next();
			if(nomReu.equals(courant.getAttributeValue("observation"))) {
				info.put("name", courant.getAttributeValue("name"));
				info.put("date", courant.getAttributeValue("dateOnly"));
				info.put("titre", courant.getAttributeValue("topic"));
				info.put("duree", courant.getAttributeValue("duration"));
				/*
				List<Element> listParticipant = courant.getChildren("speaker");
				Iterator i2 = listParticipant.iterator();
				while(i.hasNext())
				{
					//Prévu si on veut récupérer les infos des membres				
				}
				*/
				return info;
				
			}
		}
		return info;
	}
	
	public boolean rechercheTab(String recherche) {
		for(int i = 0; i < filtreMot.length; i++){
			if(recherche.equals(filtreMot[i]))
				return true;				
		}
		return false;
	}

	public HashMap<Integer, String> creationPhrase() {
		List<Element> listNite = racine.getChildren("w");
		int numPhrase = 0;
		/*
		String[] phrases;
		phrases = new String[200];
		*/
		 HashMap<Integer, String> phrases = new HashMap<Integer, String>(); 

		   //On crée un Iterator sur notre liste
		Iterator i = listNite.iterator();
		while(i.hasNext())
		{
			//On recrée l'Element courant à chaque tour de boucle
			Element courant = (Element)i.next();
			
			if(phrases.get(numPhrase) == null)
				phrases.put(numPhrase, courant.getText());
			else if(",".equals(courant.getText()) || ".".equals(courant.getText()))
				phrases.put(numPhrase, phrases.get(numPhrase) + courant.getText());
			else
				phrases.put(numPhrase, phrases.get(numPhrase) + " " + courant.getText());
			
			//Si fin de phrase
			if(".".equals(courant.getText()) || "!".equals(courant.getText()) || "?".equals(courant.getText())) {
				//System.out.println("PONC : " + courant.getText());
				numPhrase++;
			}
			else {
				System.out.println(courant.getText() + " n " + numPhrase);
			}			   
		}
		
		return phrases;
	}
}
