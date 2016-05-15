# UFAL - BSI - EAD - 2014.1
# Nome: Carlos Roberto dos Santos Silva
# Data: 
# Objetivo: criar app executado via terminal, parecido com Facebook

package br.edu.ufal.si.controller;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import br.edu.ufal.si.dao.UsuariosDao;
import br.edu.ufal.si.model.Usuarios;

public class UsuariosController implements interController {

	int autoId = 0;

	UsuariosDao uDao = new UsuariosDao();

	@Override
	public void criar() {
		Usuarios u = new Usuarios(++autoId);
		try {
			saveOrUpdate(u);
		} catch (IOException e) {
			Util.Msg("Erro ao Salvar os dados");
		}
	}

	@Override
	public void listar() {
		List<Usuarios> lista = uDao.lista();
		if (!lista.isEmpty()) {
			Util.Msg("|id \t| login \t| nome \t| email                      "
					+ "\n===================================================================================");
			for (Usuarios usuarios : lista) {
				Util.Msg("(" + usuarios.getId() + ") \t " + usuarios.getLogin() + "\t " + usuarios.getNome()
						+ "\t " + usuarios.getEmail());
			}
		} else {
			Util.Msg("Não existem usuarios cadastrados");
		}
	}

	@Override
	public void deletar() {
		Scanner scanner = new Scanner(System.in);
		Util.Msg("Informe código do Usuarios que deseja excluir: ");
		Usuarios u = uDao.getUsuarios(scanner.nextInt());
		if (u != null) {
			uDao.deleta(u);
		} else {
			Util.Msg("Código de Usuarios inexistente.");
		}
	}

	@Override
	public void editar(int id) {
		Scanner scanner = new Scanner(System.in);
		Usuarios u = uDao.getById(id);
		if (u != null) {
			try {
				saveOrUpdatePerfil(u);
			} catch (IOException e) {
				Util.Msg("Erro no iEAD, envie a mansagem abaixo para o desenvolvedor");
				e.printStackTrace();
			}
			
		} else {
			Util.Msg("Código de Usuarios inexistente.");
		}
	}

	/*
	 * MÉTODOS ESPECIFICOS DESTA CLASSE
	 */

	public boolean validaLogin(Usuarios u) {
		return ((u.getLogin() != null) && (u.getSenha() != null) && !(u.getLogin().isEmpty())
				&& !(u.getSenha().isEmpty()));
	}

	public boolean validaUsuarios(Usuarios u) {
		return ((u.getNome() != null) && (u.getEmail() != null) && !(u.getEmail().isEmpty())
				&& !(u.getNome().isEmpty()));
	}

	public boolean listarUsuarios(int idUsuario) {
		List<Usuarios> lista = uDao.lista();
		boolean t = false;
		if (!lista.isEmpty()) {
			Util.Msg("Usuário(s) do iEAD" + 
					"\n========================================================");
			for (Usuarios usuarios : lista) {
				if (usuarios.getId() != idUsuario) {
					Util.Msg("(" + usuarios.getId() + ") |  Nome: " + usuarios.getNome());
					t = true;
				}
			}
		} else {
			Util.Msg("Não existem usuarios cadastrados");
		}
		return t;
	}

	public Usuarios listarParaAmigos(int id) {
		List<Usuarios> lista = uDao.lista();
		Usuarios u = null;

		if (!lista.isEmpty()) {
			Util.Msg("Usuário(s) do iEAD " + 
					"\n========================================================");
			for (Usuarios usuarios : lista) {
				if (usuarios.getId() != id) {
					System.out
							.println("(" + usuarios.getId() + ") | Nome: " + usuarios.getNome() + " | e-mail: " + usuarios.getEmail());
					u = usuarios;
				}
			}
		} else {
			Util.Msg("Não existem Usuarios cadastrados");
		}
		return u;
	}

	public void saveOrUpdate(Usuarios u) throws IOException {
		Scanner scanner = new Scanner(System.in);

		Util.cabecalhoTela("Contas");

		Util.Msg("Login: ");
		String login = scanner.nextLine();
		if (login != null && !login.isEmpty()) {
			u.setLogin(login);
		}
		Util.Msg("Senha: ");
		String senha = scanner.nextLine();
		if (senha != null && !senha.isEmpty()) {
			u.setSenha(senha);
		}

		Util.Msg("Nome: ");
		String nome = scanner.nextLine();
		if (nome != null && !nome.isEmpty()) {
			u.setNome(nome);
		}
		Util.rodapeTela();

		if (validaLogin(u)) {
			if (uDao.lista().isEmpty()) {
				uDao.salva(u);
				Util.Msg("Conta adicionada ...");
				Util.Msg("(tecle enter para continuar)");
				System.in.read();
			} else {
				if (uDao.getByString(u.getLogin()) != null) {
					Util.Msg("Login já existe no iEAD...");
					Util.Msg("(tecle enter para continuar)");
					u = null;
					--autoId;
					System.in.read();
				} else {
					uDao.salva(u);
					Util.Msg("Conta adicionada ...");
					Util.Msg("(tecle enter para continuar)");
					System.in.read();
				}
			}
		} else {
			Util.Msg("Login e Senha precisam ser preenchidos!");
			u = null;
			--autoId;
		}

	}

	public void saveOrUpdatePerfil(Usuarios u) throws IOException {
		Scanner scanner = new Scanner(System.in);
		Util.cabecalhoTela("Perfil da Conta");
		Util.Msg("Id...: " + u.getId());
		Util.Msg("Login: " + u.getLogin());
		Util.Msg("Senha: *******");
		Util.Msg("Nome.: ");
		String nome = scanner.nextLine();
		u.setNome(nome);
		Util.Msg("email: ");
		String email = scanner.nextLine();
		
		u.setNome(nome);
		u.setEmail(email);
		
		if (validaUsuarios(u)) {
			uDao.salva(u);
			Util.Msg("Conta adicionada atualizada com sucesso ...");
			Util.Msg("(tecle enter para continuar)");
			System.in.read();
			
		} else {
			Util.Msg("Nome ou email precisam ser preenchidos!");
			u = null;
			--autoId;
		}
	}

	public Usuarios LoginAcesso() {
		Scanner scn = new Scanner(System.in);

		Util.cabecalhoTela(" * ACESSO *");
		Util.Msg("Login: ");
		String login = scn.nextLine();
		if (login == null && login.isEmpty())
			Util.Msg("Login precisam ser preenchidos!");

		Util.Msg("Senha: ");
		String senha = scn.nextLine();

		Util.rodapeTela();

		if (senha == null && senha.isEmpty()) 
			Util.Msg("Senha precisam ser preenchidas!");

		return uDao.getLoginSenha(login, senha);
	}

	public UsuariosDao uDao() {
		return uDao;
	}
}
