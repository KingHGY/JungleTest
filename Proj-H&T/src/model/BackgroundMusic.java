package model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class BackgroundMusic
{
    private volatile boolean run = true;
    private Thread mainThread;
    public void playMusic(String musicLocation)
    {
        try
        {
            File musicPath = new File(musicLocation);

            if (musicPath.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(0);
                //Clip.LOOP_CONTINUOUSLY
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void stopMusic()
    {
        synchronized (this)
        {
            run = false;
            notifyAll();
        }
    }
    public void continueMusic()
    {
        synchronized (this)
        {
            run = true;
            notifyAll();
        }
    }
    public void stop() {
        new Thread(new Runnable() {
            public void run() {
                stopMusic();
            }
        }).start();
    }
    public void continues() {
        new Thread(new Runnable() {
            public void run() {
                continueMusic();
            }
        }).start();
    }
}
