package de.danielgerber.www;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileDownloader {

    private static final Logger logger = LoggerFactory.getLogger(FileDownloader.class);
    
    public static void downloadFile(String url, String outputFile) {
        
        try {
            
            URL google = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(google.openStream());
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
        }
        catch (MalformedURLException e) {
            
            String error = "Could not download file for url: \"" + url + "\""; 
            e.printStackTrace();
            logger.error(error, e);
            throw new RuntimeException(error, e);
        }
        catch (IOException e) {
            
            String error = "Could not download file for url: \"" + url + "\" and save it to file: " + outputFile; 
            e.printStackTrace();
            logger.error(error, e);
            throw new RuntimeException(error, e);
        }
    }
    
    /**
     * 
     * @param URLName
     * @return
     */
    public static boolean exists(String url) {

        try {
            
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        }
        catch (Exception e) {
            
            String error = "Could not if remote file exits: " + url; 
            e.printStackTrace();
            logger.error(error, e);
            throw new RuntimeException(error, e);
        }
    }

    public static void main(String[] args) {

        FileDownloader.downloadFile("http://dumps.wikimedia.org/dewiki/latest/dewiki-latest-pages-articles.xml.bz2", "/Users/gerb/dewiki-latest-pages-articles.xml.bz2");
    }
}
