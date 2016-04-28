package br.univel.server;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.control.TextArea;

public class MainServerController implements Initializable, IServer {

	@FXML
	private TextField txtPorta;

	@FXML
	private TextArea textArea;

	@FXML
	private Button btStart, btStop;

	private IServer servidor;
	private Registry registry;

	private List<Cliente> listClientes = new ArrayList<>();
	private Map<Cliente, List<Arquivo>> mapArquivosPorCliente = new HashMap<>();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		txtPorta.setText("1818");
		createDiretorioUpload();
	}

	private void createDiretorioUpload() {
		File diretorioUpload = new File("C:\\Users\\Junior\\git\\JShare\\JShare135402\\upload");
		if (!diretorioUpload.exists()) {
			diretorioUpload.mkdir();
		}
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

			textArea.appendText("SERVIDOR INICIADO.\n");

			txtPorta.setDisable(true);
			btStart.setDisable(true);
			btStop.setDisable(false);

		} catch (RemoteException e) {
			textArea.appendText("Erro criando registro, verifique se a porta já não está sendo usada.\n");
			e.printStackTrace();
		}
	}

	@FXML
	protected void stopService() {
		textArea.appendText("DESCONECTANDO TODOS OS CLIENTES.\n");
		mapArquivosPorCliente.clear();

		try {
			UnicastRemoteObject.unexportObject(this, true);
			UnicastRemoteObject.unexportObject(registry, true);

			txtPorta.setDisable(false);
			btStart.setDisable(false);

			btStop.setDisable(true);

			textArea.appendText("SERVIDOR DESLIGADO.\n");

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void registrarCliente(Cliente c) throws RemoteException {
		listClientes.add(c);
		textArea.appendText("CLIENTE " + c.getNome().toUpperCase() + " CONECTADO.\n");
	}

	@Override
	public void publicarListaArquivos(Cliente c, List<Arquivo> lista) throws RemoteException {
		File diretorioUpload = new File("C:\\Users\\Junior\\git\\JShare\\JShare135402\\upload");
		for (File file : diretorioUpload.listFiles()) {
			if (file.isFile()) {
				Arquivo arquivo = new Arquivo();
				arquivo.setNome(file.getName());
				arquivo.setTamanho(file.length());
				lista.add(arquivo);
				mapArquivosPorCliente.put(c, lista);
			}
		}
		for (Arquivo arquivo : lista) {
			textArea.appendText(
					"Arquivos Disponíveis: " + arquivo.getNome() + ", Tamanho Arquivo: " + arquivo.getTamanho() + "\n");
		}

	}

	@Override
	public Map<Cliente, List<Arquivo>> procurarArquivo(String nome) throws RemoteException {
		if (nome.isEmpty()) {
			return mapArquivosPorCliente;
		} else {
			Map<Cliente, List<Arquivo>> map = new HashMap<>();
			List<Arquivo> lista = new ArrayList<>();
			mapArquivosPorCliente.keySet().stream().forEach(cliente -> {
				List<Arquivo> arquivos = mapArquivosPorCliente.get(cliente);
				for (Arquivo arq : arquivos) {
					Arquivo arquivo = new Arquivo();
					arquivo.setNome(arq.getNome());
					arquivo.setTamanho(arq.getTamanho());
					if (arquivo.getNome().equals(nome)) {
						lista.add(arquivo);
						map.put(cliente, lista);
					}
				}
			});
			return map;
		}
	}

	@Override
	public byte[] baixarArquivo(Arquivo arq) throws RemoteException {
		File file = new File("C:\\Users\\Junior\\git\\JShare\\JShare135402\\upload\\" + arq.getNome());
		Path path = Paths.get(file.getPath());
		try {
			byte[] dados = Files.readAllBytes(path);
			return dados;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void desconectar(Cliente c) throws RemoteException {
		listClientes.remove(c);
		mapArquivosPorCliente.remove(c);
		textArea.appendText("CLIENTE " + c.getNome().toUpperCase() + " DESCONECTADO.\n");
	}

}
