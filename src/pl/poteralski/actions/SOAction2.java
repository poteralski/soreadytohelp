package pl.poteralski.actions;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
import com.intellij.openapi.project.Project;
import com.mashape.unirest.http.exceptions.UnirestException;
import pl.poteralski.stackoverflow.StackOverflowHelper;

/**
 *
 * Created by codeninja on 01.02.16.
 */
public class SOAction2 extends EditorAction {

    private static StackOverflowHelper stackOverflowHelper;

    public SOAction2() throws Exception {
        super(new PasteCodeHandler());
        stackOverflowHelper = new StackOverflowHelper();
    }

    private static class PasteCodeHandler extends EditorWriteActionHandler {

        public void executeWriteAction(Editor editor, DataContext dataContext) {
            if (editor == null) {
                return;
            }

            SelectionModel selectionModel = editor.getSelectionModel();

            String selectedText = selectionModel.getSelectedText();

            if (selectedText != null && selectedText.trim().length() > 0) {

                Project project = DataKeys.PROJECT.getData(dataContext);
                try {
                    if (project != null) {

                        String code = stackOverflowHelper.getSnippet(selectedText);
                        EditorModificationUtil.deleteSelectedText(editor);
                        EditorModificationUtil.insertStringAtCaret(editor, code);
                    }
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
