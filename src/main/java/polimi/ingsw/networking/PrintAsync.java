package polimi.ingsw.networking;

import org.fusesource.jansi.Ansi;

public class PrintAsync {
    /**
     * Avoid freezing console for quick edit mode in console
     * NB If the server prints strings and you select the text, the quick edit mode pauses the system.out until
     * a key is pressed.
     * Doing so, not only the prints are paused but also the main thread is prevented from being blocked.
     * With this method quick edit mode pause only the prints avoiding the pausing of the main thread running the code
     *
     * @param msg to print
     */
    public static void printAsync(Ansi msg){
        new Thread(()->{System.out.println(msg);}).start();
    }
    public static void printAsync(String msg){
        new Thread(()->{System.out.println(msg);}).start();
    }
    public static void printAsync(StringBuilder msg){
        new Thread(()->{System.out.println(msg);}).start();
    }
    public static void printAsyncNoLine(String msg){
        new Thread(()->{System.out.print(msg);}).start();
    }
}
