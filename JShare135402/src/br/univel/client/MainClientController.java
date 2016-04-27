package br.univel.client;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comum.pojos.ArquivoFX;
import br.dagostini.jshare.comun.Cliente;
import br.dagostini.jshare.comun.ClienteFX;
import br.dagostini.jshare.comun.IServer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainClientController implements Initializable {

	@FXML
	private TextField nomeCliente, ipServidor, portaServidor;

	@FXML
	private Button btConectar, btDesconectar;

	@FXML
	private TableView<ArquivoFX> table;

	private IServer servidor;
	private Registry registry;

	private List<Arquivo> listaArquivoUpload = new ArrayList<>();
	private Map<Cliente, List<Arquivo>> mapArquivos = new HashMap<>();

	private ClienteFX clienteFX = new ClienteFX(new Cliente());

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		nomeCliente.setText("zero");
		ipServidor.setText("127.0.0.1");
		portaServidor.setText("1818");
		createDiretorioDownload();
	}

	private void createDiretorioDownload() {
		File diretorioDownload = new File("C:\\Users\\Junior\\git\\JShare\\JShare135402\\download");
		if (!diretorioDownload.exists()) {
			diretorioDownload.mkdir();
		}
	}

	@FXML
	private void conectar() {
		table.getItems().clear();

		String nome = nomeCliente.getText().trim();
		String ip = ipServidor.getText().trim();
		String porta = portaServidor.getText().trim();

		clienteFX.setNome(nome);

		try {
			if (nome.isEmpty() || ip.isEmpty() || porta.isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Campos Vazios");
				alert.setHeaderText(null);
				alert.setContentText("Existem campos vazios. Por favor verifique.");

				alert.showAndWait();

				nomeCliente.setDisable(false);
				ipServidor.setDisable(false);
				portaServidor.setDisable(false);
				btConectar.setDisable(false);
				btDesconectar.setDisable(true);
			} else {
				int numeroPorta = Integer.parseInt(porta);

				registry = LocateRegistry.getRegistry(ip, numeroPorta);
				IServer servico = (IServer) registry.lookup(IServer.NOME_SERVICO);
				servico.registrarCliente(clienteFX.getCliente());
				servico.publicarListaArquivos(clienteFX.getCliente(), listaArquivoUpload);
				mapArquivos = servico.procurarArquivo("");
				servidor = servico;

				nomeCliente.setDisable(true);
				ipServidor.setDisable(true);
				portaServidor.setDisable(true);
				btConectar.setDisable(true);
				btDesconectar.setDisable(false);
			}
		} catch (RemoteException | NotBoundException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Problema de conexão");
			alert.setHeaderText("Existem dados incorretos. Por favor verifique os dados de conexão com o servidor.");
			if (e.getMessage() != null) {
				String messageException = e.getMessage().replaceAll(";.*", "");
				alert.setContentText(messageException);

			}
			alert.showAndWait();
		}

		listaArquivosTabela();
	}

	@FXML
	private void desconectar() {
		table.getItems().clear();

		String nome = nomeCliente.getText().trim();

		clienteFX.setNome(nome);

		try {
			if (servidor != null) {
				servidor.desconectar(clienteFX.getCliente());
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
		ArquivoFX arquivo = table.getSelectionModel().getSelectedItem();
		File file = new File("C:\\Users\\Junior\\git\\JShare\\JShare135402\\download\\" + arquivo.getNome());
		try {
			byte[] arquivobyte = servidor.baixarArquivo(arquivo.getArquivo());
			Files.write(Paths.get(file.getPath()), arquivobyte, StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void listaArquivosTabela() {
		mapArquivos.keySet().stream().forEach(cliente -> {
			if (!cliente.equals(clienteFX.getCliente())) {
				List<Arquivo> arquivos = mapArquivos.get(cliente);
				for (Arquivo arq : arquivos) {
					ArquivoFX arquivoFX = new ArquivoFX(new Arquivo());
					arquivoFX.setNome(arq.getNome());
					arquivoFX.setTamanho(arq.getTamanho());
					table.getItems().add(arquivoFX);
				}
			}
		});
	}

}
