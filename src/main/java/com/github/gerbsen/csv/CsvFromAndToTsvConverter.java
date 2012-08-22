package com.github.gerbsen.csv;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class CsvFromAndToTsvConverter {

    // public static enum ConvertOption {
    //
    // SKIP_FIRST_LINE,
    // COMMA_AS_SEPARATOR,
    // SEMICOLON_AS_SEPARATOR,
    // DOUBLE_QUOTE_TEXT_DELIMITER
    // }

    /**
     * Converts a given csv file into a NEW tsv file in the same directory. The
     * filename of the new tsv file is the same as the csv file but the csv
     * extension. The csv need to end with csv. 
     * 
     * @param filename
     * @param path
     * @param separator
     * @param textDelimiter
     */
    public static void convertCsvToTsv(String path, String filename, String separator, String textDelimiter) {

        assert(filename.endsWith(".csv"));
        
        try {

            CSVReader reader = new CSVReader(new FileReader(path + filename), separator.charAt(0), textDelimiter.charAt(0));
            CSVWriter writer = new CSVWriter(new FileWriter(path + filename.replace(".csv", ".tsv")), '\t');
            
            String[] line = null;
            while ((line = reader.readNext()) != null) {
                writer.writeNext(line);
            }
            
            writer.close();
            reader.close();
        }
        catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        CsvFromAndToTsvConverter.convertCsvToTsv("/Users/gerb/Downloads/Safari/2012-05-02/", "group2-jens-daniel - final-merged.csv", ",", "\"");
    }
}
