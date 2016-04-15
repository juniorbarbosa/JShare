package br.univel.server;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comun.Cliente;
import br.dagostini.jshare.comun.IServer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MainServerController implements Initializable, IServer {

	@FXML
	private ComboBox cbxIp;

	@FXML
	private TextField txtPorta;

	@FXML
	private TextArea textArea;

	@FXML
	private Button btStart, btStop;

	private List<Cliente> mapClientes = new ArrayList<>();
	private IServer servidor;
	private Registry registry;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	@FXML
	private void startService() {
		String porta = txtPorta.getText().trim();

		if (!porta.matches("[0-9]+") || porta.length() > 5) {
			textArea.appendText("A porta deve ser um valor numérico de no máximo 5 dígitos!\n");
			return;
		}
		int numeroPorta = Integer.parseInt(porta);
		if (numeroPorta < 1024 || numeroPorta > 65535) {
			textArea.appendText("A porta deve estar entre 1024 e 65535\n");
			return;
		}

		try {

			servidor = (IServer) UnicastRemoteObject.exportObject(this, 0);
			registry = LocateRegistry.createRegistry(numeroPorta);
			registry.rebind(IServer.NOME_SERVICO, servidor);

			textArea.appendText("Serviço iniciado.\n");

			cbxIp.setDisable(true);
			txtPorta.setDisable(true);
			btStart.setDisable(true);
			btStop.setDisable(false);

		} catch (RemoteException e) {
			textArea.appendText("Erro criando registro, verifique se a porta já não está sendo usada.\n");
			e.printStackTrace();
		}
	}

	@FXML
	private void stopService() {

	}

	@Override
	public void registrarCliente(Cliente c) throws RemoteException {
		mapClientes.add(c);
	}

	@Override
	public void publicarListaArquivos(Cliente c, List<Arquivo> lista) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<Cliente, List<Arquivo>> procurarArquivo(String nome) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] baixarArquivo(Arquivo arq) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void desconectar(Cliente c) throws RemoteException {
		mapClientes.remove(c);
	}

}
