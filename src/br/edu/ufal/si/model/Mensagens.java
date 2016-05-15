package br.edu.ufal.si.model;

public class Mensagens {
	private int id;
	private int idUsuario;
	private int idUsuarioAmigo;
	private String mensagem;

	public Mensagens(int i) {
		this.id = i;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
}
