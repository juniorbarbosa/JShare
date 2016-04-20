package br.dagostini.jshare.comun;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

public class ClienteFX {

	private final StringProperty nome = new SimpleStringProperty();
	private final StringProperty ip = new SimpleStringProperty();
	private final IntegerProperty porta = new SimpleIntegerProperty();
	private final ReadOnlyObjectWrapper<Cliente> cliente = new ReadOnlyObjectWrapper<>();

	public ClienteFX(Cliente cliente) {
		if (cliente == null) {
			throw new IllegalArgumentException("O cliente deve ser informado");
		}
		this.cliente.set(cliente);
		initClienteFX();
	}

	private void initClienteFX() {
		nomeProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			getCliente().setNome(newValue);
		});
		ipProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			getCliente().setIp(newValue);
		});
		portaProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> {
			if (newValue == null) {
				getCliente().setPorta(-1);
			} else {
				getCliente().setPorta(newValue.intValue());
			}
		});
	}

	public Cliente getCliente() {
		return cliente.get();
	}

	public ReadOnlyObjectProperty clienteProperty() {
		return cliente.getReadOnlyProperty();
	}

	public String getNome() {
		return nome.get();
	}

	public void setNome(String value) {
		nome.set(value);
	}

	public StringProperty nomeProperty() {
		return nome;
	}

	public String getIp() {
		return ip.get();
	}

	public void setIp(String value) {
		ip.set(value);
	}

	public StringProperty ipProperty() {
		return ip;
	}

	public int getPorta() {
		return porta.get();
	}

	public void setPorta(int value) {
		porta.set(value);
	}

	public IntegerProperty portaProperty() {
		return porta;
	}

}
