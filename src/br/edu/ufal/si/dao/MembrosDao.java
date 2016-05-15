package br.edu.ufal.si.dao;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufal.si.model.Membros;

public class MembrosDao implements interDao<Membros> {
	private List<Membros> membros = new ArrayList<Membros>();

	@Override
	public void salva(Membros m) {
		membros.add(m);
	}

	@Override
	public void deleta(Membros m) {
		membros.remove(m);
	}

	@Override
	public Membros getById(int id) {
		Membros m = null;
		for (Membros me : membros) {
			if (me.getIdComunidade() == id) {
				m = me;
			}
		}
		return m;
	}

	@Override
	public Membros getByString(String s) {
		return null;
	}

	public List<Membros> lista() {
		return membros;
	}

	/*
	 * MÉTODOS ESPECÍFICOS
	 */

	public Membros getComunidadeMembro(int idComunidade, int idUsuario) {
		Membros m = null;
		for (Membros me : membros) {
			if (me.getIdComunidade() == idComunidade && me.getIdUsuario() == idUsuario) {
				m = me;
			}
		}
		return m;
	}

	public Membros getComunidadeUsuarios(int idUsuario) {
		Membros m = null;
		for (Membros me : membros) {
			if (me.getIdUsuario() == idUsuario) {
				m = me;
			}
		}
		return m;
	}

}
