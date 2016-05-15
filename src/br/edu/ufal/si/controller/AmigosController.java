package br.edu.ufal.si.controller;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import br.edu.ufal.si.dao.AmigosDao;
import br.edu.ufal.si.dao.UsuariosDao;
import br.edu.ufal.si.model.Amigos;
import br.edu.ufal.si.model.Usuarios;

public class AmigosController implements interController {
    int autoId = 1; 
    
    AmigosDao aDao = new AmigosDao();
    
    @Override
    public void criar() {
	Amigos a = new Amigos(++autoId);
    }
     
    @Override
    public void editar(int id) {
    	
    }

    @Override
    public void listar() {
	List<Amigos> lista = aDao.lista();
	if (!lista.isEmpty()) {
	    Util.Msg("|Código \t| IdUsuario \t| IdUsuarioAmigo \t|                         " +
	        "\n===================================================================================");
	    for (Amigos amigos : lista) {
	        Util.Msg("("+amigos.getId() + ") | (" +
	            amigos.getIdUsuario() + ") | (" + amigos.getIdUsuarioAmigo());
	    }
	}
	else 
	    Util.Msg("Não existem Amigox cadastrados");
    }

    @Override
    public void deletar() {
    	
    }
    
    /*
     * MÉTODOS ESPECIFICOS DESTA CLASSE
     */
    
    public boolean listaAdicionarAmigos(int idUsuario, UsuariosDao uDao) {
		List<Amigos> lista = aDao.lista();
		boolean t = false;
		Util.Msg("Usuários da rede iEAD esperando pela sua Amizade"+
			        "\n=========================================================");
		
		if (lista.isEmpty()) {
			// lista de amizade ainda vazia
			for (Usuarios u : uDao.lista()) {
				if (u.getId()!=idUsuario) {
					System.out.println("("+u.getId()+") | Nome: " + u.getNome());
					t=true;
				} 
			}
		}
		else {
			// Os que não foram selecionados para amizade
			for (Usuarios u : uDao.lista()) {
				if (u.getId()!=idUsuario) {
					if (aDao.getByAmigos(u.getId(), idUsuario)==null){
						System.out.println("("+u.getId()+") | Nome: " + u.getNome());
						t=true;
					}
				}
			}
		}
		if (!t) Util.Msg("Por favor verifique sua lista de solicitação(ões).. ");
		
		return t;
    }
    
    public void acrescentar(int idUsuario, UsuariosDao uDao) throws IOException{
		Scanner scanner = new Scanner(System.in);
		while (true) { 
			Util.Msg("Digite o código do Usuário (ou 0 para retornar): ");
			try {
				int idUsuarioAmigo = scanner.nextInt();
				if (idUsuarioAmigo == 0) break;
				if (uDao.getById(idUsuarioAmigo)!=null) {
					if (idUsuarioAmigo != idUsuario) {
						if (aDao.getByAmigos(idUsuario,idUsuarioAmigo) == null) {
							Amigos a = new Amigos(autoId++);
							a.setIdUsuario(idUsuario);
							a.setIdUsuarioAmigo(idUsuarioAmigo);
							a.setMensagem("Solicitação de amizade de ");
							a.setAceito(false);
							aDao.salva(a);
							Util.Msg("Solicitação de amizade enviada ...");
						}
						else Util.Msg("Amigo já adicionado");
					} else {
						Util.Msg("Você não pode ser seu próprio amigo !!");
					}
				} else 
					Util.Msg("Código do Usuário Inexistente !!");
			} catch (InputMismatchException e) {
				scanner.nextLine();
				Util.Msg("Digte o código correto do Usuário!!");
				Util.Msg("(tecle enter para continuar)");
			}	
		}
    }

    
    public void listarAmigos(int idUsuario, UsuariosDao uDao) {
		List<Amigos> lista = aDao.lista();
		Usuarios u = null;
		
		if (!lista.isEmpty()) {
		    Util.Msg("Amigo(s)                "+
		        "\n=========================================================");
		    for (Amigos amigos : lista) {
		    	if (amigos.getIdUsuario()==idUsuario) {
		    	     u = uDao.getById(amigos.getIdUsuarioAmigo());
		    	     if (amigos.getAceito()) 
		    	    	 Util.Msg(u.getNome()+" aceitou sua solicitação de amizade!! ");
		    	     else 
		    	    	 Util.Msg("Solicitação de amizada enviada para " + u.getNome());
		    	} else {
		    	     if (amigos.getIdUsuarioAmigo()==idUsuario) {
			          u = uDao.getById(amigos.getIdUsuario());
			    	  if (amigos.getAceito()) 
			    	      Util.Msg("Você aceitou " + u.getNome() + " com seu amigo... ");
			    	  else 
			    	      Util.Msg(u.getNome() + " enviou solcitação de amizade para você ...");
		    	     }
		    	}
		    }
		}
		else 
		    Util.Msg("Não existem Amigos cadastrados");
    }
    
    public boolean listaSolicitacaoAmigos(int idUsuario, UsuariosDao uDao) {
		List<Amigos> lista = aDao.lista();
		Usuarios u = null;
		boolean t = false;
		
		if (!lista.isEmpty()) {
			Util.Msg("Solicitação(ões) de Amizade(s)                 "+
				"\n=========================================================");
			for (Amigos amigos : aDao.lista()) {
				if (amigos.getIdUsuario()!=idUsuario) {
					if ((amigos.getIdUsuarioAmigo()==idUsuario)) {
						if (amigos.getAceito()==false) {
							u = uDao.getById(amigos.getIdUsuario());
							if (u!=null){
								Util.Msg("("+amigos.getId() + ") " + amigos.getMensagem()+u.getNome());
								t=true;
							}
			    		     }	
					}
				}
			}
		}
		else 
		    Util.Msg("Não existem Amigos cadastrados");
	
	return t;
    }
    
    public void confirmarSolicitacao(int idUsuario, UsuariosDao uDao) throws IOException{
		Scanner scanner = new Scanner(System.in);
		Amigos a = null;
	 	while (true) {
	 		Util.Msg("Digite o Código da solicitação (ou 0 para retornar): ");
	 		try {
	 			int idUsuarioAmigo = scanner.nextInt();
	 			if (idUsuarioAmigo == 0) break;
	 			if (uDao.getById(idUsuarioAmigo)!=null) {
					a = aDao.getById(idUsuarioAmigo);
					if (a != null) {
						a.setAceito(true);
						aDao.salva(a);
						Util.Msg("Solicitação de amizada confirmada ...");
					}
					else {
						Util.Msg("Amizade já confirmada ...");
					}
	 			} else {
	 				Util.Msg("Código do Usuário Inexistente !!");
	 			}
	     
	 		} catch (InputMismatchException e) {
	 			scanner.nextLine();
	 			Util.Msg("Digte o Código correto da Solicitação !!");
	 			Util.Msg("(tecle enter para continuar)");
				System.in.read();
	 		}
	 	}
   }
    
    public AmigosDao aDao(){
    	return aDao;
    }
}
