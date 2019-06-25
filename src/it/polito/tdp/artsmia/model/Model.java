package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private ArtsmiaDAO dao;
	private Graph<ArtObject, DefaultWeightedEdge> grafo;
	private Map<Integer, ArtObject> oggetti;
	private ArtObject scelto;
	private List<ArtObject> best;
	private int bestPeso;
	private String classification;
	
	public Model() {
		dao = new ArtsmiaDAO();
		oggetti = new HashMap<>();
		dao.listObjects(oggetti);
	}
	
	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, oggetti.values());
		
		for (Adiacenza a: dao.listAdiacenze())
			Graphs.addEdge(grafo, oggetti.get(a.getPrimo()), oggetti.get(a.getSecondo()), a.getPeso());
		
	}
	
	public boolean verifica(int id) {
		if (oggetti.containsKey(id))
			return true;
		else
			return false;
	}
	
	public int componenteConnessa(int id) {
		scelto = oggetti.get(id);
		
		List<ArtObject> list = new ArrayList<>();
		DepthFirstIterator<ArtObject, DefaultWeightedEdge> iterator = new DepthFirstIterator<>(grafo, scelto);
		
		while ( iterator.hasNext() )
			list.add(iterator.next());
		
		return list.size();
	}
	
	public List<ArtObject> trovaCammino(int lun) {
		best = new ArrayList<>();
		bestPeso = 0;
		classification = scelto.getClassification();
		
		List<ArtObject> parziale = new ArrayList<ArtObject>();
		parziale.add(scelto);
		
		cerca(lun, parziale);
		
		Collections.sort(best);
		return best;
	}

	private void cerca(int lun, List<ArtObject> parziale) {
		
		if (parziale.size() == lun) {
			int peso = calcolaPeso(parziale);
			if (peso > bestPeso) {
				bestPeso = peso;
				best = new ArrayList<>(parziale);
			}
		}
		
		for (ArtObject a: Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1)))
			if (!parziale.contains(a) && a.getClassification().compareTo(classification)==0) {
				parziale.add(a);
				
				cerca(lun, parziale);
				
				parziale.remove(parziale.size()-1);
			}
		
	}
	
	public int calcolaPeso(List<ArtObject> completa) {
		int somma = 0;
		
		for (int i=1; i<completa.size(); i++)
			somma += grafo.getEdgeWeight(grafo.getEdge(completa.get(i-1), completa.get(i)));
		
		return somma;
	}
	
}