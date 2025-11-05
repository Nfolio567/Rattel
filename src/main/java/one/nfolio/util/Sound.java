package one.nfolio.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sound {
  @JsonProperty("bgm-volume")
  private float bgmVolume;

  @JsonProperty("mic-volume")
  private float micVolume;

  @JsonProperty("mute")
  private boolean mute;

  public Sound() {}

  public void setBgmVolume(float bgmVolume) {
    this.bgmVolume = bgmVolume;
  }

  public void setMicVolume(float micVolume) {
    this.micVolume = micVolume;
  }

  public void setMute(boolean mute) {
    this.mute = mute;
  }

  public float getBgmVolume() {
    return bgmVolume;
  }

  public float getMicVolume() {
    return micVolume;
  }

  public boolean getMute() {
    return mute;
  }
}
