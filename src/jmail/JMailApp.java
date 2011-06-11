/*
 * JMailApp.java
 */

package jmail;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import java.io.*;
/**
 * The main class of the application.
 */
public class JMailApp extends SingleFrameApplication {

    public static java.util.Properties applicationProperties = new java.util.Properties();
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new JMailView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of JMailApp
     */
    public static JMailApp getApplication() {
        return Application.getInstance(JMailApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        File f = new File(System.getProperty("user.home") + "/.jMail");
        if(!f.exists()) {
            f.mkdir();
        }
        else {
            File prop = new File(System.getProperty("user.home") + "/.jMail/properties");
            if(prop.exists()) {
             try {
                 applicationProperties.load(new BufferedInputStream(new FileInputStream(prop)));
             }
             catch(Exception e) {
                 System.out.println(e);
                 System.out.println("IN MAIN OPENING PROPERTIES");
             }
            }
        }
        launch(JMailApp.class, args);
    }
}
