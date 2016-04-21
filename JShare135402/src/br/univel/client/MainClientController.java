package br.univel.client;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comun.Cliente;
import br.dagostini.jshare.comun.ClienteFX;
import br.dagostini.jshare.comun.IServer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainClientController implements Initializable {

	@FXML
	private TextField nomeCliente, ipServidor, portaServidor;

	@FXML
	private Button btConectar, btDesconectar;

	@FXML
	private TableView<Arquivo> table;

	private IServer servidor;
	private Registry registry;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	@FXML
	private void conectar() {
		String nome = nomeCliente.getText().trim();
		String ip = ipServidor.getText().trim();
		String porta = portaServidor.getText().trim();
		int numeroPorta = Integer.parseInt(porta);

		ClienteFX clienteFX = new ClienteFX(new Cliente());
		clienteFX.setNome(nome);

		try {
			registry = LocateRegistry.getRegistry(ip, numeroPorta);
			IServer servico = (IServer) registry.lookup(IServer.NOME_SERVICO);
			servico.registrarCliente(clienteFX.getCliente());
			servidor = servico;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		nomeCliente.setDisable(true);
		ipServidor.setDisable(true);
		portaServidor.setDisable(true);
		btConectar.setDisable(true);
		btDesconectar.setDisable(false);

	}

	@FXML
	private void desconectar() {
		String nome = nomeCliente.getText().trim();

		ClienteFX clienteFx = new ClienteFX(new Cliente());
		clienteFx.setNome(nome);

		try {
			if (servidor != null) {
				servidor.desconectar(clienteFx.getCliente());
				servidor = null;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		registry = null;
		servidor = null;

		nomeCliente.setDisable(false);
		ipServidor.setDisable(false);
		portaServidor.setDisable(false);
		btConectar.setDisable(false);
		btDesconectar.setDisable(true);
	}

	@FXML
	private void download() {

	}

}
