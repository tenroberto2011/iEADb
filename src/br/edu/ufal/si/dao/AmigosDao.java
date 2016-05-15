# UFAL - BSI - EAD - 2014.1
# Nome: Carlos Roberto dos Santos Silva
# Data: 
# Objetivo: criar app executado via terminal, parecido com Facebook

package br.edu.ufal.si.dao;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufal.si.model.Amigos;

public class AmigosDao implements interDao<Amigos> {

	private List<Amigos> amigos = new ArrayList<Amigos>();

	@Override
	public void salva(Amigos a) {
		if (amigos.contains(a)) {
			// System.out.println("Amigo "+
			// " atualizado com sucesso!");
		} else {
			amigos.add(a);
			// System.out.println("Amigo salvo com sucesso!");
		}
	}

	@Override
	public void deleta(Amigos a) {
		amigos.remove(a);
		System.out.println("Apagando Amigo ...");
		a = null;
		System.out.println("Amigo removido com sucesso!");
	}

	@Override
	public Amigos getById(int id) {
		Amigos a = null;
		for (Amigos am : amigos) {
			if (am.getId() == id) {
				a = am;
			}
		}
		return a;
	}

	@Override
	public Amigos getByString(String s) {
		Amigos a = null;
		for (Amigos am : amigos) {
			if (am.getMensagem().equals(s)) {
				a = am;
			}
		}
		return a;
	}

	public List<Amigos> lista() {
		return amigos;
	}

	public Amigos getByIdUsuario(int idUsuario) {
		Amigos a = null;
		for (Amigos am : amigos) {
			if (am.getIdUsuario() == idUsuario) {
				a = am;
			}
		}
		return a;
	}

	public Amigos getByIdUsuarioAmigo(int idUsuarioAmigo) {
		Amigos a = null;
		for (Amigos am : amigos) {
			if (am.getIdUsuarioAmigo() == idUsuarioAmigo) {
				a = am;
			}
		}
		return a;
	}

	public Amigos getByAmigos(int idUsuario, int idUsuarioAmigo) {
		Amigos a = null;
		for (Amigos am : amigos) {
			if ((am.getIdUsuario() == idUsuario && am.getIdUsuarioAmigo() == idUsuarioAmigo)
					|| (am.getIdUsuario() == idUsuarioAmigo && am.getIdUsuarioAmigo() == idUsuario)) {
				a = am;
			}
		}
		return a;
	}

	public Amigos getByAmigosAtual(int idUsuario) {
		Amigos a = null;
		for (Amigos am : amigos) {
			if ((am.getIdUsuario() == idUsuario && am.getIdUsuarioAmigo() != idUsuario)
					|| (am.getIdUsuario() != idUsuario && am.getIdUsuarioAmigo() == idUsuario)) {
				if (am.getAceito())
					a = am;
			}
		}
		return a;
	}
}
