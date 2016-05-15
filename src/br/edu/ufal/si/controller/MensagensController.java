# UFAL - BSI - EAD - 2014.1
# Nome: Carlos Roberto dos Santos Silva
# Data: 
# Objetivo: criar app executado via terminal, parecido com Facebook

package br.edu.ufal.si.controller;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import br.edu.ufal.si.dao.AmigosDao;
import br.edu.ufal.si.dao.MensagensDao;
import br.edu.ufal.si.dao.UsuariosDao;
import br.edu.ufal.si.model.Amigos;
import br.edu.ufal.si.model.Mensagens;
import br.edu.ufal.si.model.Usuarios;

public class MensagensController implements interController{
    int autoId = 0;

    MensagensDao mDao = new MensagensDao();
	
    @Override
    public void criar() {
    	Mensagens m = new Mensagens(++autoId);
    }

    @Override
    public void editar(int id) {
    	
    }

    @Override
    public void listar() {
    	List<Mensagens> lista = mDao.lista();
    	
        if (!lista.isEmpty()) {
            Util.Msg("Mensagens");
            for (Mensagens me : lista) {
                System.out.println(me.getId() + "\t " +
                    me.getIdUsuario() + "\t " + me.getIdUsuarioAmigo() +
                        me.getMensagem());
            }
        }
        else {
            System.out.println("Não existem mensagens");
        }
    }

    @Override
    public void deletar() {	
    }
    
    /*
    *   MÉTODOS ESPECIFICOS DESTA CLASSE
    */
    public boolean valida(Mensagens m) {
    	return ((m.getIdUsuarioAmigo() != 0) && (m.getMensagem() != null) && !(m.getMensagem().isEmpty()));
    }
	
    public void listarAmigos(int idUsuario, AmigosDao aDao, UsuariosDao uDao) {
    	Usuarios u = null;
	
		if (!aDao.lista().isEmpty()) {
		    System.out.println("|Amigo(s)                "+
		        "\n=========================================================");
		    for (Amigos amigos : aDao.lista()) {
		    	if (amigos.getIdUsuario()==idUsuario) {
		    		u = uDao.getById(amigos.getIdUsuarioAmigo());
		    		if (amigos.getAceito()) 
		    			System.out.println("("+u.getId()+") "+ u.getNome());
		    	} else {
		    		if (amigos.getIdUsuarioAmigo()==idUsuario) {
			    		u = uDao.getById(amigos.getIdUsuario());
			    		if (amigos.getAceito()) 
			    			System.out.println("("+u.getId()+") "+ u.getNome());
		    		}
		    	}
		    }
		}
		else 
		    System.out.println("Não existem Amigos cadastrados");
    }
    
    public void enviarMensagensAmigos(int idUsuario) throws IOException{
    	Scanner scn = new Scanner(System.in);
	 
		while (true) {
			Util.Msg("Digite o Códigodo do Amigo (ou 0 para retornar): ");
			try {
				int idUsuarioAmigo = scn.nextInt();
				if (idUsuarioAmigo==0) break;
				
				Util.Msg("Digite uma Mensagem: ");
				String msg = scn.nextLine();
				Mensagens m = new Mensagens(autoId++);
				m.setIdUsuario(idUsuario);
				m.setIdUsuarioAmigo(idUsuarioAmigo);
				m.setMensagem(msg);
				mDao.salva(m);
				Util.Msg("(tecle enter para continuar)");
				System.in.read();
			} catch (InputMismatchException e) {
				scn.nextLine();  
	 			Util.Msg("Digte o Código correto do Amigo !!");
	 			Util.Msg("(tecle enter para continuar)");
				System.in.read();
	 		} catch (NumberFormatException e){
	 			scn.nextLine();  
	 			Util.Msg("Digte o Código correto do Amigo !!");
	 			Util.Msg("(tecle enter para continuar)");
				System.in.read();
	 		} 
		}
    }
    
    public void enviarMensagensUsuarios(int idUsuario, UsuariosDao uDao) throws IOException{
		Scanner scn = new Scanner(System.in);
		
		while (true) {
			Util.Msg("Digite o Código do Usuário (ou 0 para retornar):");
			try {
				String sIdUsuarioAmigo = scn.nextLine();
				if (!sIdUsuarioAmigo.isEmpty()) {
					int idUsuarioAmigo = Integer.parseInt(sIdUsuarioAmigo);
					if (idUsuarioAmigo!=idUsuario) {
						if (idUsuarioAmigo==0) break;
						if (uDao.getById(idUsuarioAmigo)!=null) {
							if (idUsuarioAmigo!=0) {
								Mensagens m = new Mensagens(autoId++);
								m.setIdUsuario(idUsuario);
								m.setIdUsuarioAmigo(idUsuarioAmigo);
								Util.Msg("Digite a Mensagem: ");
								String msg = scn.nextLine();
								if (!msg.isEmpty()) m.setMensagem(msg);
								if (valida(m)) 
								     mDao.salva(m);
								else {
								     Util.Msg("Informações precisam ser preenchidas!");
								     m = null;
								     --autoId;
								}
							}	
						} else 
							Util.Msg("Código do Usuário não existe no iEAD !!");	
					} else
						Util.Msg("Não podes enviar mensagem para você mesmo !!");
				} else 
					Util.Msg("Código do Usuário não informado !!");
					
			} catch (InputMismatchException e) {
				scn.nextLine();  
	 			Util.Msg("Digte o Código correto do Amigo !!");
	 			Util.Msg("(tecle enter para continuar)");
				System.in.read();
	 		} catch (NumberFormatException e){
	 			scn.nextLine();  
	 			Util.Msg("Digte o Código correto do Amigo !!");
	 			Util.Msg("(tecle enter para continuar)");
				System.in.read();
	 		}
		}
    }
    
    public void listarMensagensUsuarios(int idUsuario, MensagensDao mDao, UsuariosDao uDao) {
		Usuarios uU = null;
		Usuarios uA = null;
		
		if (!mDao.lista().isEmpty()) {
		    System.out.println("|Mensagens         "+
		        "\n=========================================================");
		    for (Mensagens m : mDao.lista()) {
		        uU = uDao.getById(m.getIdUsuario());
	    	    	uA = uDao.getById(m.getIdUsuarioAmigo());
	    	    	
	    	    	if (m.getIdUsuario()==idUsuario && m.getIdUsuarioAmigo()!=idUsuario) {
	    	    	     System.out.println("Enviada (=>) para: "+ uA.getNome());
	    	    	     System.out.println("Mensagem.........: "+ m.getMensagem());
		    	}
	    	    	else {
	    	    	    if (m.getIdUsuario()!=idUsuario && m.getIdUsuarioAmigo()==idUsuario) {
	    	    		 System.out.println("Recebido (<=) de: "+ uU.getNome());
	    	    		 System.out.println("Mensagem........: "+ m.getMensagem());
	    	    	    }
	    	    	}
	    	    }
		}
		else 
		    System.out.println("Não existem mensagens cadastrados");
    }
    
    public MensagensDao mDao(){
    	return mDao;
    }
    
}
