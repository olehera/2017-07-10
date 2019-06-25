package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model;
	
	public void setModel(Model model) {
		this.model = model;
	}

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="boxLUN"
	private ChoiceBox<Integer> boxLUN; // Value injected by FXMLLoader

	@FXML // fx:id="btnCalcolaComponenteConnessa"
	private Button btnCalcolaComponenteConnessa; // Value injected by FXMLLoader

	@FXML // fx:id="btnCercaOggetti"
	private Button btnCercaOggetti; // Value injected by FXMLLoader

	@FXML // fx:id="btnAnalizzaOggetti"
	private Button btnAnalizzaOggetti; // Value injected by FXMLLoader

	@FXML // fx:id="txtObjectId"
	private TextField txtObjectId; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doAnalizzaOggetti(ActionEvent event) {
		model.creaGrafo();
	}

	@FXML
	void doCalcolaComponenteConnessa(ActionEvent event) {
		int id = 0;
		
		try {
    		id = Integer.parseInt(txtObjectId.getText().trim());
    	} catch(NullPointerException npe) {
    		txtResult.setText("Inserisci un numero intero come object id");
    		txtObjectId.clear();
    		return ;
    	}
    	
    	if ( !model.verifica(id) ) {
    		txtResult.setText("Id inserito non presente nel database!");
    		return ;
    	} 
    	
    	int num = model.componenteConnessa(id);
    	
    	txtResult.setText("Componente Connessa: "+num+" vertici!");
    	
    	List<Integer> interi = new LinkedList<>();
    	for (int i=2; i<=num; i++)
    		interi.add(i);
    	
    	boxLUN.getItems().clear();
    	boxLUN.getItems().addAll(interi);
	}

	@FXML
	void doCercaOggetti(ActionEvent event) {
		int lun = 0;
		
		if (boxLUN.getValue()==null) {
    		txtResult.setText("Devi selezionare un LUN!");
    		return ;
    	} else 
    		lun = (int) boxLUN.getValue();
		
		List<ArtObject> cammino = model.trovaCammino(lun);
		txtResult.setText("\n\nCammino di Peso Massimo:\n\nPeso Totale: "+model.calcolaPeso(cammino)+"\n\n");
		for (ArtObject a: cammino)
			txtResult.appendText(a.getName()+"\n");
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert boxLUN != null : "fx:id=\"boxLUN\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnCalcolaComponenteConnessa != null : "fx:id=\"btnCalcolaComponenteConnessa\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnCercaOggetti != null : "fx:id=\"btnCercaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnAnalizzaOggetti != null : "fx:id=\"btnAnalizzaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert txtObjectId != null : "fx:id=\"txtObjectId\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";
	}
	
}