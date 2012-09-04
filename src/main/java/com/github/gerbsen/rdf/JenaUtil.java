/**
 * 
 */
package com.github.gerbsen.rdf;

import java.io.IOException;
import java.io.InputStream;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;


/**
 * @author Daniel Gerber <dgerber@informatik.uni-leipzig.de>
 *
 */
public class JenaUtil {

    public static OntModel loadModelFromFile(String filename) {
        
        try {
            
            OntModel ontologyModel = ModelFactory.createOntologyModel();
            InputStream in = FileManager.get().open(filename);
            ontologyModel.read(in, "");
            in.close();
            
            return ontologyModel;
        }
        catch (IOException e) {
            
            throw new RuntimeException("Could not load model from file: " + filename, e);
        }
    }
}
