package br.edu.ufal.si.dao;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufal.si.controller.Util;
import br.edu.ufal.si.model.Mensagens;

public class MensagensDao implements interDao<Mensagens> {

	private List<Mensagens> mensagens = new ArrayList<Mensagens>();

	@Override
	public void salva(Mensagens m) {
		mensagens.add(m);
		Util.Msg("Mensagem enviada...");
	}

	@Override
	public void deleta(Mensagens m) {
		mensagens.remove(m);
		Util.Msg("Apagando mensagem ...");
	}

	@Override
	public Mensagens getById(int id) {
		Mensagens m = null;
		for (Mensagens me : mensagens) {
			if (me.getId() == id) {
				m = me;
			}
		}
		return m;
	}

	@Override
	public Mensagens getByString(String s) {
		Mensagens m = null;
		for (Mensagens me : mensagens) {
			if (me.getMensagem().equals(s)) {
				m = me;
			}
		}
		return m;
	}

	public List<Mensagens> lista() {
		return mensagens;
	}
}
