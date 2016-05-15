package br.edu.ufal.si.controller;

import java.io.IOException;

import br.edu.ufal.si.dao.AmigosDao;
import br.edu.ufal.si.dao.ComunidadesDao;
import br.edu.ufal.si.dao.MembrosDao;
import br.edu.ufal.si.dao.MensagensDao;
import br.edu.ufal.si.dao.UsuariosDao;
import br.edu.ufal.si.model.Amigos;
import br.edu.ufal.si.model.Comunidades;
import br.edu.ufal.si.model.Membros;
import br.edu.ufal.si.model.Mensagens;
import br.edu.ufal.si.model.Usuarios;

public class PerfilController {
	public void informacoesPerfil(int idUsuario, UsuariosDao uDao, AmigosDao aDao, MensagensDao mDao,
			ComunidadesDao cDao, MembrosDao meDao) throws IOException {
		Usuarios u = null;
		Amigos a = null;
		Mensagens m = null;
		Comunidades c = null;
		Membros me = null;
		String nomeAmigo = "";

		u = uDao.getById(idUsuario);

		Util.Msg("** PERFIL DO USUÁRIO **");
		Util.Msg("Nome.......: " + u.getNome());
		if (u.getEmail() != null)
			Util.Msg("e-mail.....: " + u.getEmail());
		else
			Util.Msg("e-mail.....: nao informado");
		Util.Msg("Login......: " + u.getLogin());
		Util.Msg("Senha......: *******");

		Util.Msg("** COMUNIDADE(S) **");
		if (!meDao.lista().isEmpty()) {
			for (Membros mem : meDao.lista()) {
				if (mem.getIdUsuario() == idUsuario) {
					c = cDao.getComunidades(mem.getIdComunidade());
					if (c != null)
						Util.Msg("	=> " + c.getNome() + " - " + c.getDescricao());
				}

			}
		} else
			Util.Msg("Nenhuma comunidade...");

		Util.Msg("** AMIGO(S)	 **");
		if (!aDao.lista().isEmpty()) {
			for (Amigos am : aDao.lista()) {
				if (am.getIdUsuario() == idUsuario || am.getIdUsuarioAmigo() == idUsuario) {
					if (am.getAceito()) {
						if (am.getIdUsuario() != idUsuario) {
							Usuarios us = null;
							us = uDao.getById(am.getIdUsuario());
							nomeAmigo = us.getNome();
						}
						if (am.getIdUsuarioAmigo() != idUsuario) {
							Usuarios us = null;
							us = uDao.getById(am.getIdUsuarioAmigo());
							nomeAmigo = us.getNome();
						}
						Util.Msg("	=> " + nomeAmigo);
					}
				}

			}
		} else
			Util.Msg("Nenhum amigo...");

		Util.Msg("** MENSAGENS **");
		if (!mDao.lista().isEmpty()) {
			for (Mensagens men : mDao.lista()) {
				if (men.getIdUsuario() == idUsuario || men.getIdUsuarioAmigo() == idUsuario) {
					if (men.getIdUsuario() != idUsuario) {
						Usuarios us = null;
						us = uDao.getById(men.getIdUsuario());
						nomeAmigo = "	RECEBIDA <=: " + us.getNome();
					}
					if (men.getIdUsuarioAmigo() != idUsuario) {
						Usuarios us = null;
						us = uDao.getById(men.getIdUsuarioAmigo());
						nomeAmigo = "	ENVIADA =>: " + us.getNome();
					}
					Util.Msg("=> " + nomeAmigo + ": " + men.getMensagem());
				}

			}
		} else
			Util.Msg("Nenhuma mensagem...");

		Util.Msg("\n(tecle enter para continuar)");
		System.in.read();

	}
}
