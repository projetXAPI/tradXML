package xml;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.rusticisoftware.tincan.Activity;
import com.rusticisoftware.tincan.ActivityDefinition;
import com.rusticisoftware.tincan.Agent;
import com.rusticisoftware.tincan.RemoteLRS;
import com.rusticisoftware.tincan.Statement;
import com.rusticisoftware.tincan.TCAPIVersion;
import com.rusticisoftware.tincan.Verb;
import com.rusticisoftware.tincan.lrsresponses.StatementLRSResponse;

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


	public void envoieInfoReu(RemoteLRS lrs2, HashMap<String, String> infoReu, String nomReu) throws URISyntaxException {
		for(Map.Entry mapentry : infoReu.entrySet()){
			System.out.println("clé " + mapentry.getKey() + " Valeur " + mapentry.getValue());
				Agent agent = new Agent();
				agent.setMbox("mailto:vincentglize@hotmail.fr");
		      agent.setName(nomReu);

		      Verb verb = new Verb("http://adlnet.gov/expapi/verbs/attempted", "information");

		      Activity activity = new Activity("http://rusticisoftware.github.com/TinCanJava", mapentry.getKey().toString(), mapentry.getValue().toString());
		      
		      //activity.setId("test");
		      
		      Statement st = new Statement();
		      st.setActor(agent);
		      st.setVerb(verb);
		      st.setObject(activity);

		      StatementLRSResponse lrsRes = lrs.saveStatement(st);
		      if (lrsRes.getSuccess()) {
		          // success, use lrsRes.getContent() to get the statement back
		    	  //System.out.println("Mot bien enregistré");
		      }
		      else {
		          // failure, error information is available in lrsRes.getErrMsg()
		    	  System.out.println("Problème lors de l'envoie du statement !");
		      }
			
		}
	}

}
