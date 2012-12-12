/**
 * 
 */
package com.github.gerbsen.sparql;

import java.io.StringWriter;

import org.apache.log4j.chainsaw.Main;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;
import com.hp.hpl.jena.update.GraphStoreFactory;
import com.hp.hpl.jena.update.UpdateAction;
import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateProcessor;
import com.hp.hpl.jena.update.UpdateRequest;

/**
 * @author Daniel Gerber <dgerber@informatik.uni-leipzig.de>
 *
 */
public class SparqlUtil {
    
    public static void main(String[] args) {

//        http://[2001:638:902:2010:0:168:35:138]/sparql
        
        String server = "http://localhost:1111/sparql";
        
        test("http://rdflivenews.org", "http://localhost:1111/sparql");
        
//        System.out.println("exists(http://rdflivenews.org)" + isGraphExisting("http://rdflivenews.org", server));
//        System.out.println("exists(http://rdflivenews.org/test)" + isGraphExisting("http://rdflivenews.org/test", server));
//        createGraph("http://rdflivenews.org/test", server);
    }
    
    /**
     * Checks with the help of SPARQL ASK if a given graph exists at
     * a given SPARQL endpoint. 
     * 
     * @param graph - the graph uri in question
     * @param endpointUrl - the url of the endpoint
     * @return true if the graph exist, false otherwise
     */
    public static boolean isGraphExisting(String graph, String endpointUrl){
        
        Query query = QueryFactory.create(String.format("ASK { GRAPH <%s> { ?s ?p ?o } }", graph));
        return QueryExecutionFactory.sparqlService(endpointUrl, query).execAsk();
    }
    
    /**
     * 
     * @param graph
     * @param endpointUrl
     */
    public static void createGraph(String graph, String endpointUrl) {
        
        UpdateExecutionFactory.createRemote(
                    UpdateFactory.create(String.format("CREATE GRAPH <%s>", graph)),
                    endpointUrl)
                .execute();
    }        
    
    /**
     * 
     * @param graph
     * @param endpointUrl
     */
    public static void clearGraph(String graph, String endpointUrl) {
        
        UpdateExecutionFactory.createRemote(
                UpdateFactory.create(String.format("CLEAR GRAPH <%s>", graph)),
                endpointUrl)
            .execute();
    }
    
    public static void test(String graph, String endpointUrl) {
        
//        String add = "INSERT DATA { GRAPH <"+graph+"> { <http://exmaple.org/test1> <http://example.org/test2> <http://example.org/test3> . } }";
//        System.out.println(add);
        String add = "insert in graph <http://rdflivenews.org>  {<http://dbpedia.org/resource/z> a <http://dbpedia.org/ontology/B>}";
        
        UpdateExecutionFactory.createRemote(
                UpdateFactory.create(add, graph),
                endpointUrl)
            .execute();
    }
    

    public static String writeModelToSparqlEndpoint(OntModel model, String graph, String endpointUrl) throws Exception {

        // create graph or clear it, if it's already there
        if (!isGraphExisting(graph, endpointUrl)) createGraph(graph, endpointUrl);
        else clearGraph(graph, endpointUrl);

        // write n-triple serialization into StringWriter and surround the model
        // with insert statement
        StringWriter w = new StringWriter();
        w.append("INSERT DATA IN GRAPH '" + graph + "'" + " {");
        model.write(w, "N-TRIPLE");
        w.append(" }");
        w.flush();

        // open connection and run statement
        QueryEngineHTTP qeh = new QueryEngineHTTP(endpointUrl, w.toString());
        ResultSet rs = qeh.execSelect();
        String result = "";
        while (rs.hasNext()) result += rs.next().get("callret-0");
        qeh.close();

        return result;

    } 
}
