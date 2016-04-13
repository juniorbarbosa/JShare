package br.dagostini.jshare.comum.pojos;

import java.io.Serializable;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Arquivo implements Serializable {

	private static final long serialVersionUID = 8077295408159335912L;

	private final StringProperty nome = new SimpleStringProperty();
	private final LongProperty tamanho = new SimpleLongProperty();

	public String getNome() {
		return nome.get();
	}

	public void setNome(String value) {
		nome.set(value);
	}

	public StringProperty nomeProperty() {
		return nome;
	}

	public long getTamanho() {
		return tamanho.get();
	}

	public void setTamanho(long value) {
		tamanho.set(value);
	}

	public LongProperty tamanhoProperty() {
		return tamanho;
	}

	@Override
	public String toString() {
		return nome + " - " + tamanho;
	}
}
