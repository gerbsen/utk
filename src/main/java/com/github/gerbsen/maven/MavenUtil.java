/**
 * 
 */
package com.github.gerbsen.maven;

import java.io.File;

/**
 * @author Daniel Gerber <dgerber@informatik.uni-leipzig.de>
 *
 */
public class MavenUtil {

    public static File loadFile(String path) {
        
        return new File(MavenUtil.class.getResource(path).getFile());
    }
}