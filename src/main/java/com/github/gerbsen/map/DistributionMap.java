/**
 * 
 */
package com.github.gerbsen.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Ordering;


/**
 * @author Daniel Gerber <dgerber@informatik.uni-leipzig.de>
 *
 */
public class DistributionMap<T extends Comparable<T>> {

    private ValueComparableMap<T,Integer> distribution;
    
    /**
     * 
     */
    public DistributionMap() {
        
        this.distribution = new ValueComparableMap<T, Integer>(Ordering.natural());
    }
    
    /**
     * Adds an element to the map with the inital value of 1. If the element
     * is already contained in the distribution it's number of occurrence is
     * increased by 1.
     * 
     * @param element
     */
    public void addElement(T element) {
        
        if ( this.distribution.containsKey(element) )
            this.distribution.put(element, this.distribution.get(element) + 1);
        else
            this.distribution.put(element, 1);
    }

    /**
     * @return the underlying map 
     */
    public Map<T, Integer> getMap() {

        return this.distribution;
    }

    /**
     * This method sorts the given distribution by the number of occurrences. 
     * This is done by filling a new array list with all entries and applying 
     * a comparator on this list. The returned list is in descending order. 
     * 
     * @return a sorted list of T (by occurrence) in descending order 
     */
    public List<Entry<T, Integer>> sort() {

        return MapUtil.sortEntiesByValues(this.distribution);
    }

    /**
     * @return the number of distinct elements
     */
    public int size() {

        return this.distribution.size();
    }
    
    public static void main(String[] args){
        
        ValueComparableMap<String, Integer> distribution = new ValueComparableMap<String, Integer>(Ordering.natural());
        distribution.put("NNP", 1);
        
        
//        DistributionMap<String> map = new DistributionMap<String>();
//        
//        map.addElement("b");
//        map.addElement("c");
        
        System.out.println(distribution);
    }
}
