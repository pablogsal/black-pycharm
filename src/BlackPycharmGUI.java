import com.intellij.openapi.project.Project;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;

/**
 * GUI for the {@link BlackPycharmConfigurable}
 */
public class BlackPycharmGUI {
    private JPanel rootPanel;
    private JTextField exeNameTextField;
    private JCheckBox notShowDialogCheckBox;
    private JPanel exeNameSetting;
    private JTextField runtimeOutputDirectoryTextField;
    private BlackPycharmConfig mConfig;

    BlackPycharmGUI() {
        // $$$setupUI$$$ will be executed here (inserted automatically)
    }

    public void createUI(Project project) {
        mConfig = BlackPycharmConfig.getInstance(project);
        exeNameTextField.setText(mConfig.getExecutableName());
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JTextField getExeNameTextField() {
        return exeNameTextField;
    }

    public boolean isModified() {
        boolean modified = false;
        modified |= !exeNameTextField.getText().equals(mConfig.getExecutableName());
        return modified;
    }

    public void apply() {
        mConfig.setExecutableName(exeNameTextField.getText());
    }

    public void reset() {
        exeNameTextField.setText(mConfig.getExecutableName());
    }

       /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
