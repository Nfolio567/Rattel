package one.nfolio.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Setting {
  @JsonProperty("font")
  private Font font;

  @JsonProperty("sound")
  private Sound sound;

  public Setting() {}

  public void setFont(Font font) {
    this.font = font;
  }

  public void setSound(Sound sound) {
    this.sound = sound;
  }

  public Font getFont() {
    return font;
  }

  public Sound getSound() {
    return sound;
  }
}
