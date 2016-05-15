# UFAL - BSI - EAD - 2014.1
# Nome: Carlos Roberto dos Santos Silva
# Data: 
# Objetivo: criar app executado via terminal, parecido com Facebook

package br.edu.ufal.si.main;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import br.edu.ufal.si.controller.AmigosController;
import br.edu.ufal.si.controller.ComunidadesController;
import br.edu.ufal.si.controller.MensagensController;
import br.edu.ufal.si.controller.PerfilController;
import br.edu.ufal.si.controller.UsuariosController;
import br.edu.ufal.si.controller.Util;
import br.edu.ufal.si.model.Usuarios;

public class Principal {

	UsuariosController ctllUsuarios = new UsuariosController();
	AmigosController ctllAmigos = new AmigosController();
	MensagensController ctllMensagens = new MensagensController();
	ComunidadesController ctllComunidades = new ComunidadesController();
	PerfilController ctllPerfil = new PerfilController();

	public Usuarios loginAtual = null;

	static Scanner scanTecla = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		Principal principal = new Principal();
		principal.acessoLogin(principal);
	}

	public void acessoLogin(Principal principal) throws IOException {
		int opcao;
		while (true) {
			principal.menuLogin();
			try {
				opcao = scanTecla.nextInt();
				principal.opcaoLogin(principal, opcao);
			} catch (InputMismatchException e) {
				Util.Msg("Digte a Opção correta !!");
				scanTecla.nextLine();
			}
		}
	}

	public void menuLogin() {
		Util.limparTela();
		Util.cabecalhoTela("iEAD");
		Util.Msg("(1) Criar Conta");
		Util.Msg("(2) Acessar");
		Util.Msg("(3) Sair");
		System.out.print("Informe a sua opção: ");
	}

	public void opcaoLogin(Principal principal, int opcao) throws IOException {
		switch (opcao) {
		case 1:
			Util.limparTela();
			ctllUsuarios.criar();
			Util.limparTela();
			break;
		case 2:
			Util.limparTela();
			loginAtual = ctllUsuarios.LoginAcesso();
			if (loginAtual != null)
				this.acessoPrincipal(principal);
			else {
				Util.Msg("\nConta ainda não existe ...");
				System.in.read();
			}
			break;
		case 3:
			Util.limparTela();
			Util.Msg("Muito Obrigado por usar o iEAD ");
			System.exit(0);
		default:
			Util.Msg("Opção inválida");
			break;
		}
	}

	public void acessoPrincipal(Principal principal) throws IOException {
		int opcao;
		while (true) {
			principal.menuPrincipal();
			try {
				opcao = scanTecla.nextInt();
				if (principal.opcaoPrincipal(principal, opcao)) {
					this.acessoLogin(principal);
					break;
				}
			} catch (InputMismatchException e) {
				Util.Msg("Digte a Opção correta do menu Principal!!");
				scanTecla.nextLine();
			}
		}
	}

	public void menuPrincipal() {
		Util.limparTela();
		Util.Msg("|:. TELA PRINCIPAL .:|");
		Util.loginAtualTela(loginAtual.getLogin());
		Util.Msg("(1) Editar Pefil");
		Util.Msg("(2) Amigos");
		Util.Msg("(3) Mensagens");
		Util.Msg("(4) Comunidade");
		Util.Msg("(5) Informações do Perfil");
		Util.Msg("(6) Sair do iEAD");
		System.out.print("Informe a sua opção: ");
	}

	public boolean opcaoPrincipal(Principal principal, int opcao) throws IOException {
		boolean ret = false;
		switch (opcao) {
		case 1:
			Util.limparTela();
			ctllUsuarios.editar(loginAtual.getId());
			break;
		case 2:
			Util.limparTela();
			if (ctllUsuarios.uDao().lista().size() > 1)
				this.acessoAmigos(principal);
			else {
				Util.Msg("Não existe(m) Usuário(s) para se tornarem amigos ...");
				Util.Msg("(tecle enter para continuar)");
				System.in.read();
			}
			break;
		case 3:
			Util.limparTela();
			if (ctllUsuarios.uDao().lista().size() > 1)
				this.acessoMensagens(principal);
			else {
				Util.Msg("Não existe(m) Usuário(s) para se enviar mensagem ...");
				Util.Msg("(tecle enter para continuar)");
				System.in.read();
			}
			break;
		case 4:
			Util.limparTela();
			if (ctllUsuarios.uDao().lista().size() > 1)
				this.acessoComunidades(principal);
			else {
				Util.Msg("Não existe(m) Usuário(s) para se criar uma comunidade ...");
				Util.Msg("(tecle enter para continuar)");
				System.in.read();
			}
			break;
		case 5:
			Util.limparTela();
			ctllPerfil.informacoesPerfil(loginAtual.getId(), ctllUsuarios.uDao(), ctllAmigos.aDao(),
					ctllMensagens.mDao(), ctllComunidades.cDao(), ctllComunidades.meDao());
			break;
		case 6:
			ret = true;
			break;
		default:
			Util.Msg("Opção inválida");
			System.in.read();
			break;
		}
		return ret;
	}

	public void acessoAmigos(Principal principal) throws IOException {
		int opcao;
		while (true) {
			principal.menuAmigos();
			try {
				opcao = scanTecla.nextInt();
				if (principal.opcaoAmigos(opcao)) {
					this.acessoPrincipal(principal);
					break;
				}
			} catch (InputMismatchException e) {
				Util.Msg("Digte a Opção correta do menu de Amigos !!");
				scanTecla.nextLine();
			}
		}
	}

	public void menuAmigos() {
		Util.limparTela();
		System.out.println("Opções de Amigos");
		Util.loginAtualTela(loginAtual.getLogin());
		Util.Msg("(1) Adicionar");
		Util.Msg("(2) Amigos");
		Util.Msg("(3) Solicitações");
		Util.Msg("(4) Retornar");
		System.out.print("Informe a sua opção: ");
	}

	public boolean opcaoAmigos(int opcao) throws IOException {
		boolean ret = false;
		boolean t = false;
		Usuarios us = null;

		switch (opcao) {
		case 1:
			Util.limparTela();
			t = ctllAmigos.listaAdicionarAmigos(loginAtual.getId(), ctllUsuarios.uDao());
			if (t)
				ctllAmigos.acrescentar(loginAtual.getId(), ctllUsuarios.uDao());
			else {
				Util.limparTela();
				Util.Msg("Nenhum amigo para ser adicionado...");
				Util.Msg("Verifique sua(s) solicitação(ões)...");
				Util.Msg("(tecle enter para continuar)");
				System.in.read();
			}
			break;
		case 2:
			Util.limparTela();
			ctllAmigos.listarAmigos(loginAtual.getId(), ctllUsuarios.uDao());
			Util.Msg("(tecle enter para continuar)");
			System.in.read();
			break;
		case 3:
			Util.limparTela();
			t = ctllAmigos.listaSolicitacaoAmigos(loginAtual.getId(), ctllUsuarios.uDao());
			if (t) {
				ctllAmigos.confirmarSolicitacao(loginAtual.getId(), ctllUsuarios.uDao());
			} else {
				Util.Msg("Não existe(m) solicitação(ões) de amizade...");
				Util.Msg("(tecle enter para continuar)");
				System.in.read();
			}
			break;
		case 4:
			ret = true;
			break;
		default:
			Util.Msg("Opção inválida");
			break;
		}
		return ret;
	}

	public void acessoMensagens(Principal principal) throws IOException {
		int opcao;
		while (true) {
			principal.menuMensagens();
			try {
				opcao = scanTecla.nextInt();
				if (principal.opcaoMensagens(opcao)) {
					this.acessoPrincipal(principal);
					break;
				}
			} catch (InputMismatchException e) {
				Util.Msg("Digte a Opção correta do menu de Mensagens !!");
				scanTecla.nextLine();
			}
		}
	}

	public void menuMensagens() {
		Util.limparTela();
		System.out.println("Opções de Mensagens");

		Util.loginAtualTela(loginAtual.getLogin());
		Util.Msg("(1) Enviar");
		Util.Msg("(2) Verificar");
		Util.Msg("(3) Retornar");
		System.out.print("Informe a sua opção: ");
	}

	public boolean opcaoMensagens(int opcao) throws IOException {
		boolean ret = false;
		boolean t = false;

		switch (opcao) {
		case 1:
			Util.limparTela();
			t = ctllUsuarios.listarUsuarios(loginAtual.getId());
			if (t)
				ctllMensagens.enviarMensagensUsuarios(loginAtual.getId(), ctllUsuarios.uDao());
			break;
		case 2:
			Util.limparTela();
			ctllMensagens.listarMensagensUsuarios(loginAtual.getId(), ctllMensagens.mDao(), ctllUsuarios.uDao());
			Util.Msg("Precione qualquer tecla para retornar ...");
			System.in.read();
			break;
		case 3:
			ret = true;
			break;
		default:
			Util.Msg("Opção inválida");
			break;
		}
		return ret;
	}

	public void acessoComunidades(Principal principal) throws IOException {
		int opcao;
		while (true) {
			principal.menuComunidades();
			try {
				opcao = scanTecla.nextInt();
				if (principal.opcaoComunidades(opcao)) {
					this.acessoPrincipal(principal);
					break;
				}
			} catch (InputMismatchException e) {
				Util.Msg("Digte a Opção correta do menu de Comunidades !!");
				scanTecla.nextLine();
			}
		}
	}

	public void menuComunidades() {
		Util.limparTela();
		Util.Msg("Opções de Comunidadaes");
		Util.loginAtualTela(loginAtual.getLogin());
		Util.Msg("(1) Criar");
		Util.Msg("(2) Participar");
		Util.Msg("(3) Sair");
		Util.Msg("(4) Remover Membro(s)");
		Util.Msg("(5) Listar Comunidades x Membro(s)");
		Util.Msg("(6) Retornar");
		System.out.print("Informe a sua opção: ");
	}

	public boolean opcaoComunidades(int opcao) throws IOException {
		boolean ret = false;
		boolean t = false;
		Usuarios us = null;

		switch (opcao) {
		case 1:
			Util.limparTela();
			ctllComunidades.criarComunidade(loginAtual.getId(), ctllUsuarios);
			break;
		case 2:
			Util.limparTela();
			ctllComunidades.participarComunidades(loginAtual.getId());
			break;
		case 3:
			Util.limparTela();
			ctllComunidades.sairComunidades(loginAtual.getId());
			break;
		case 4:
			Util.limparTela();
			ctllComunidades.removerComunidadesMembros(loginAtual.getId(), ctllUsuarios.uDao());
			break;
		case 5:
			Util.limparTela();
			ctllComunidades.listarComunidadesMembros(ctllUsuarios.uDao());
			break;
		case 6:
			ret = true;
			break;
		default:
			Util.Msg("Opção inválida");
			break;
		}
		return ret;
	}
}
