/**
 * 
 */
package com.github.gerbsen.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * @author Daniel Gerber <dgerber@informatik.uni-leipzig.de>
 *
 */
public class DistributionMap<T> {

    private Map<T,Integer> distribution;
    
    /**
     * 
     */
    public DistributionMap() {
        
        this.distribution = new HashMap<T,Integer>();
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
}
