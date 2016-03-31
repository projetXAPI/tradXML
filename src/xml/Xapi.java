package xml;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.rusticisoftware.tincan.Activity;
import com.rusticisoftware.tincan.ActivityDefinition;
import com.rusticisoftware.tincan.Agent;
import com.rusticisoftware.tincan.Extensions;
import com.rusticisoftware.tincan.RemoteLRS;
import com.rusticisoftware.tincan.Statement;
import com.rusticisoftware.tincan.TCAPIVersion;
import com.rusticisoftware.tincan.Verb;
import com.rusticisoftware.tincan.lrsresponses.StatementLRSResponse;
import com.rusticisoftware.tincan.lrsresponses.StatementsResultLRSResponse;
import com.rusticisoftware.tincan.json.StringOfJSON;

public class Xapi {
	
	public RemoteLRS lrs; 
	
	
	public RemoteLRS initXapi() throws MalformedURLException
	{
		 lrs = new RemoteLRS();

	      lrs.setEndpoint("https://cloud.scorm.com/tc/0O6GMM7KOI/sandbox/");
	      lrs.setVersion(TCAPIVersion.V100);
	      lrs.setUsername("vincentglize@hotmail.fr");
	      lrs.setPassword("iutbay64");
	      
	      return lrs;
	}
	
	/*
	public void envoieOccurence(RemoteLRS lrs, HashMap<String, Integer> occurence) throws URISyntaxException
	{
		for(Map.Entry mapentry : occurence.entrySet()){
			System.out.println("clé " + mapentry.getKey() + " Valeur " + mapentry.getValue());
				//Création statement pour le mot courant (Key = mot Value = le nombre d'occurence)
				Agent agent = new Agent();
		      agent.setMbox("mailto:vincentglize@hotmail.fr");
		      agent.setName("Vincent");

		      Verb verb = new Verb("http://adlnet.gov/expapi/verbs/attempted", "occurence");
		      
		      Activity activity = new Activity("http://rusticisoftware.github.com/TinCanJava", mapentry.getKey().toString(), mapentry.getValue().toString());
		      //activity.setId("testatest");
		      
		      Statement st = new Statement();
		      st.setActor(agent);
		      st.setVerb(verb);
		      st.setObject(activity);

		      StatementLRSResponse lrsRes = lrs.saveStatement(st);
		      if (lrsRes.getSuccess()) {
		          // success, use lrsRes.getContent() to get the statement back
		    	  System.out.println("Mot bien enregistré");
		      }
		      else {
		          // failure, error information is available in lrsRes.getErrMsg()
		    	  System.out.println("Problème lors de l'envoie du statement !");
		      }
			
		}
		
		System.out.println("Envoie des mots fini");
	}
	*/

	/*
	 * Méthode pour créer les statements relatif aux infos de la réunion
	 * paramètre : les infos de la réunion en HASHMAP, le nom de la réunion traité
	 * Retour : Retour la liste des statements créés
	 * 
	 */
	public List<Statement> creationStatementInfoReu(HashMap<String, String> infoReu, String nomReu) throws URISyntaxException, IOException {
		List<Statement> listStatements = new LinkedList();
		for(Map.Entry mapentry : infoReu.entrySet()){
				Agent agent = new Agent();
				agent.setMbox("mailto:vincentglize@hotmail.fr");
		      agent.setName(nomReu);

		      Verb verb = new Verb("http://projet10.fr/verbs/information/"+nomReu, "information");

		      Activity activity = new Activity("http://rusticisoftware.github.com/TinCanJava", mapentry.getKey().toString(), mapentry.getValue().toString());
		      
		     	      
		      Statement st = new Statement();
		      st.setActor(agent);
		      st.setVerb(verb);
		      st.setObject(activity);
		      
		      listStatements.add(st);
		}
		return listStatements;
	}

	/*
	 * Méthode pour créer les statements relatif aux phrases dites d'un membre donné
	 * paramètre : les infos de la réunion en HASHMAP, le nom de la réunion traité, acteur
	 * Retour : Retour la liste des statements créés
	 * 
	 */
	/*
	public List<Statement> creerStatementsPhrasesAncien(HashMap<Integer, String[]> phrases, String nomReu, String acteur) throws URISyntaxException, IOException {
		String debut;
		String phrase;
		String test;
		String[] value;
		List<Statement> listStatements = new LinkedList();
		for(Map.Entry mapentry : phrases.entrySet()){
			value = (String[]) mapentry.getValue();
			debut = value[0];
			phrase = (String) value[1];
			
				Agent agent = new Agent();
				agent.setMbox("mailto:vincentglize@hotmail.fr");
		      agent.setName(acteur);

		      Verb verb = new Verb("http://projet10.fr/verbs/phrase/"+nomReu, "phrase");
		      

		      Activity activity = new Activity("http://rusticisoftware.github.com/TinCanJava", phrase, debut);
		      ActivityDefinition ad = new ActivityDefinition(phrase, debut);

		      //ad.setExtensions(new Extensions(new StringOfJSON("meetingID:es2002")));
		      //ad.setMoreInfo("es2002");
		      
		      
		      //activity.setDefinition(ad);
		      
		      
		      
		      
		      //activity.setId("test");
		      
		      Statement st = new Statement();
		      st.setActor(agent);
		      st.setVerb(verb);
		      st.setObject(activity);
		      

		      listStatements.add(st);
				
		}
		
		return listStatements;
		
	}
	*/
	
	/*
	 * Méthode pour créer les statements relatif aux phrases dites d'un membre donné
	 * paramètre : les infos de la réunion en HASHMAP, le nom de la réunion traité, acteur
	 * Retour : Retour la liste des statements créés
	 * 
	 */	
	public List<Statement> creerStatementsPhrases(Map<Float, String[]> phrases, String nomReu) throws URISyntaxException {
		List<Statement> listStatements = new LinkedList();
		String debut;
		String phrase;
		String acteur;
		String[] value;
		for(Map.Entry mapentry : phrases.entrySet()){
			value = (String[]) mapentry.getValue();
			acteur = value[0];
			phrase = (String) value[1];
			
			debut = mapentry.getKey().toString();
			
				Agent agent = new Agent();
				agent.setMbox("mailto:vincentglize@hotmail.fr");
		      agent.setName(acteur);

		      Verb verb = new Verb("http://projet10.fr/verbs/phrase/"+nomReu, "phrase");
		      

		      Activity activity = new Activity("http://rusticisoftware.github.com/TinCanJava", phrase, debut);
		      ActivityDefinition ad = new ActivityDefinition(phrase, debut);

		      //ad.setExtensions(new Extensions(new StringOfJSON("meetingID:es2002")));
		      //ad.setMoreInfo("es2002");
		      
		      
		      //activity.setDefinition(ad);
		      
		      
		      
		      
		      //activity.setId("test");
		      
		      Statement st = new Statement();
		      st.setActor(agent);
		      st.setVerb(verb);
		      st.setObject(activity);
		      

		      listStatements.add(st);
				
		}
		
		return listStatements;
	}

	
	/*
	 * Méthode pour créer les statements relatif aux temps de chaque rôle d'un membre
	 * paramètre : les infos de la réunion en HASHMAP, le nom de la réunion traité, le membre
	 * Retour : Retour la liste des statements créés
	 * 
	 */
	public List<Statement> creerStatementsRoleTemps(HashMap<String, Float> object, String nomReu,
			String acteur) throws URISyntaxException {
		
		List<Statement> listStatements = new LinkedList();
		
		for(Map.Entry mapentry : object.entrySet()){
			
				Agent agent = new Agent();
				agent.setMbox("mailto:vincentglize@hotmail.fr");
		      agent.setName(acteur);

		      Verb verb = new Verb("http://projet10.fr/verbs/temps_role/"+nomReu, "temps_role");

		      Activity activity = new Activity("http://rusticisoftware.github.com/TinCanJava", mapentry.getKey().toString(), mapentry.getValue().toString());
		      ActivityDefinition ad = new ActivityDefinition(mapentry.getKey().toString(), mapentry.getValue().toString());

		      //ad.setExtensions(new Extensions(new StringOfJSON("{\"meetingID\":\"es2002\"}")));
		      //activity.setDefinition(ad);
		      
		      //activity.setId("test");
		      
		      Statement st = new Statement();
		      st.setActor(agent);
		      st.setVerb(verb);
		      st.setObject(activity);
		      
		      listStatements.add(st);
			
		}
		
		return listStatements;
	}
	
	
	/*
	 * Méthode pour créer les statements relatif aux role de chaque membre
	 * paramètre : les infos de la réunion en HASHMAP, le nom de la réunion traité, le membre
	 * Retour : Retour la liste des statements créés
	 * 
	 */
	public List<Statement>  creerStatementsRole(HashMap<String, String> roles, String nomReu, String acteur) throws URISyntaxException {
		
		List<Statement> listStatements = new LinkedList<Statement>();
		
		for(Map.Entry mapentry : roles.entrySet()){
				Agent agent = new Agent();
				agent.setMbox("mailto:vincentglize@hotmail.fr");
		      agent.setName(acteur);

		      Verb verb = new Verb("http://projet10.fr/verbs/role/"+nomReu, "role");

		      Activity activity = new Activity("http://rusticisoftware.github.com/TinCanJava", mapentry.getKey().toString(), mapentry.getValue().toString());
		      ActivityDefinition ad = new ActivityDefinition(mapentry.getKey().toString(), mapentry.getValue().toString());

		      //ad.setExtensions(new Extensions(new StringOfJSON("{\"meetingID\":\"es2002\"}")));
		      //activity.setDefinition(ad);
		      
		      //activity.setId("test");
		      
		      Statement st = new Statement();
		      st.setActor(agent);
		      st.setVerb(verb);
		      st.setObject(activity);
		      
		      listStatements.add(st);
			
		}
		
		return listStatements;
	}
	
	
	
	/*
	 * 
	 * ENVOI STATEMENT SUR LE SERVEUR LRS
	 * 
	 */
	public void envoieStatements(RemoteLRS lrs2, List<Statement> listStatements) {
		StatementsResultLRSResponse lrsRes = lrs.saveStatements(listStatements);
	    if (lrsRes.getSuccess()) {
	        // success, use lrsRes.getContent() to get the statement back
	    	//System.out.println("Mot bien enregistré");
	    }
	    else {
	        // failure, error information is available in lrsRes.getErrMsg()
	    	System.out.println("Problème lors de l'envoie du statement !: "+lrsRes.getErrMsg());
	    	new Exception();
	    }		
	}

}
