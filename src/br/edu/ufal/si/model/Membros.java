package br.edu.ufal.si.model;

public class Membros {
	private int idComunidade;
	private int idUsuario;
	private boolean administrador;

	public int getIdComunidade() {
		return idComunidade;
	}

	public void setIdComunidade(int idComunidade) {
		this.idComunidade = idComunidade;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}
}
