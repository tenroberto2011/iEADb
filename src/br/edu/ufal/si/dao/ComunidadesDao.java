package br.edu.ufal.si.dao;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufal.si.controller.Util;
import br.edu.ufal.si.model.Comunidades;
import br.edu.ufal.si.model.Membros;

public class ComunidadesDao implements interDao<Comunidades> {

	private List<Comunidades> comunidades = new ArrayList<Comunidades>();
	private List<Membros> membros = new ArrayList<Membros>();

	@Override
	public void salva(Comunidades c) {
		comunidades.add(c);
		Util.Msg("Comunidade criada ...");
	}

	@Override
	public void deleta(Comunidades c) {

		for (Membros me : membros) {
			if (me.getIdComunidade() == c.getId()) {
				membros.remove(me.getIdComunidade());
			}
		}
		comunidades.remove(c);
		Util.Msg("Apagando comunidade ...");
	}

	@Override
	public Comunidades getById(int id) {
		Comunidades c = null;
		for (Comunidades co : comunidades) {
			if (co.getId() == id) {
				c = co;
			}
		}
		return c;
	}

	@Override
	public Comunidades getByString(String s) {
		Comunidades c = null;
		for (Comunidades co : comunidades) {
			if (co.getDescricao().equals(s)) {
				c = co;
			}
		}
		return c;
	}

	/*
	 * MÉTODOS ESPECIFICOS
	 */
	public Comunidades getComunidades(int id) {
		Comunidades c = null;
		for (Comunidades co : comunidades) {
			if (co.getId() == id) {
				c = co;
			}
		}
		return c;
	}

	public Comunidades getComunidadesMembros(int idUsuario) {
		Comunidades c = null;
		for (Comunidades co : comunidades) {
			if (co.getIdUsuario() == idUsuario) {
				c = co;
			}
		}
		return c;
	}

	public List<Comunidades> lista() {
		return comunidades;
	}
}
