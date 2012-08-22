package com.github.gerbsen.file;

import java.io.File;
import java.io.FilenameFilter;


public class FilenameFilters {

	/**
	 * @return a new filename filter with the file ending ".txt"
	 */
	public static FilenameFilter getTxtFilter(){
		
		return new FilenameFilter(){

			public boolean accept(File file, String name) {

				return name.endsWith(".txt");
			}
		};
	}
}
