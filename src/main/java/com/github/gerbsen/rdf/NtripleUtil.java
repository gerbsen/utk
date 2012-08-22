package de.danielgerber.rdf;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.parser.NxParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.danielgerber.file.BufferedFileReader;
import de.danielgerber.string.StringUtil;


public class NtripleUtil {

    private static final Logger logger = LoggerFactory.getLogger(BufferedFileReader.class);
    
	/**
	 * 
	 * @param filename
	 * @return
	 */
	public static Map<String,String> parseNTripleFile(String filename, String removePrefix) {

		Map<String,String> mapping =  new HashMap<String,String>();
		String line = "";
		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(filename))));
			
			while ((line = br.readLine()) != null) {

				// get the subject and cut it out of the 
				String subject = line.substring(0, line.indexOf(" "));
				subject = (removePrefix == null || removePrefix.equals("")) ? subject : subject.replace(removePrefix, "");
				line = line.substring(line.indexOf(" ") + 1);
				
				// we dont need the predicate but we will need to cut it out anyway
				String predicate = line.substring(0, line.indexOf(" "));
				line = line.substring(line.indexOf(" ") + 1);
				
				String object = line;
				if (object.startsWith("\"") ) object = StringUtils.substringBetween(object, "\"", "\"");
				if (object.startsWith("<") ) object = StringUtils.substringBetween(object, "<", ">");
				object = StringEscapeUtils.unescapeJava(object);
				object = (removePrefix == null || removePrefix.equals("")) ? object : object.replace(removePrefix, "");
				
				mapping.put(StringUtil.removeCharactersFromString(subject, "<>"), 
							StringUtil.removeCharactersFromString(object, "<>"));
			}
			br.close();
		}
		catch (Exception e) {

		    e.printStackTrace();
            String error = "Could not read line: " + line + " \nfrom file " + filename;
            logger.error(error, e);
            throw new RuntimeException(error, e);
		}
		return mapping;
	}
	
	public static List<String> getSubjectsFromNTriple(String filename, String replacePrefix) {
	    
       List<String> results = new ArrayList<String>();
        
        NxParser nxp = NtripleUtil.openNxParser(filename);
        while (nxp.hasNext()) {

            Node[] ns = nxp.next();
            results.add(replacePrefix == null || replacePrefix.equals("") ? ns[0].toString() : ns[0].toString().replace(replacePrefix, ""));
        }
        return results;
	}
	
	public static List<String> getPredicatesFromNTriple(String filename, String replacePrefix) {
        
        List<String> results = new ArrayList<String>();
        
        NxParser nxp = NtripleUtil.openNxParser(filename);
        while (nxp.hasNext()) {

            Node[] ns = nxp.next();
            results.add(replacePrefix == null || replacePrefix.equals("") ? ns[1].toString() : ns[1].toString().replace(replacePrefix, ""));
        }
        return results;
    }
	
	public static List<String> getObjectsFromNTriple(String filename, String replacePrefix) {
        
	    List<String> results = new ArrayList<String>();
        
        NxParser nxp = NtripleUtil.openNxParser(filename);
        while (nxp.hasNext()) {

            Node[] ns = nxp.next();
            results.add(replacePrefix == null || replacePrefix.equals("") ? ns[2].toString() : ns[2].toString().replace(replacePrefix, ""));
        }
        return results;
    }
	
	public static List<String[]> getSubjectAndObjectsFromNTriple(String filename, String replacePrefix) {
        
        List<String[]> results = new ArrayList<String[]>();
        
        NxParser nxp = NtripleUtil.openNxParser(filename);
        while (nxp.hasNext()) {

            Node[] ns = nxp.next();
            results.add(new String[] { 
                    replacePrefix == null || replacePrefix.equals("") ? ns[0].toString() : ns[0].toString().replace(replacePrefix, ""), 
                    replacePrefix == null || replacePrefix.equals("") ? ns[2].toString() : ns[2].toString().replace(replacePrefix, ""), });
        }
        return results;
    }
	
	public static Map<String,String> getSubjectAndObjectsMappingFromNTriple(String filename, String replacePrefix) {
        
	    Map<String,String> results = new HashMap<String,String>();
        
        NxParser nxp = NtripleUtil.openNxParser(filename);
        while (nxp.hasNext()) {

            Node[] ns = nxp.next();
            results.put( 
                    replacePrefix == null || replacePrefix.equals("") ? ns[0].toString() : ns[0].toString().replace(replacePrefix, ""), 
                    replacePrefix == null || replacePrefix.equals("") ? ns[2].toString() : ns[2].toString().replace(replacePrefix, ""));
        }
        return results;
    }
	
	public static Map<String,String> getObjectsAndSubjectsMappingFromNTriple(String filename, String replacePrefix) {
        
        Map<String,String> results = new HashMap<String,String>();
        
        NxParser nxp = NtripleUtil.openNxParser(filename);
        while (nxp.hasNext()) {

            Node[] ns = nxp.next();
            results.put( 
                    replacePrefix == null || replacePrefix.equals("") ? ns[2].toString() : ns[2].toString().replace(replacePrefix, ""), 
                    replacePrefix == null || replacePrefix.equals("") ? ns[0].toString() : ns[0].toString().replace(replacePrefix, ""));
        }
        return results;
    }
	
	public static List<String[]> parseNTriple(String filename, String replacePrefix) {
	    
	    List<String[]> results = new ArrayList<String[]>();
	    
        NxParser nxp = NtripleUtil.openNxParser(filename);
        while (nxp.hasNext()) {

            Node[] ns = nxp.next();
            results.add(new String[] { 
                    replacePrefix == null || replacePrefix.equals("") ? ns[0].toString() : ns[0].toString().replace(replacePrefix, ""), 
                    replacePrefix == null || replacePrefix.equals("") ? ns[1].toString() : ns[1].toString().replace(replacePrefix, ""),
                    replacePrefix == null || replacePrefix.equals("") ? ns[2].toString() : ns[2].toString().replace(replacePrefix, ""), });
        }
        return results;
	}
	
	public static NxParser openNxParser(String filename) {
	    
	    NxParser nxp;
	    try {
	        
            nxp = new NxParser(new FileInputStream(filename),false);
        }
	    catch (FileNotFoundException e) {
            
            e.printStackTrace();
            String error = "Could not parse file " + filename;
            logger.error(error, e);
            throw new RuntimeException(error, e);
        }
        catch (IOException e) {
            
            e.printStackTrace();
            String error = "Could not parse file " + filename;
            logger.error(error, e);
            throw new RuntimeException(error, e);
        }
	    return nxp;
	}
	
	public static void main(String[] args) {

		NxParser nxp = NtripleUtil.openNxParser("/Users/gerb/Development/workspaces/experimental/en_wiki_exp/dbpedia/labels_en.nt");
        while (nxp.hasNext()) {

            Node[] ns = nxp.next();
            
            System.out.println(ns[0].toString());
            System.out.println(ns[0].toN3());
            
            System.out.println(ns[1].toString());
            System.out.println(ns[1].toN3());
            
            System.out.println(ns[2].toString());
            System.out.println(ns[2].toN3());
            
            break;
        }
	}
}
