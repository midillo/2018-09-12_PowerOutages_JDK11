package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {

	private PowerOutagesDAO dao;
	private SimpleWeightedGraph<Nerc, DefaultWeightedEdge> grafo;
	private Map<Integer, Nerc> idMap;

	public Model() {
		this.dao = new PowerOutagesDAO();
		this.idMap = new HashMap<Integer, Nerc>();

	}

	public void creaGrafo() {

		this.grafo = new SimpleWeightedGraph<Nerc, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		//vertici
		dao.loadAllNercs(idMap);
		Graphs.addAllVertices(this.grafo, idMap.values());

		//archi con peso
		for(Adiacenze a: dao.getAdiacenze()) {
			if(idMap.containsKey(a.getN1()) && idMap.containsKey(a.getN2())) {
				Graphs.addEdge(this.grafo, idMap.get(a.getN1()), idMap.get(a.getN2()), a.getPeso());
			}
		}

		//archi con peso 0
		for(Adiacenze a: dao.getConfinanti()) {
			if(!this.grafo.containsEdge(this.grafo.getEdge(idMap.get(a.getN1()), idMap.get(a.getN2())))) {
				if(idMap.containsKey(a.getN1()) && idMap.containsKey(a.getN2())) {
					Graphs.addEdge(this.grafo, idMap.get(a.getN1()), idMap.get(a.getN2()), 0.00);
				}
			}
		}

	}

	public int getNvertici() {
		return this.grafo.vertexSet().size();
	}

	public int getNarchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Nerc> getNercs(){
		List<Nerc> result = new ArrayList<Nerc>(idMap.values());
		Collections.sort(result);
		return result;
	}
	
	public List<Vicini> getVicini(Nerc partenza){
		
		List<Vicini> result = new ArrayList<Vicini>();
		List<Nerc> vicini = Graphs.neighborListOf(this.grafo, partenza);
		
		for(Nerc v: vicini) {
			Double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(partenza, v));
			result.add(new Vicini(v.getValue(), peso));
		}
		
		Collections.sort(result);
		return result;
	}

}
