package one.nfolio.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Sound {
  @JsonProperty("bgm-volume")
  private double bgmVolume;

  @JsonProperty("effects-volume")
  private double effectsVolume;

  @JsonProperty("mic-volume")
  private double micVolume;

  @JsonProperty("mute")
  private boolean mute;

  public Sound() {}
}
