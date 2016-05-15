package br.edu.ufal.si.model;

public class Usuarios {

	private int id;
	private String login;
	private String senha;
	private String nome;
	private String email;

	public Usuarios(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
