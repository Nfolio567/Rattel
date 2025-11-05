package one.nfolio.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Font {
  @JsonProperty("size")
  private int size;

  @JsonProperty("family")
  private String family;

  public Font() {}

  public void setSize(int size) {
    this.size = size;
  }

  public void setFamily(String family) {
    this.family = family;
  }

  public int getSize() {
    return size;
  }

  public String getFamily() {
    return family;
  }
}
