package br.dagostini.jshare.comun;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Identificação do cliente.
 * 
 * @author fernandod
 *
 */
public class Cliente implements Serializable {

	private static final long serialVersionUID = 8998030883019232904L;

	private final StringProperty nome = new SimpleStringProperty();
	private final StringProperty ip = new SimpleStringProperty();
	private final IntegerProperty porta = new SimpleIntegerProperty();

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
