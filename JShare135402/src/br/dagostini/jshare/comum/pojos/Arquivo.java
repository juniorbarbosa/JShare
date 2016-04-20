package br.dagostini.jshare.comum.pojos;

import java.io.Serializable;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Arquivo implements Serializable {

	private static final long serialVersionUID = 8077295408159335912L;

	private String nome;
	private long tamanho;
	private ArquivoFX arquivoFX;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
		if (getArquivoFX() != null) {
			getArquivoFX().setNome(nome);
		}
	}

	public long getTamanho() {
		return tamanho;
	}

	public void setTamanho(long tamanho) {
		this.tamanho = tamanho;
		if (getArquivoFX() != null) {
			getArquivoFX().setTamanho(tamanho);
		}
	}

	public ArquivoFX getArquivoFX() {
		return arquivoFX;
	}

	public void setArquivoFX(ArquivoFX arquivoFX) {
		this.arquivoFX = arquivoFX;
	}
}
