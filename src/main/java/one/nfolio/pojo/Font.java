package one.nfolio.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Font {
  @JsonProperty("size")
  private int size;

  @JsonProperty("family")
  private String family;

  public Font() {}

}
