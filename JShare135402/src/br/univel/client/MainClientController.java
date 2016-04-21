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

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		nomeCliente.setText("zero");
		ipServidor.setText("127.0.0.1");
		portaServidor.setText("1818");
	}

	@FXML
	private void conectar() {
		table.getItems().clear();
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
			servico.publicarListaArquivos(clienteFX.getCliente(), listaArquivoUpload);
			mapArquivos = servico.procurarArquivo("");
			servidor = servico;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		listaArquivosTabela();

		nomeCliente.setDisable(true);
		ipServidor.setDisable(true);
		portaServidor.setDisable(true);
		btConectar.setDisable(true);
		btDesconectar.setDisable(false);

	}

	@FXML
	private void desconectar() {
		table.getItems().clear();

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
		System.out.println("1");
		try {
			System.out.println("2");
			
			ArquivoFX arquivo = table.getSelectionModel().getSelectedItem();
			
			File arquivoFx = new File(arquivo.getNome());
			
			System.out.println("arquivo: " + arquivoFx.getName());
			
			System.out.println("3" + arquivo.getNome());
			
			File file = new File("C:\\Users\\Junior\\git\\JShare\\JShare135402\\download\\" + arquivoFx.getName());
			
			System.out.println("4" + arquivo.getNome());
			
			System.out.println("5" + arquivo.getNome());
			
			// ate aqui vem
			
			byte[] arquivobyte = servidor.baixarArquivo(arquivo.getArquivo());
			
			System.out.println("6" + arquivo.getNome());
			
			Files.write(Paths.get(file.getPath()), arquivobyte, StandardOpenOption.CREATE);
			
			System.out.println("7" + arquivo.getNome());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void listaArquivosTabela() {
		for (Map.Entry<Cliente, List<Arquivo>> entry : mapArquivos.entrySet()) {
			Cliente cliente = entry.getKey();
			List<Arquivo> arquivos = entry.getValue();
			for (Arquivo arq : arquivos) {
				ArquivoFX arquivoFX = new ArquivoFX(new Arquivo());
				arquivoFX.setNome(arq.getNome());
				arquivoFX.setTamanho(arq.getTamanho());
				table.getItems().add(arquivoFX);
			}
		}

	}

}
