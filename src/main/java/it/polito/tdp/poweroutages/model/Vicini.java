package it.polito.tdp.poweroutages.model;

public class Vicini implements Comparable<Vicini>{
	
	@Override
	public String toString() {
		return nome + "\tcon peso = " + peso + "\n";
	}

	private String nome;
	private Double peso;
	
	public Vicini(String nome, Double peso) {
		super();
		this.nome = nome;
		this.peso = peso;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Double getPeso() {
		return peso;
	}
	
	public void setPeso(Double peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Vicini o) {
		return - this.peso.compareTo(o.getPeso());
	}

}
