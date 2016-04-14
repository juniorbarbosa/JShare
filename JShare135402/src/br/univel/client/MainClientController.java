package br.univel.client;

import java.net.URL;
import java.util.ResourceBundle;

import br.dagostini.jshare.comum.pojos.Arquivo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainClientController implements Initializable {

	@FXML
	private TextField nomeCliente, ipServidor, portaServidor;

	@FXML
	private TableView<Arquivo> table;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	@FXML
	private void conectar() {

	}

	@FXML
	private void desconectar() {

	}

	@FXML
	private void download() {

	}

}
