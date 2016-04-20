package br.dagostini.jshare.comun;

import java.io.Serializable;

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

	public ClienteFX getClienteFX() {
		return clienteFX;
	}

	public void setClienteFX(ClienteFX clienteFX) {
		this.clienteFX = clienteFX;
	}

}
