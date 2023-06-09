package polimi.ingsw.view.gui;

import com.sun.tools.javac.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class Sound {

    public static boolean play=false;

    public static synchronized void playSound(final String soundname) {
        if(play) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Clip clip = AudioSystem.getClip();
                        URL res = getClass().getClassLoader().getResource("audio/" + soundname);
                        File file = Paths.get(res.toURI()).toFile();
                        AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
                        clip.open(inputStream);
                        FloatControl gainControl =
                                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        gainControl.setValue(-10.0f);
                        clip.start();
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
            }).start();
        }
    }


}
