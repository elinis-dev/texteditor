package astropad;

import java.awt.Robot;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

public class DOptionPane {

	public static final String SPEICHERN = "Speichern";
	public static final String NICHT_SPEICHERN = "Nicht speichern";
	public static final String ABBRECHEN = "Abbrechen";
	public static final String PROFIL = "Profil";
	public static final String REPO = "Repository";

	public static void addStyleClass(Alert alert) {
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add("JMetroLightTheme.css");
	}

	public static void addIcon(Alert alert) {
		alert.getDialogPane().setGraphic(new ImageView(("icon.png").toString()));
	}

	public static String showInformation(String title, String message, String... options) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		addIcon(alert);
		addStyleClass(alert);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Information");
		alert.setHeaderText(title);
		alert.setContentText(message);

		if (options == null || options.length == 0) {
			options = new String[] { PROFIL, REPO, ABBRECHEN };
		}

		List<ButtonType> buttons = new ArrayList<>();
		for (String option : options) {
			buttons.add(new ButtonType(option));
		}

		alert.getDialogPane().requestFocus();

		alert.getButtonTypes().setAll(buttons);
		Optional<ButtonType> result = alert.showAndWait();
		if (!(result.isPresent())) {
			return ABBRECHEN;
		} else {
			return result.get().getText();
		}
	}

	public static String showWarning(String title, String message, String... options) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		addStyleClass(alert);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("DNotepad");
		alert.setContentText(message);
		alert.getDialogPane().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				event.consume();
				try {
					Robot r = new Robot();
					r.keyPress(java.awt.event.KeyEvent.VK_SPACE);
					r.keyRelease(java.awt.event.KeyEvent.VK_SPACE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		if (options == null || options.length == 0) {
			options = new String[] { SPEICHERN, NICHT_SPEICHERN, ABBRECHEN };
		}

		List<ButtonType> buttons = new ArrayList<>();
		for (String option : options) {
			buttons.add(new ButtonType(option));
		}

		alert.getDialogPane().requestFocus();

		alert.getButtonTypes().setAll(buttons);
		Optional<ButtonType> result = alert.showAndWait();
		if (!result.isPresent()) {
			return ABBRECHEN;
		} else {
			return result.get().getText();
		}
	}

	public static void showError(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Error");
		alert.setHeaderText(title);
		alert.setContentText(message);

		alert.showAndWait();
	}

	public static void showException(String title, String message, Exception exception) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Exception");
		alert.setHeaderText(title);
		alert.setContentText(message);

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		String exceptionText = sw.toString();

		Text label = new Text("Details:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}

	public static final String YES = "Yes";
	public static final String NO = "No";
	public static final String OK = "OK";
	public static final String CANCEL = "Cancel";

	public static String showConfirm(String title, String message, String... options) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Choose an option");
		alert.setHeaderText(title);
		alert.setContentText(message);

		// To make enter key press the actual focused button, not the first one. Just
		// like pressing "space".
		alert.getDialogPane().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				event.consume();
				try {
					Robot r = new Robot();
					r.keyPress(java.awt.event.KeyEvent.VK_SPACE);
					r.keyRelease(java.awt.event.KeyEvent.VK_SPACE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		if (options == null || options.length == 0) {
			options = new String[] { OK, CANCEL };
		}

		List<ButtonType> buttons = new ArrayList<>();
		for (String option : options) {
			buttons.add(new ButtonType(option));
		}

		alert.getButtonTypes().setAll(buttons);

		Optional<ButtonType> result = alert.showAndWait();
		if (!result.isPresent()) {
			return CANCEL;
		} else {
			return result.get().getText();
		}
	}

	public static String showTextInput(String title, String message, String defaultValue) {
		TextInputDialog dialog = new TextInputDialog(defaultValue);
		dialog.initStyle(StageStyle.UTILITY);
		dialog.setTitle("Input");
		dialog.setHeaderText(title);
		dialog.setContentText(message);

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}

	}

}