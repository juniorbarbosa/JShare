package br.dagostini.jshare.comun;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Objects;

public class Cliente implements Serializable {

	private static final long serialVersionUID = 8998030883019232904L;

	private String nome;
	private String ip;
	private int porta;
	private ClienteFX clienteFX;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
		if (getClienteFX() != null) {
			getClienteFX().setNome(nome);
		}
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
		if (getClienteFX() != null) {
			getClienteFX().setIp(ip);
		}
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
		if (getClienteFX() != null) {
			getClienteFX().setPorta(porta);
		}
	}

	@Transient
	public ClienteFX getClienteFX() {
		return clienteFX;
	}

	@Transient
	public void setClienteFX(ClienteFX clienteFX) {
		this.clienteFX = clienteFX;
	}

	@Override
	public boolean equals(Object outro) {
		return outro instanceof Cliente && getNome().equals(((Cliente) outro).getNome());
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + Objects.hashCode(this.nome);
		return hash;
	}

}
