package one.nfolio.util;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class SoundConfig {
  private static FloatControl clickButtonGainControl;

  private static FloatControl bgmGainControl;
  public static void clickButton() {
    try {
      Clip clip = AudioSystem.getClip();
      InputStream rawBGM = Objects.requireNonNull(SoundConfig.class.getResourceAsStream("/media/button.wav"));
      clip.open(AudioSystem.getAudioInputStream(new BufferedInputStream(rawBGM)));

      clickButtonGainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      float lineValue = ConfigResolver.getEffectsGain();
      System.out.println(lineValue);
      clickButtonGainControl.setValue((float) (20 * Math.log10(lineValue == 0 ? 0.0001 : lineValue)) + clickButtonGainControl.getMaximum());
      clip.start();
    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
      ExceptionAlert alert = new ExceptionAlert();
      alert.show(e);
    }
  }

  public static void setClickButtonGain(float newValue) {
    clickButtonGainControl.setValue(newValue);
  }

  public static void startBGM() {
    try {
      Clip clip = AudioSystem.getClip();
      InputStream rawBGM = Objects.requireNonNull(SoundConfig.class.getResourceAsStream("/media/bgm.wav"));
      clip.open(AudioSystem.getAudioInputStream(new BufferedInputStream(rawBGM)));

      bgmGainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      float lineValue = ConfigResolver.getBgmGain();
      System.out.println(lineValue);
      bgmGainControl.setValue((float) (20 * Math.log10(lineValue == 0 ? 0.0001 : lineValue)) + bgmGainControl.getMaximum());
      clip.loop(Clip.LOOP_CONTINUOUSLY);
      clip.start();
    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
      ExceptionAlert alert = new ExceptionAlert();
      alert.show(e);
    }
  }

  public static void setBgmGain(float newValue) {
    bgmGainControl.setValue(newValue + bgmGainControl.getMaximum());
  }
}
