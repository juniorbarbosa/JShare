package br.dagostini.jshare.comum.pojos;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

public class ArquivoFX {

	private final StringProperty nome = new SimpleStringProperty();
    private final LongProperty tamanho = new SimpleLongProperty();
    private final ReadOnlyObjectWrapper<Arquivo> arquivo = new ReadOnlyObjectWrapper<>();

    public ArquivoFX(Arquivo arquivo) {
        this.arquivo.set(arquivo);
        initArquivoFX();
    }

    private void initArquivoFX() {
    	nomeProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			getArquivo().setNome(newValue);
		});
    	tamanhoProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> {
    		getArquivo().setTamanho((long) newValue);
		});
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

    public long getTamanho() {
        return tamanho.get();
    }

    public void setTamanho(long value) {
        tamanho.set(value);
    }

    public LongProperty tamanhoProperty() {
        return tamanho;
    }

    public Arquivo getArquivo() {
        return arquivo.get();
    }

    public ObjectProperty arquivoProperty() {
        return arquivo;
    }

}
