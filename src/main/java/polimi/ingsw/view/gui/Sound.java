package polimi.ingsw.view.gui;

import com.sun.tools.javac.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
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



                        InputStream in = Sound.class.getResourceAsStream("/audio/"+soundname);


                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = in.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        byte[] audioData = outputStream.toByteArray();

                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(audioData);


                        AudioInputStream inputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);




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
