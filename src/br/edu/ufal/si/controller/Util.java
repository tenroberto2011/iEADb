# UFAL - BSI - EAD - 2014.1
# Nome: Carlos Roberto dos Santos Silva
# Data: 
# Objetivo: criar app executado via terminal, parecido com Facebook

package br.edu.ufal.si.controller;

import java.util.Scanner;

public class Util {

	public static void limpaConsole() {
		final String ANSI_CLS = "\u001b[2J";
		final String ANSI_HOME = "\u001b[H";
		System.out.print(ANSI_CLS + ANSI_HOME);
		System.out.print("");
		System.out.flush();
	}

	public static void limparTela() {
		final String ANSI_CLS = "\u001b[2J";
		final String ANSI_HOME = "\u001b[H";
		System.out.print(ANSI_CLS + ANSI_HOME);
		System.out.print("");
		System.out.flush();
	}

	public static void cabecalhoTela(String tela) {
		System.out.println("==================[ " + tela
		                + " ]===================");
	}

	public static void rodapeTela() {
		System.out.println(
		                "=================================================");
	}

	public static void loginAtualTela(String loginAtual) {
		System.out.println("(==> Login Atual: " + loginAtual + " )");
	}

	public static void Msg(String msg) {
		System.out.println(msg);
	}

	public static boolean MsgSimNao(String msg) {
		final String sim = "s";
		final String SIM = "S";
		final String nao = "n";
		final String NAO = "N";
		boolean r = false;

		Scanner scn = new Scanner(System.in);
		System.out.println(msg);
		String simnao = scn.nextLine();
		if (simnao.equals(sim) || sim.equals(SIM)) {
			r = true;
		}
		return r;
	}
}
