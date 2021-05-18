import repos.ConnectionManager;
import view.frames.SplashScreenFrame;


/**
 * Entry class of the application
 *
 * @author Kirichuk K.N.
 * @version 0.01 25.02.2021
 */
public class Main {

    /**
     * Entry point of the application.
     * Creates <code>SplashScreen</code> object .
     *
     * @param args command line arguments of the application
     */
    public static void main(String[] args) {
        ConnectionManager connectionManager = new ConnectionManager();
        new SplashScreenFrame(connectionManager);

    }
}

