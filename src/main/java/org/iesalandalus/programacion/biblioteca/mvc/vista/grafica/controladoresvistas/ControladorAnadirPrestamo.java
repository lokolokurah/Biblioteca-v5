package org.iesalandalus.programacion.biblioteca.mvc.vista.grafica.controladoresvistas;

import java.time.LocalDate;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.vista.grafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ControladorAnadirPrestamo {
	
	private IControlador controladorMVC;
	private ControladorVentanaPrincipal padre;
	private ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
	private ObservableList<Libro> libros = FXCollections.observableArrayList();
	
	@FXML private ListView<Alumno> lvAlumno;
	@FXML private ListView<Libro> lvLibro;
	@FXML private DatePicker dpPrestamo;
	@FXML private Button btAnadir;
	@FXML private Button btCancelar;
	
	private boolean alumnosBool, librosBool;
	
	private class CeldaAlumno extends ListCell<Alumno> {
        @Override
        public void updateItem(Alumno alumno, boolean vacio) {
            super.updateItem(alumno, vacio);
            if (vacio) {
            	setText("");
            } else {
            	setText(alumno.getNombre());
            }
        }
    }
    
    private class CeldaLibro extends ListCell<Libro> {
        @Override
        public void updateItem(Libro libro, boolean vacio) {
            super.updateItem(libro, vacio);
            if (vacio) {
            	setText("");
            } else {
            	setText(libro.getTitulo());
            }
        }
    }
    
	@FXML
	private void initialize() {
		lvAlumno.setItems(alumnos);
		lvAlumno.setCellFactory(l -> new CeldaAlumno());
		lvLibro.setItems(libros);
		lvLibro.setCellFactory(l -> new CeldaLibro());
	}
	
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
    public void setPadre(ControladorVentanaPrincipal padre) {
    	this.padre = padre;
    }
    
	public void setAlumnos(ObservableList<Alumno> alumnos) {
	    this.alumnos.setAll(alumnos);
	}
	    
	public void setLibros(ObservableList<Libro> libros) {
	    this.libros.setAll(libros);
	}
	
	public void setAlumnosBool(boolean trigger) {
		alumnosBool = trigger;
	}
	
	public void setLibrosBool(boolean trigger) {
		librosBool = trigger;
	}
	
	@FXML
	private void anadirPrestamo() {
		Prestamo prestamo = null;
		try {
			prestamo = getPrestamo();
			controladorMVC.prestar(prestamo);
			padre.actualizaPrestamos();
			if (alumnosBool) {
				padre.mostrarPrestamosAlumno(prestamo.getAlumno());
				alumnosBool = false;
			}
			if (librosBool) {
				padre.mostrarPrestamosLibro(prestamo.getLibro());
				librosBool = false;
			}
			Stage propietario = ((Stage) btAnadir.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Prestamo", "Prestamo añadido satisfactoriamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Prestamo", e.getMessage());
		}	
	}
	
	@FXML
	private void cancelar() {
		((Stage) btCancelar.getScene().getWindow()).close();
	}
	
    public void inicializa() {
    	lvAlumno.getSelectionModel().clearSelection();
        lvLibro.getSelectionModel().clearSelection();
        lvAlumno.setItems(alumnos);
        lvLibro.setItems(libros);
    	dpPrestamo.setValue(LocalDate.now());
    }
	
	private Prestamo getPrestamo() {
		Alumno alumno = lvAlumno.getSelectionModel().getSelectedItem();
		Libro libro = lvLibro.getSelectionModel().getSelectedItem();
		LocalDate fechaPrestamo = dpPrestamo.getValue();
		return new Prestamo(alumno, libro, fechaPrestamo);
	}

}
