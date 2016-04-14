package br.univel.server;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class MainServerController implements Initializable {

	@FXML
	private TextField txtPorta;

	@FXML
	private TextArea textArea;

	@FXML
	private Button btStart, btStop;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	@FXML
	private void startService() {

	}

	@FXML
	private void stopService() {

	}

}
