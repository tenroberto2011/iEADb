package br.edu.ufal.si.dao;

public interface interDao<E> {

	public void salva(E o);

	public void deleta(E o);

	public E getById(int id);

	public E getByString(String s);

}
