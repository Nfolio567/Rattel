package one.nfolio.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import one.nfolio.pojo.Setting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ConfigResolver {
  public static Path getConfigPath() throws IOException {
    String os = System.getProperty("os.name").toLowerCase();

    Path configPath;
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

  private static Setting getSetting() throws IOException {
    Path configDir = getConfigPath();
    System.out.println(configDir);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(new File(
                    Paths.get(String.valueOf(configDir), "setting.json").toUri()),
            Setting.class
    );
  }

  public static float getBgmGain() throws IOException {
    Setting setting = getSetting();
    return (float) setting.getSound().getBgmVolume();
  }

  public static float getEffectsGain() throws IOException {
    Setting setting = getSetting();
    return (float) setting.getSound().getEffectsVolume();
  }
}
