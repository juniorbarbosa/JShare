package br.dagostini.jshare.comum.pojos;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Diretorio {

	private final StringProperty nome = new SimpleStringProperty();

	public String getNome() {
		return nome.get();
	}

	public void setNome(String value) {
		nome.set(value);
	}

	public StringProperty nomeProperty() {
		return nome;
	}

}