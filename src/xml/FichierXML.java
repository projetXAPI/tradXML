package xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
	
	/*
	 * Fonction plus utilisé mais laissé si jamais une utilité est trouvé. 
	 * Elle permet de renvoyé les occurences des mots dans 
	 */
	
	/*
	
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
	
	*/
	
	/*
	 * Méthode pour créer les informations relatif à la réunion
	 * paramètre : Nom de la réunion
	 * Retour : HashMap composé d'une clé et de sa valeur (chaine de caractère)
	 * 
	 */
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
				info.put("titre", courant.getAttributeValue("name"));
				info.put("date", courant.getAttributeValue("dateOnly"));
				info.put("description", courant.getAttributeValue("description"));
				info.put("duree", courant.getAttributeValue("duration"));
				info.put("nom", nomReu);
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
	
	/*
	 * Méthode plus utilisé, elle servait à chercher une valeur dans un tableau
	 * 
	 */
	/*
	public boolean rechercheTab(String recherche) {
		for(int i = 0; i < filtreMot.length; i++){
			if(recherche.equals(filtreMot[i]))
				return true;				
		}
		return false;
	}
	*/
	
	
	/*
	 * Méthode pour créer les phrases
	 * Retour : tableau avec les phrases
	 * 
	 */
	public HashMap<Float, String[]> creationPhrase(String acteur) {
		List<Element> listNite = racine.getChildren("w");
		int numPhrase = 0;
		float debutPhrase = 0;
		//String phrase;
		String[] phrase;
		 HashMap<Float, String[]> phrases = new HashMap<Float, String[]>(); 

		   //On crée un Iterator sur notre liste
		Iterator i = listNite.iterator();
		while(i.hasNext())
		{
			//On recrée l'Element courant à chaque tour de boucle
			Element courant = (Element)i.next();
			
			phrase = phrases.get(debutPhrase);
			
			//phrases.put(debutPhrase, new String[]{ courant.getAttributeValue("starttime"), courant.getText() });
			
			if(phrases.get(debutPhrase) == null)
				phrases.put(debutPhrase, new String[]{ acteur, courant.getText() });
			else if(",".equals(courant.getText()) || ".".equals(courant.getText()))
				phrases.put(debutPhrase, new String[]{ acteur, phrase[1] + courant.getText() });
			else
				phrases.put(debutPhrase, new String[]{ acteur, phrase[1] + " " + courant.getText() });
			
			//Si fin de phrase
			if(".".equals(courant.getText()) || "!".equals(courant.getText()) || "?".equals(courant.getText())) {
				//System.out.println("PONC : " + courant.getText());
				debutPhrase = Float.parseFloat(courant.getAttributeValue("starttime"));
			}		   
		}
		
		return phrases;
	}
	
	/*
	 * Méthode pour créer le temps que chaque membre a était dans chaque rôle (neutre, protagoniste, etc...)
	 * Retour : HashMap composé d'une clé et de sa valeur (chaine de caractère)
	 * 
	 */
	public HashMap<String, Float> creationRoleTemps() {
		List<Element> listNite = racine.getChildren("role");
		//String phrase;
		HashMap<String, Float> roles = new HashMap<String, Float>(); 
		float debut;
		float fin;
		   //On crée un Iterator sur notre liste
		Iterator i = listNite.iterator();
		while(i.hasNext())
		{
			//On recrée l'Element courant à chaque tour de boucle
			Element courant = (Element)i.next();
			
			debut = Float.parseFloat(courant.getAttributeValue("starttime").toString());
			fin = Float.parseFloat((courant.getAttributeValue("endtime").toString()));
			if(roles.containsKey(courant.getAttributeValue("type")))
			{
				roles.put(courant.getAttributeValue("type"), (roles.get(courant.getAttributeValue("type")) + (fin - debut)));
			}
			else {
				roles.put(courant.getAttributeValue("type"), (float) 0);
			}		
		}
		
		return roles;
	}

	/*
	 * Méthode pour créer les rôle avec leurs temps associé
	 * Retour : HashMap composé d'une clé et de sa valeur (Valeur composé du début de chaque fois ou il est dans ce role) (chaine de caractère)
	 * 
	 */
	public HashMap<String, String> creationRole() {
		List<Element> listNite = racine.getChildren("role");
		//String phrase;
		//HashMap<String, Float> roles = new HashMap<String, Float>(); 
		HashMap<String, String> roles = new HashMap<String, String>();
		String debut;
		String type;
		   //On crée un Iterator sur notre liste
		Iterator i = listNite.iterator();
		while(i.hasNext())
		{
			//On recrée l'Element courant à chaque tour de boucle
			Element courant = (Element)i.next();
			
			type = courant.getAttributeValue("type").toString();
			debut = courant.getAttributeValue("starttime").toString();
			
			
			if(roles.containsKey(courant.getAttributeValue("type"))) {
				roles.put(type, roles.get(courant.getAttributeValue("type")) + " | " + debut);
			} else {
				roles.put(type, debut);
			}
		}
		
		return roles;
	}

}
