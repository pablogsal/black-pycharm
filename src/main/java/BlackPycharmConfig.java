import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

/**
 * PersistentStateComponent keeps project config values.
 * Similar notion of 'preference' in Android
 */
@State(
    name = "BlackPycharmConfig",
    storages = {@Storage("BlackPycharmConfig.xml")}
)
public class BlackPycharmConfig implements PersistentStateComponent<BlackPycharmConfig> {

  /* NOTE: member should be "public" to be saved in xml */
  /* add_executable(): exe name */
  static final String EXECUTABLE_NAME_FILENAME = "/usr/local/bin/black";
  public static final String DEFAULT_EXECUTABLE_NAME = EXECUTABLE_NAME_FILENAME;
  public String executableName = DEFAULT_EXECUTABLE_NAME;  // persistent member should be public

  BlackPycharmConfig() {

  }

  String getExecutableName() {
    if (executableName == null) {
      // Error, it should not happen
      executableName = "";
    }
    return executableName;
  }

  void setExecutableName(String executableName) {
    this.executableName = executableName;
  }

  @Nullable
  @Override
  public BlackPycharmConfig getState() {
    return this;
  }

  @Override
  public void loadState(BlackPycharmConfig blackPycharmConfig) {
    XmlSerializerUtil.copyBean(blackPycharmConfig, this);
  }

  @Nullable
  public static BlackPycharmConfig getInstance(Project project) {
    BlackPycharmConfig sfec = ServiceManager.getService(project, BlackPycharmConfig.class);
    return sfec;
  }
}
