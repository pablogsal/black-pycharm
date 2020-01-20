import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.changes.ui.SelectFilePathsDialog;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * GUI for the {@link BlackPycharmConfigurable}
 */
public class BlackPycharmGUI {
    private JPanel rootPanel;
    private JTextField exeNameTextField;
    private JCheckBox notShowDialogCheckBox;
    private JPanel exeNameSetting;
    private JButton pathSelectorButton;
    private JTextField maxLineLengthTextField;
    private JPanel maxLineLengthSetting;
    private JTextField runtimeOutputDirectoryTextField;
    private BlackPycharmConfig mConfig;

    BlackPycharmGUI() {
        // $$$setupUI$$$ will be executed here (inserted automatically)
        pathSelectorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                final JFileChooser fc = new JFileChooser();
                //Handle open button action.
                if (actionEvent.getSource() == pathSelectorButton) {
                    int returnVal = fc.showOpenDialog(rootPanel);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        exeNameTextField.setText(file.getAbsolutePath());
                    }
                }
            }
        });
    }

    public void createUI(Project project) {
        mConfig = BlackPycharmConfig.getInstance(project);
        exeNameTextField.setText(mConfig.getExecutableName());
        maxLineLengthTextField.setText(mConfig.getMaxLineLength());
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
        modified |= !maxLineLengthTextField.getText().equals(mConfig.getMaxLineLength());
        return modified;
    }

    public void apply() {
        mConfig.setExecutableName(exeNameTextField.getText());
        mConfig.setMaxLineLength(maxLineLengthTextField.getText().trim());
    }

    public void reset() {
        exeNameTextField.setText(mConfig.getExecutableName());
        maxLineLengthTextField.setText(mConfig.getMaxLineLength());
    }

       /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
