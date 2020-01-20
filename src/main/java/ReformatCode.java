import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ReformatCode extends AnAction {

  private BlackPycharmConfig config;

  public ReformatCode() {
    super();
  }

  private byte[] toByteArray(InputStream inputStream) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    int read;
    byte[] bytes = new byte[1024];

    while ((read = inputStream.read(bytes)) != -1) {
      byteArrayOutputStream.write(bytes, 0, read);
    }

    return byteArrayOutputStream.toByteArray();
  }

  // --Commented out by Inspection START (20/01/2020 16:17):
  //  private byte[] getProcessStdout(Process p) throws IOException {
  //    return toByteArray(p.getInputStream());
  //  }
  // --Commented out by Inspection STOP (20/01/2020 16:17)

  private byte[] getProcessStderr(Process p) throws IOException {
    return toByteArray(p.getErrorStream());
  }

  private void reformatFile(String path) throws InterruptedException, IOException {

    String blackPath = config.getExecutableName();
    // Invoke black.
    Process blackProcess = Runtime.getRuntime().exec(new String[]{
        blackPath, path,
    });

    blackProcess.waitFor();

    if (blackProcess.exitValue() != 0) {
      String errorMsg = new String(getProcessStderr(blackProcess));
      throw new RuntimeException(errorMsg);
    }
  }

  // --Commented out by Inspection START (20/01/2020 16:09):
  //  private void writeFileContent(InputStream inputStream, OutputStream outputStream)
  //          throws IOException {
  //    int read;
  //    byte[] bytes = new byte[1024];
  //
  //    while ((read = inputStream.read(bytes)) != -1) {
  //      outputStream.write(bytes, 0, read);
  //    }
  //  }
  // --Commented out by Inspection STOP (20/01/2020 16:09)

  private void displayErrorMessage(AnActionEvent event, String message) {
    StatusBar statusBar = WindowManager.getInstance()
            .getStatusBar(Objects.requireNonNull(
                    PlatformDataKeys.PROJECT.getData(event.getDataContext())));


    JBPopupFactory.getInstance()
            .createHtmlTextBalloonBuilder("BlackPycharm: " + message,
                    MessageType.ERROR, null)
            .setFadeoutTime(7500)
            .createBalloon()
            .show(RelativePoint.getSouthEastOf(statusBar.getComponent()),
                    Balloon.Position.atRight);
  }

  @Override
  public void actionPerformed(AnActionEvent event) {

    Project project = event.getRequiredData(CommonDataKeys.PROJECT);
    config = BlackPycharmConfig.getInstance(project);

    // extract current open file, it could be file or folder or null it doesn't get focus
    VirtualFile virtualFile = event.getData(PlatformDataKeys.VIRTUAL_FILE);

    if (virtualFile == null || virtualFile.isDirectory()) {
      return;
    }

    String path = virtualFile.getPath();

    if (!path.endsWith(".py")) {
      return;
    }

    if (!virtualFile.isWritable()) {
      return;
    }

    try {
      // save changes so that IDE doesn't display message box
      FileDocumentManager fileDocumentManager = FileDocumentManager.getInstance();
      Document document = fileDocumentManager.getDocument(virtualFile);
      assert document != null;
      fileDocumentManager.saveDocument(document);

      // reformat it using black
      this.reformatFile(virtualFile.getPath());

      // unlock the file & refresh
      Application app = ApplicationManager.getApplication();
      app.runWriteAction(() -> virtualFile.refresh(false, false));
    } catch (IOException | InterruptedException | RuntimeException e) {
      this.displayErrorMessage(event, e.getMessage());
    }
  }
}
