package org.iesalandalus.programacion.biblioteca.mvc.vista.grafica.controladoresvistas;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.vista.grafica.utilidades.Dialogos;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAnadirAlumno {
	
	private static final String ER_NOMBRE = "[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+\s{1}[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+";
	private static final String ER_CORREO = "[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)+"; 
	
	private IControlador controladorMVC;
	private ObservableList<Alumno> alumnos;
	
	@FXML private TextField tfNombre;
	@FXML private TextField tfCorreo;
	@FXML private ComboBox<Curso> cbCurso;
	@FXML private Button btAnadir;
	@FXML private Button btCancelar;
	
	@FXML
	private void initialize() {
		tfNombre.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_NOMBRE, tfNombre));
		tfCorreo.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_CORREO, tfCorreo));
	}
	
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	public void setAlumnos(ObservableList<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
	
	@FXML
	private void anadirAlumno() {
		Alumno alumno = null;
		try {
			alumno = getAlumno();
			controladorMVC.insertar(alumno);
			alumnos.setAll(controladorMVC.getAlumnos());
			Stage propietario = ((Stage) btAnadir.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Alumno", "Alumno añadido satisfactoriamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Alumno", e.getMessage());
		}	
	}
	
	@FXML
	private void cancelar() {
		((Stage) btCancelar.getScene().getWindow()).close();
	}
	
    public void inicializa() {
    	tfNombre.setText("");
    	compruebaCampoTexto(ER_NOMBRE, tfNombre);
    	tfCorreo.setText("");
    	compruebaCampoTexto(ER_CORREO, tfCorreo);
    	cbCurso.getItems().setAll(Curso.values());
    	cbCurso.getSelectionModel().select(Curso.PRIMERO);
    }
    
	private void compruebaCampoTexto(String er, TextField campoTexto) {	
		String texto = campoTexto.getText();
		if (texto.matches(er)) {
			campoTexto.setStyle("-fx-border-color: green; -fx-border-radius: 5;");
		}
		else {
			campoTexto.setStyle("-fx-border-color: red; -fx-border-radius: 5;");
		}
	}
	
	private Alumno getAlumno() {
		String nombre = tfNombre.getText();
		String correo = tfCorreo.getText();
		Curso curso = cbCurso.valueProperty().getValue();
		return new Alumno(nombre, correo, curso);
	}

}
