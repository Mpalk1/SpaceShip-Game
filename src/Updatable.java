import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface Updatable {
    void setup();
    void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException;
}
