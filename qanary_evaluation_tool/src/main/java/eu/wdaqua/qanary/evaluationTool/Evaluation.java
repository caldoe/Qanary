package eu.wdaqua.qanary.evaluationTool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import org.apache.commons.io.FileUtils;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * represents a wrapper of the Stanford NER tool used here as a spotter
 * 
 * @author Dennis Diefenbach & Kuldeep Singh
 *
 */

public class Evaluation {
	private static final Logger logger = LoggerFactory.getLogger(Evaluation.class);
	
	public void qald6_test() {
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
		String uriServer="http://localhost:8080/startquestionansweringwithtextquestion";
		String components="alchemy";
		//String components="StanfordNER ,agdistis";
		
		long startTime = System.currentTimeMillis();
		
		try {
			Double globalPrecision=0.0;
			Double globalRecall=0.0;
			int count=0;
			String path = Evaluation.class.getResource("/qald-6-train-multilingual.json").getPath();
			File file = new File(path);
			String content=FileUtils.readFileToString(file);
			JSONObject json = new JSONObject(content);
			JSONArray tests = json.getJSONArray("questions");
			for (int i = 0; i<tests.length(); i++){
				JSONObject questionObject = tests.getJSONObject(i);
				JSONArray questions = questionObject.getJSONArray("question");
				String question=questions.getJSONObject(0).get("string").toString();
	
				logger.info("Question "+question);
				
				//Send the question
				RestTemplate restTemplate = new RestTemplate();
				UriComponentsBuilder service = UriComponentsBuilder.fromHttpUrl(uriServer);
				
				MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
				bodyMap.add("question", question);
				bodyMap.add("componentlist", components);
				bodyMap.add("submit", "start QA process");
				String response = restTemplate.postForObject(service.build().encode().toUri(), bodyMap, String.class);
				logger.info("Response pipline {}"+response);
				
				//Retrieve the computed uris
				JSONObject responseJson = new JSONObject(response);
				String endpoint = responseJson.getString("endpoint");
				String namedGraph = responseJson.getString("graph");
				String sparql = "prefix qa: <http://www.wdaqua.eu/qa#> "
						+ "prefix oa: <http://www.w3.org/ns/openannotation/core/> "
						+ "prefix xsd: <http://www.w3.org/2001/XMLSchema#> " 
						+ "SELECT ?uri { " + "GRAPH <" + namedGraph + "> { "
						+ "  ?a a qa:AnnotationOfNamedEntity . "
						+ "  ?a oa:hasBody ?uri } }";
				ResultSet r=selectTripleStore(sparql,endpoint);
				List<String> systemAnswers = new ArrayList<String>();
				while (r.hasNext()){
					QuerySolution s=r.next();
					logger.info("System answers {} ",s.getResource("uri").toString());
					systemAnswers.add(s.getResource("uri").toString());
				}
				
				
				//Retrieve the expected resources from the SPARQL query
				List<String> expectedAnswers = new ArrayList<String>();
				if (questionObject.getJSONObject("query").has("sparql")){
					String query=questionObject.getJSONObject("query").get("sparql").toString();
					System.out.println(query);
					Pattern pattern=Pattern.compile("<.*?>");
				    Matcher matcher = pattern.matcher(query);
				    while (matcher.find()){
				    	//System.out.println(matcher.group().toString());
				    	if (expectedAnswers.contains(matcher.group().toString())==false){
				    		expectedAnswers.add(matcher.group().toString().replace("<", "").replace(">", ""));
				    		logger.info("Expected Answers {} ", matcher.group().toString().replace("<", "").replace(">", ""));
				    	}
				    }
				}
				
				//Compute precision and recall
				int correctRetrived = 0;
				for (String s:systemAnswers){
					if (expectedAnswers.contains(s)){
						correctRetrived++;
					}
				}
				System.out.println("Correct retrived"+correctRetrived);
				
				Double precision = (double)correctRetrived/systemAnswers.size();
				Double recall = (double)correctRetrived/expectedAnswers.size();
				if (precision.isNaN()){
					precision=0.0;
				}
				if (recall.isNaN()){
					recall=0.0;
				}
				logger.info("PRECISION {} ",precision);
				logger.info("RECALL {} ",recall);
				globalPrecision+=precision;
				globalRecall+=recall;
				count++;
			}
			System.out.println("GlobalPrecision="+(double)globalPrecision/count);
			System.out.println("GlobalRecall="+(double)globalRecall/count);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadTripleStore(String sparqlQuery, String endpoint) {
		UpdateRequest request = UpdateFactory.create(sparqlQuery);
		UpdateProcessor proc = UpdateExecutionFactory.createRemote(request, endpoint);
		proc.execute();
	}

	public ResultSet selectTripleStore(String sparqlQuery, String endpoint) {
		Query query = QueryFactory.create(sparqlQuery);
		QueryExecution qExe = QueryExecutionFactory.sparqlService(endpoint, query);
		return qExe.execSelect();
	}

	class Selection {
		public int begin;
		public int end;
	}

}