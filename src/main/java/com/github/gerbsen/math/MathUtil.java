package com.github.gerbsen.math;

import java.util.Collection;


public class MathUtil {

	/**
	 * Returns the average value of all entries in the provided list.
	 * The value is not rounded. If the list is empty or null the 
	 * returned value is null.
	 * 
	 * @param list - the list to search for an average
	 * @return the average value
	 */
	public static <T extends Number> Double getAverage(Collection<T> list) {
		
		// we can't create an average in those cases
		if ( list == null || list.size() == 0 ) return -1D;
		
		// add all up
		double average = 0;
		for ( T listEntry : list ) average += listEntry.doubleValue(); 
		// and divide them by the list size
		return average / (double) list.size();
	}
	
	/**
	 * Returns the maximum value of all entries in the provided list.
	 * If the list is empty or null the returned value is null.
	 * 
	 * @param list - the list to search for an maximum
	 * @return the maximum value
	 */
	public static <T extends Number> Double getMax(Collection<T> list) {
		
		// we can't create a maximum in those cases
		if ( list == null || list.size() == 0 ) return -1D;
		
		Double maximum = 0D;
		for ( T listEntry : list ) maximum = Math.max(maximum, listEntry.doubleValue()); 
		return maximum;
	}
	
	/**
	 * Returns the minimum value of all entries in the provided list.
	 * If the list is empty or null the returned value is null.
	 * 
	 * @param list - the list to search for an minimum
	 * @return the minimum value
	 */
	public static <T extends Number> Double getMin(Collection<T> list) {
		
		// we can't create a minimum in those cases
		if ( list == null || list.size() == 0 ) return -1D;
		
		Double minimum = 0D;
		for ( T listEntry : list ) minimum = Math.min(minimum, listEntry.doubleValue()); 
		return minimum;
	}
}
