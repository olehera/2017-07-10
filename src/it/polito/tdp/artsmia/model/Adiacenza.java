package it.polito.tdp.artsmia.model;

public class Adiacenza {
	
	private int primo;
	private int secondo;
	private int peso;
	
	public Adiacenza(int primo, int secondo, int peso) {
		super();
		this.primo = primo;
		this.secondo = secondo;
		this.peso = peso;
	}

	public int getPrimo() {
		return primo;
	}

	public int getSecondo() {
		return secondo;
	}

	public int getPeso() {
		return peso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + peso;
		result = prime * result + primo;
		result = prime * result + secondo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adiacenza other = (Adiacenza) obj;
		if (peso != other.peso)
			return false;
		if (primo != other.primo)
			return false;
		if (secondo != other.secondo)
			return false;
		return true;
	}
	
}