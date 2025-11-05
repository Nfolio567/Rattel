package one.nfolio.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ConfigResolver {
  private static Path configPath;
  public static Path getConfigPath() throws IOException {
    String os = System.getProperty("os.name").toLowerCase();

    if(os.contains("win")) {
      configPath = Paths.get(System.getenv("APPDATA"), "Rattel");
    } else if(os.contains("mac")) {
      configPath = Paths.get(System.getProperty("user.home"), "Library", "Application Support", "Rattel");
    } else {
      String xdg = System.getenv("XDG_CONFIG_HOME");
      if(xdg != null) {
        configPath = Paths.get(xdg, "Rattel");
      } else {
        configPath = Paths.get(System.getProperty("user.home"), ".config", "Rattel");
      }
    }

    Files.createDirectories(configPath);
    Path settingJson = Paths.get(String.valueOf(configPath), "setting.json");
    if (!Files.exists(settingJson)) {
      Files.createFile(settingJson);
      try (InputStream settingContent = ConfigResolver.class.getResourceAsStream("/settingBaby/setting.json")) {
        Files.write(settingJson, Objects.requireNonNull(settingContent).readAllBytes());
      }
    }

    return configPath;
  }

  public static float getGain() throws IOException {
    getConfigPath();
    ObjectMapper mapper = new ObjectMapper();
    Setting setting = mapper.readValue(new File(
            Paths.get(String.valueOf(configPath), "setting.jso").toUri()),
            Setting.class
    );
    return setting.getSound().getMicVolume();
  }
}
