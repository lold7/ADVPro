package se233.chapter4;

/**
 * This is a launcher class to work around a JavaFX issue with executable JARs.
 * It calls the main method of the actual application class.
 */
public class JarLauncher {
    public static void main(String[] args) {
        Launcher.main(args);
    }
}
