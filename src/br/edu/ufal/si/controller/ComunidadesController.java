package br.edu.ufal.si.controller;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import br.edu.ufal.si.dao.ComunidadesDao;
import br.edu.ufal.si.dao.MembrosDao;
import br.edu.ufal.si.dao.UsuariosDao;
import br.edu.ufal.si.model.Comunidades;
import br.edu.ufal.si.model.Membros;
import br.edu.ufal.si.model.Usuarios;

public class ComunidadesController implements interController {
	int autoId = 0;

	ComunidadesDao cDao = new ComunidadesDao();
	MembrosDao mDao = new MembrosDao();

	@Override
	public void criar() {
		Comunidades c = new Comunidades(++autoId);
		saveOrUpdate(c);
	}

	@Override
	public void editar(int id) {

	}

	@Override
	public void listar() {
		List<Comunidades> lista = cDao.lista();

		if (!lista.isEmpty()) {
			Util.Msg("Comunidade Criadas ");
			for (Comunidades co : lista) {
				System.out.println(co.getId() + "\t " + co.getNome() + "\t " + co.getDescricao());
			}
		} else {
			Util.Msg("Não existem Comunidades");
		}
	}

	@Override
	public void deletar() {
		Scanner scn = new Scanner(System.in);

		Util.Msg("Informe código da Comunidade que deseja excluir: ");
		try {
			Comunidades c = cDao.getComunidades(scn.nextInt());
			if (c != null) {
				cDao.deleta(c);
			} else {
				Util.Msg("Código de Comunidade inexistente.");
			}
		} catch (InputMismatchException e) {
			Util.Msg("Digte a Código correta da Comunidade !!");
			scn.nextLine();
		}
	}

	public boolean valida(Comunidades c) {
		return (((c.getNome() != null) && !(c.getNome().isEmpty())) && (c.getDescricao() != null)
				&& !(c.getDescricao().isEmpty()));
	}

	public void saveOrUpdate(Comunidades c) {
		Scanner scanner = new Scanner(System.in);

		Util.cabecalhoTela("Comunidade");
		Util.Msg("Descrição: ");
		String descricao = scanner.nextLine();
		if (descricao != null && !descricao.isEmpty()) {
			c.setDescricao(descricao);
		}
		Util.rodapeTela();

		if (valida(c)) {
			c.setAdministrador(true);
			cDao.salva(c);
		} else {
			Util.Msg("Nome precisa ser preenchida!");
			c = null;
			--autoId;
		}
	}

	public void criarComunidade(int idUsuario, UsuariosController ctllUsuarios) {
		Comunidades c = new Comunidades(++autoId);
		Scanner scn = new Scanner(System.in);
		boolean simnao = false;

		Util.cabecalhoTela("Comunidade");
		System.out.println("Nome: ");
		String nome = scn.nextLine();
		if (nome != null && !nome.isEmpty()) {
			c.setNome(nome);
		}

		System.out.println("Descrição: ");
		String descricao = scn.nextLine();
		if (descricao != null && !descricao.isEmpty()) {
			c.setDescricao(descricao);
		}

		Util.rodapeTela();
		if (valida(c)) {
			c.setAdministrador(true);
			int idComunidade = c.getId();
			cDao.salva(c);
			Membros m = new Membros();
			m.setIdUsuario(idUsuario);
			m.setIdComunidade(idComunidade);
			m.setAdministrador(true);
			mDao.salva(m);

			// adicionar o usuario que criou a comunidade
			simnao = Util.MsgSimNao("Deseja adicionar membro (s/n)?");

			if (simnao) {
				Util.limparTela();
				Util.cabecalhoTela("    Comunidade   ");
				Util.Msg(c.getDescricao());
				Util.cabecalhoTela("     Membros     ");

				// adicionar outros usuários
				ctllUsuarios.listarUsuarios(idUsuario);
				while (true) {
					Util.Msg("Selecione o Código do Usuário (ou 0 para retornar):: ");
					try {
						int id = scn.nextInt();
						if (id == 0) break;
						if (ctllUsuarios.uDao.getById(id)!=null) {
							if (mDao.getComunidadeMembro(idComunidade, id) == null) {
								Membros me = new Membros();
								me.setIdUsuario(id);
								me.setIdComunidade(idComunidade);
								me.setAdministrador(false);
								mDao.salva(me);
							} else {
								Util.Msg("Membro já existe nessa comunidade");
							}	
						} else 
							Util.Msg("Código do Usuário não existe no iEAD");
					} catch (InputMismatchException e) {
						Util.Msg("Digte a Código correta do Usuário !!");
						scn.nextLine();
					}
				}
			}
		} else {
			Util.Msg("Alguns campos precisam ser preenchida!");
			c = null;
			--autoId;
		}
	}

	// Participação em uma determinada comunidade
	public void participarComunidades(int idUsuario) throws IOException {
		Scanner scn = new Scanner(System.in);

		if (this.listarComunidadesUsuarios(idUsuario)) {
			while (true) {
				Util.Msg("Digite o Código da comunidade (ou 0 para retornar): ");
				try {
					int idComunidade = scn.nextInt();
					if (idComunidade == 0) break;
					if (mDao.getById(idComunidade) != null) {
						if (mDao.getComunidadeMembro(idComunidade, idUsuario) == null) {
							Membros me = new Membros();
							me.setIdUsuario(idUsuario);
							me.setIdComunidade(idComunidade);
							me.setAdministrador(false);
							mDao.salva(me);
							Util.Msg("Você foi adicionado(a) a comunidade...");
						} 
						else Util.Msg("Membro já existe nessa comunidade...");
					} 
					else Util.Msg("Comunidade não existe !" );
				} catch (InputMismatchException e) {
					scn.nextLine();
					Util.Msg("Digte a Código correto da Comunidade !!");
					Util.Msg("(tecle enter para continuar)");
					System.in.read();
				}
			}
		} else {
			Util.Msg("Nenhuma comunidade disponível...");
			Util.Msg("(tecle enter para continuar)");
			System.in.read();
		}
	}

	// Sair de uma determinada comunidade
	public void sairComunidades(int idUsuario) throws IOException {
		Scanner scn = new Scanner(System.in);
		Membros m = null;

		if (this.listarComunidadesMembrosApenas(idUsuario)) {
			while (true) {
				Util.Msg("Digite o Código da comunidade (0 para sair): ");
				try {
					int idComunidade = scn.nextInt();
					if (idComunidade == 0) break;
					if (mDao.getById(idComunidade) != null) {
						m = mDao.getComunidadeMembro(idComunidade, idUsuario);
						if (m != null) {
							mDao.deleta(m);
							Util.Msg("Você foi retirado(a) da comunidade...");
							m = null;
						} else 
							Util.Msg("Você não é Membro dessa comunidade");
					} else 
						Util.Msg("Comunidade não existe !" );
				} catch (InputMismatchException e) {
					Util.Msg("Digte a Código correta da Comunidade !!");
					scn.nextLine();
					Util.Msg("(tecle enter para continuar)");
					System.in.read();
				}
			}
		} else {
			Util.Msg(" Nenhuma comunidade disponível...");
			Util.Msg("(tecle enter para continuar)");
			System.in.read();
		}
	}

	// Retirar um membro da sua comunidade
	public void removerComunidadesMembros(int idUsuario, UsuariosDao uDao) throws IOException {
		Scanner scn = new Scanner(System.in);
		Membros m = null;

		if (this.listarComunidadesMembrosApenas(idUsuario)) {
			while (true) {
				// Comunidade
				Util.Msg("Selecione a comunidade (0 para sair): ");
				try {
					int idComunidade = scn.nextInt();
					if (idComunidade == 0) break;
					if (mDao.getById(idComunidade) != null) {
						// Membros
						while (true) {
							if (this.listarMembros(idComunidade, uDao)) {
								Util.Msg("Digite o Código do Membro (0 para sair): ");
								try {
									int idMembro = scn.nextInt();
									if (idMembro == 0) break;
									if (uDao.getById(idMembro)!= null) {
										m = mDao.getComunidadeMembro(idComunidade, idMembro);
										if (m != null) {
											mDao.deleta(m);
											m = null;
											Util.Msg("Membro retirardo da comunidade ...");
										}
									} else 
										Util.Msg("Código do Usuário não existe no iEAD");
								} catch (InputMismatchException e) {
									Util.Msg("Digte a Código correta do Membro !!");
									scn.nextLine();
									Util.Msg("(tecle enter para continuar)");
									System.in.read();
								}
							}
						}
					} else 
						Util.Msg("Comunidade não existe !" );
				} catch (InputMismatchException e) {
					scn.nextLine();
					Util.Msg("Digte o código correto da Comunidade !!");
					Util.Msg("(tecle enter para continuar)");
				}
			}
		} else {
			Util.Msg(" Nenhuma comunidade disponível...");
			Util.Msg("(tecle enter para continuar)");
			System.in.read();
		}
	}

	/*
	 * Lista de todos as comunidades
	 */
	public void listarComunidades() throws IOException {
		if (!cDao.lista().isEmpty()) {
			Util.Msg("Comunidade(s)         "
					+ "\n=========================================================");
			// Util.Msg("Id \t Nome \t Descrição");
			for (Comunidades co : cDao.lista()) {
				Util.Msg("(" + co.getId() + ") | Nome: " + co.getNome() + " | Descrição: " + co.getDescricao());
			}
		} else
			Util.Msg("Não existe(m) comunidade(s) cadastrada(s)");
	}

	/*
	 * Todas as comunidades que o usuário logado participa
	 */
	public boolean listarComunidadesMembrosApenas(int idUsuario) throws IOException {
		Comunidades c = null;
		boolean r = false;

		if (!cDao.lista().isEmpty()) {
			Util.Msg("Comunidade(s) que faço parte        "
					+ "\n=========================================================");
			// Util.Msg("Id \t Nome \t Descrição");
			for (Membros m : mDao.lista()) {
				if (m.getIdUsuario() == idUsuario) {
					c = cDao.getById(m.getIdComunidade());
					Util.Msg("(" + m.getIdComunidade() + ") | Nome: " + c.getNome() + " | Descrição: " + c.getDescricao());
					r = true;
				}
			}
		} else
			Util.Msg("Você não está participando de nenhuma comunidade...");

		return r;
	}

	/*
	 * Lista de comunidades que um usuario participal
	 */
	public boolean listarComunidadesUsuarios(int idUsuario) throws IOException {
		Membros m = null;
		boolean r = false;

		if (!cDao.lista().isEmpty()) {
			Util.Msg("Comunidade(s)         " + 
					"\n=====================================================");
			// Util.Msg("Id \t Nome \t Descrição");
			for (Comunidades co : cDao.lista()) {
				m = mDao.getComunidadeMembro(co.getId(), idUsuario);
				if (m == null) {
					Util.Msg("(" + co.getId() + ") | Nome: " + co.getNome() + " | Descrição: " + co.getDescricao());
					r = true;
				}
			}
		} else
			Util.Msg("Não existe(m) comunidade(s) cadastrada(s)");
		return r;
	}

	/*
	 * Listar apenas os membros de uma determinada comunidade
	 */
	public boolean listarMembros(int idComunidade, UsuariosDao uDao) throws IOException {
		Usuarios u = null;
		String adm;
		boolean r = false;

		if (!mDao.lista().isEmpty()) {
			Util.Msg("Membro(s)         " + "\n=========================================================");
			for (Membros me : mDao.lista()) {
				if (me.getIdComunidade() == idComunidade) {
					u = uDao.getById(me.getIdUsuario());
					if (me.isAdministrador()) {
						adm = "(Administrador)";
					} else {
						adm = "";
					}
					Util.Msg(" => " + me.getIdUsuario() + " - " + u.getNome() + adm);
					r = true;
				}
			}
		} else
			Util.Msg("Não existe(m) Membro(s) cadastrado(s)");
		return r;
	}

	/*
	 * Listar
	 */
	public void listarComunidadesMembros(UsuariosDao uDao) throws IOException {
		Usuarios u = null;
		String adm;

		if (!cDao.lista().isEmpty()) {
			Util.Msg("Comunidade(s) e seus Membro(s)" + "\n=========================================================");
			Util.Msg("Obs: Digite 0 para sair");
			Util.Msg("=========================================================");
			for (Comunidades co : cDao.lista()) {
				Util.Msg(co.getNome());
				for (Membros me : mDao.lista()) {
					if (me.getIdComunidade() == co.getId()) {
						u = uDao.getById(me.getIdUsuario());

						if (me.isAdministrador()) {
							adm = "(Administrador)";
						} else {
							adm = "";
						}
						Util.Msg(" => (" + me.getIdUsuario() + ") - " + u.getNome() + adm);
					}
				}
				Util.Msg("---------------------------------");
			}
		} else
			Util.Msg("Não existem mensagens cadastrados ...");

		Util.Msg("(tecle enter para continuar)");
		System.in.read();
	}

	public ComunidadesDao cDao() {
		return cDao;
	}

	public MembrosDao meDao() {
		return mDao;
	}

}
