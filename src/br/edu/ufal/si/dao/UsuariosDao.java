# UFAL - BSI - EAD - 2014.1
# Nome: Carlos Roberto dos Santos Silva
# Data: 
# Objetivo: criar app executado via terminal, parecido com Facebook

package br.edu.ufal.si.dao;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufal.si.model.Usuarios;

public class UsuariosDao implements interDao<Usuarios> {

	private List<Usuarios> usuarios = new ArrayList<Usuarios>();

	@Override
	public void salva(Usuarios u) {
		if (usuarios.contains(u)) {
			System.out.println("\nUsuario " + u.getId() + " atualizado com sucesso!");
		} else {
			usuarios.add(u);
			System.out.println("Conta criada com sucesso no iEAD!");
		}
	}

	@Override
	public void deleta(Usuarios u) {
		usuarios.remove(u);
		System.out.println("\nApagando Usuario " + u.getId() + "...");
		u = null;
		System.out.println("\nConta removida com sucesso!");
	}

	@Override
	public Usuarios getById(int id) {
		Usuarios u = null;
		for (Usuarios us : usuarios) {
			if (us.getId() == id) {
				u = us;
			}
		}
		return u;
	}

	@Override
	public Usuarios getByString(String s) {
		Usuarios u = null;
		for (Usuarios us : usuarios) {
			if (us.getLogin().equals(s)) {
				u = us;
			}
		}
		return u;
	}

	/*
	 * MÉTODOS ESPECIFICOS DESTA CLASSE
	 */

	public List<Usuarios> lista() {
		return usuarios;
	}

	public Usuarios getLoginSenha(String login, String senha) {
		Usuarios usuario = null;
		for (Usuarios us : usuarios) {
			if (us.getLogin().equals(login) && us.getSenha().equals(senha)) {
				usuario = us;
			}
		}
		return usuario;
	}

	public Usuarios getUsuarios(int id) {
		Usuarios u = null;
		for (Usuarios us : usuarios) {
			if (us.getId() == id) {
				u = us;
			}
		}
		return u;
	}
}
