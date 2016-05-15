package br.edu.ufal.si.model;

public class Amigos {

	private int id;
	private int idUsuario;
	private int idUsuarioAmigo;
	private String mensagem;
	private boolean aceito;

	public Amigos(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int i) {
		this.id = i;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdUsuarioAmigo() {
		return idUsuarioAmigo;
	}

	public void setIdUsuarioAmigo(int idUsuarioAmigo) {
		this.idUsuarioAmigo = idUsuarioAmigo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public boolean getAceito() {
		return aceito;
	}

	public void setAceito(boolean aceito) {
		this.aceito = aceito;
	}
}
