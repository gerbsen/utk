/**
 * 
 */
package de.danielgerber.evaluation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import de.danielgerber.file.BufferedFileReader;
import de.danielgerber.file.FileUtil;


/**
 *
 *@author Daniel Gerber <dgerber@informatik.uni-leipzig.de>
 */
public class KappaScorer {

	/**
	 * This method generates the inter-annotator agreement with the help of
	 * the Cohen's Kappa-Score. It used BigDecimals and is precise up to three
	 * decimals.
	 * @see http://en.wikipedia.org/wiki/Cohen's_kappa
	 * 
	 * @param oneYesTwoYes number of cases both annotators judged a case to be true
	 * @param oneYesTwoNo number of cases where annotator 1 judged a case to be true and annotator 2 judged a case to be false
	 * @param oneNoTwoYes number of cases where annotator 1 judged a case to be false and annotator 2 judged a case to be true
	 * @param oneNoTwoNo number of cases both annotators judged a case to be false
	 * @param cases the number of cases both annotators had to decide, this has to be equal for both annotators
	 * @return the cohens kappe score
	 */
	public static double getCohenKappaScore(int oneYesTwoYes, int oneYesTwoNo, int oneNoTwoYes, int oneNoTwoNo, int cases) {
		
		// Pr(a)
		BigDecimal agreement		= new BigDecimal((double) (oneYesTwoYes + oneNoTwoNo) / cases);

		// how often says one yes or no
		BigDecimal yesRateOne		= new BigDecimal((double)(oneYesTwoYes + oneYesTwoNo) / cases);
		BigDecimal noRateOne		= new BigDecimal((double)(oneNoTwoYes + oneNoTwoNo) / cases);

		// how often says two yes or no		
		BigDecimal yesRateTwo		= new BigDecimal((double)(oneNoTwoYes + oneYesTwoYes) / cases);
		BigDecimal noRateTwo		= new BigDecimal((double)(oneNoTwoNo + oneYesTwoNo) / cases);

		// random agreement on yes or no
		BigDecimal randomYesBoth	= yesRateOne.multiply(yesRateTwo);
		BigDecimal randomNoBoth		= noRateOne.multiply(noRateTwo);

		// random agreement both Pr(e)
		BigDecimal randomAgreement	= randomYesBoth.add(randomNoBoth);
		
		return agreement.subtract(randomAgreement).divide(new BigDecimal(1).subtract(randomAgreement), 3, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 
	 * @param annotatorOne
	 * @param annotatorTwo
	 * @return
	 */
	public static double calculateCohensKappa(List<Integer> annotatorOne, List<Integer> annotatorTwo) {
	    
	    // this needs to be true otherwise we can not calculate a kappa score
	    assert(annotatorOne.size() ==  annotatorTwo.size());
	    
	    int oneYesTwoYes = 0, oneYesTwoNo = 0, oneNoTwoYes = 0, oneNoTwoNo = 0;
	    
	    for ( int i = 0 ; i < annotatorOne.size() ; i++ ) {
	        
	        if ( annotatorOne.get(i).equals(annotatorTwo.get(i)) && annotatorTwo.get(i).equals(1) ) oneYesTwoYes++;
	        else if ( annotatorOne.get(i).equals(annotatorTwo.get(i)) && annotatorTwo.get(i).equals(0) ) oneNoTwoNo++;
	        else if ( annotatorOne.get(i).equals(1) && annotatorTwo.get(i).equals(0) ) oneYesTwoNo++;
	        else if ( annotatorOne.get(i).equals(0) && annotatorTwo.get(i).equals(1) ) oneNoTwoYes++;
	        else {
	            
	            System.err.println("There is something wrong with line: " + i + " (" + annotatorOne.get(i) + "," + annotatorTwo.get(i) + ")");
	        }
	    }
	    
	    System.out.printf("%-1s\t%-1s\t%-1s\t%-1s\t%-1s%n", "", "", "  B", "  B", "");
	    System.out.printf("%-1s\t%-1s\t%-1s\t%-1s\t%-1s%n", "", "", "Yes", " No", "  Σ");
	    System.out.printf("%-1s\t%-1s\t%3d\t%3d\t%3d%n", "A", "Yes", oneYesTwoYes, oneYesTwoNo, oneYesTwoYes + oneYesTwoNo);
	    System.out.printf("%-1s\t%-1s\t%3d\t%3d\t%3d%n", "A", " No", oneNoTwoYes, oneNoTwoNo, oneNoTwoYes + oneNoTwoNo);
	    System.out.printf("%-1s\t%-1s\t%3d\t%3d\t%3d%n", "", "  Σ", oneYesTwoYes + oneNoTwoYes, oneYesTwoNo + oneNoTwoNo, oneYesTwoYes + oneNoTwoYes + oneYesTwoNo + oneNoTwoNo);
	    
	    return getCohenKappaScore(oneYesTwoYes, oneYesTwoNo, oneNoTwoYes, oneNoTwoNo, annotatorOne.size());
	}
	
	public static void main(String[] args) {

        BufferedFileReader reader = FileUtil.openReader("/Users/gerb/group2-jens-daniel-kappa.txt");
        
        List<Integer> annotatorOne = new ArrayList<Integer>();
        List<Integer> annotatorTwo = new ArrayList<Integer>();
        
        String line = "";
        while ( (line = reader.readLine()) != null ) {
            
            String[] parts = line.split(",");
            
            annotatorOne.add(Integer.valueOf(parts[0]));
            annotatorTwo.add(Integer.valueOf(parts[1]));
        }
        System.out.println("JL & DG");
        System.out.println("\nCohens kappa: " + calculateCohensKappa(annotatorOne, annotatorTwo));
        
        reader = FileUtil.openReader("/Users/gerb/group1-axel-mohamed-kappa.txt");
        
        annotatorOne = new ArrayList<Integer>();
        annotatorTwo = new ArrayList<Integer>();
        
        while ( (line = reader.readLine()) != null ) {
            
            String[] parts = line.split(",");
            
            annotatorOne.add(Integer.valueOf(parts[0]));
            annotatorTwo.add(Integer.valueOf(parts[1]));
        }
        System.out.println("MM & AN");
        System.out.println("\nCohens kappa: " + calculateCohensKappa(annotatorOne, annotatorTwo));
        reader.close();
    }
}
