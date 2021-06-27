package it.polito.tdp.poweroutages.model;

public class Adiacenze implements Comparable<Adiacenze>{
	
	private Integer n1;
	private Integer n2;
	private Double peso;
	
	public Adiacenze(Integer n1, Integer n2, Double peso) {
		super();
		this.n1 = n1;
		this.n2 = n2;
		this.peso = peso;
	}

	public Integer getN1() {
		return n1;
	}
	
	public void setN1(Integer n1) {
		this.n1 = n1;
	}
	
	public Integer getN2() {
		return n2;
	}
	
	public void setN2(Integer n2) {
		this.n2 = n2;
	}
	
	public Double getPeso() {
		return peso;
	}
	
	public void setPeso(Double peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Adiacenze o) {
		return this.peso.compareTo(o.getPeso());
	}

	@Override
	public String toString() {
		return n2 + "\tcon peso = " + peso +"\n";
	}
	
	
	
}
