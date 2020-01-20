import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import javax.swing.JComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This ProjectConfigurable class appears on Settings dialog,
 * to let the user configure this plugin's behavior.
 */
public class BlackPycharmConfigurable implements SearchableConfigurable {

    private BlackPycharmGUI gui;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final BlackPycharmConfig config;

    private final Project project;

    public BlackPycharmConfigurable(@NotNull Project project) {
        this.project = project;
        config = BlackPycharmConfig.getInstance(project);
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "BlackPycharm Configuration";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "preference.BlackPycharmConfigurable";
    }

    @NotNull
    @Override
    public String getId() {
        return "preference.BlackPycharmConfigurable";
    }

    @Nullable
    @Override
    public Runnable enableSearch(String s) {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        gui = new BlackPycharmGUI();
        gui.createUI(project);
        return gui.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return gui.isModified();
    }

    @Override
    public void apply() {
        gui.apply();
    }

    @Override
    public void reset() {
        gui.reset();
    }

    @Override
    public void disposeUIResources() {
        gui = null;
    }
}
