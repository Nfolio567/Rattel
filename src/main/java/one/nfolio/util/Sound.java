package one.nfolio.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sound {
  @JsonProperty("bgm-volume")
  private double bgmVolume;

  @JsonProperty("mic-volume")
  private double micVolume;

  @JsonProperty("mute")
  private boolean mute;

  public Sound() {}

  public void setBgmVolume(double bgmVolume) {
    this.bgmVolume = bgmVolume;
  }

  public void setMicVolume(double micVolume) {
    this.micVolume = micVolume;
  }

  public void setMute(boolean mute) {
    this.mute = mute;
  }

  public double getBgmVolume() {
    return bgmVolume;
  }

  public double getMicVolume() {
    return micVolume;
  }

  public boolean getMute() {
    return mute;
  }
}
