package one.nfolio.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Setting {
  @JsonProperty("font")
  private Font font;

  @JsonProperty("sound")
  private Sound sound;

  @JsonProperty("user-name")
  private String userName;

  @JsonProperty("jwt-token")
  private String jwtToken;

  public Setting() {}

}
