import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    File player_shooting_sound = new File("assets/sfx/laserShoot.wav");
    File player_powerUp_sound = new File("assets/sfx/powerUp.wav");
    File enemy_hit = new File("assets/sfx/enemyHit.wav");
    AudioInputStream AudioStream;


    public void playShootingSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioStream = AudioSystem.getAudioInputStream(player_shooting_sound);
        Clip Clip = AudioSystem.getClip();
        Clip.open(AudioStream);
        FloatControl volume = (FloatControl) Clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-15.0f);
        Clip.start();
        System.out.println("shooting sound playing");
    }
    public void playPowerUpSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioStream = AudioSystem.getAudioInputStream(player_powerUp_sound);
        Clip Clip = AudioSystem.getClip();
        Clip.open(AudioStream);
        FloatControl volume = (FloatControl) Clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-15.0f);
        Clip.start();
    }
    public void playEnemyHit() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioStream = AudioSystem.getAudioInputStream(enemy_hit);
        Clip Clip = AudioSystem.getClip();
        Clip.open(AudioStream);
        FloatControl volume = (FloatControl) Clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-15.0f);
        Clip.start();
    }
}
