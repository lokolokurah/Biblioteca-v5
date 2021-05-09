package org.iesalandalus.programacion.biblioteca.mvc.vista.grafica.controladoresvistas;

import java.io.IOException;
import java.time.LocalDate;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.biblioteca.mvc.vista.grafica.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorVentanaPrincipal {
	
	private static final String BORRAR_ALUMNO = "Borrar Alumno";
	private static final String BORRAR_LIBRO = "Borrar Libro";
	private static final String DEVOLVER_PRESTAMO = "Devolver Prestamo";
	private static final String BORRAR_PRESTAMO = "Borrar Prestamo";
	
	private ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
	private ObservableList<Prestamo> prestamosAlumno = FXCollections.observableArrayList();
    private ObservableList<Libro> libros = FXCollections.observableArrayList();
	private ObservableList<Prestamo> prestamosLibro = FXCollections.observableArrayList();
    private ObservableList<Prestamo> prestamos = FXCollections.observableArrayList();
    
	private IControlador controladorMVC;
	
    @FXML private TableView<Alumno> tvAlumnos;
    @FXML private TableColumn<Alumno, String> tcNombre;
    @FXML private TableColumn<Alumno, String> tcCorreo;
    @FXML private TableColumn<Alumno, Curso> tcCurso;
    
    @FXML private TableView<Prestamo> tvPrestamosAlumno;
    @FXML private TableColumn<Prestamo, String> tcaLibro;
    @FXML private TableColumn<Prestamo, String> tcafPrestamo;
    @FXML private TableColumn<Prestamo, String> tcafDevolucion;
    @FXML private TableColumn<Prestamo, String> tcaPuntos;
    
    @FXML private TableView<Libro> tvLibros;
    @FXML private TableColumn<Libro, String> tcTitulo;
    @FXML private TableColumn<Libro, String> tcAutor;
    @FXML private TableColumn<Libro, String> tcPD;
    
    @FXML private TableView<Prestamo> tvPrestamosLibro;
    @FXML private TableColumn<Prestamo, String> tclAlumno;
    @FXML private TableColumn<Prestamo, String> tclfPrestamo;
    @FXML private TableColumn<Prestamo, String> tclfDevolucion;
    @FXML private TableColumn<Prestamo, String> tclPuntos;
    
    @FXML private TableView<Prestamo> tvPrestamos;
    @FXML private TableColumn<Prestamo, String> tcAlumno;
    @FXML private TableColumn<Prestamo, String> tcLibro;
    @FXML private TableColumn<Prestamo, String> tcFPrestamo;
    @FXML private TableColumn<Prestamo, String> tcFDevolucion;
    @FXML private TableColumn<Prestamo, String> tcPuntos;
    @FXML private DatePicker dpMes;
    @FXML private CheckBox cbMes;
    @FXML private Label primeroESO;
    
    private Stage anadirAlumno;
    private ControladorAnadirAlumno cAnadirAlumno;
    private Stage anadirLibro;
    private ControladorAnadirLibro cAnadirLibro;
    private Stage anadirPrestamo;
    private ControladorAnadirPrestamo cAnadirPrestamo;
    
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	@FXML
	private void initialize() {
		//Iniciamos la tabla alumnos
		tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	    tcCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
	    tcCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
	    tvAlumnos.setItems(alumnos);
	    tvAlumnos.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> mostrarPrestamosAlumno(nv));
	    //Iniciamos la tabla libros
	    tcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
	    tcAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
	    tcPD.setCellValueFactory(libro -> new SimpleStringProperty(getLibroString(libro.getValue())));
	    tvLibros.setItems(libros);
	    tvLibros.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> mostrarPrestamosLibro(nv));
	    //Iniciamos la tabla Prestamos
	    tcAlumno.setCellValueFactory(new PropertyValueFactory<>("alumno"));
	    tcLibro.setCellValueFactory(new PropertyValueFactory<>("libro"));
	    tcFPrestamo.setCellValueFactory(new PropertyValueFactory<>("fechaPrestamo"));
	    tcFDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));
	    tcPuntos.setCellValueFactory(prestamo -> new SimpleStringProperty(Integer.toString(prestamo.getValue().getPuntos())));
	    tvPrestamos.setItems(prestamos);
	    //Iniciamos la tabla prestamos del alumno
	    tcaLibro.setCellValueFactory(new PropertyValueFactory<>("libro"));
	    tcafPrestamo.setCellValueFactory(new PropertyValueFactory<>("fechaPrestamo"));
	    tcafDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));
	    tcaPuntos.setCellValueFactory(prestamo -> new SimpleStringProperty(Integer.toString(prestamo.getValue().getPuntos())));
	    tvPrestamosAlumno.setItems(prestamosAlumno);
	    //Iniciamos la tabla prestamos del libro
	    tclAlumno.setCellValueFactory(new PropertyValueFactory<>("alumno"));
	    tclfPrestamo.setCellValueFactory(new PropertyValueFactory<>("fechaPrestamo"));
	    tclfDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));
	    tclPuntos.setCellValueFactory(prestamo -> new SimpleStringProperty(Integer.toString(prestamo.getValue().getPuntos())));
	    tvPrestamosLibro.setItems(prestamosLibro);
	    //Mes
	    dpMes.setValue(LocalDate.now());
	    cbMes.selectedProperty().addListener((ob, ov, nv) -> mostrarTodos(nv));
	    dpMes.valueProperty().addListener((ob, ov, nv) -> mostrarPrestamosMes(nv));
	}
	 
	@FXML
	private void salir() {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?", null)) {
			controladorMVC.terminar();
			System.exit(0);
		}
	}
	
	@FXML
	private void acercaDe() throws IOException {
		VBox contenido = FXMLLoader.load(LocalizadorRecursos.class.getResource("vistas/AcercaDe.fxml"));
		Dialogos.mostrarDialogoInformacionPersonalizado("Biblioteca", contenido);
	}
	
	@FXML
	void anadirAlumno(ActionEvent event) throws IOException {
		crearAnadirAlumno();
		anadirAlumno.showAndWait();
	}
	   
    @FXML
    void borrarAlumno(ActionEvent event) {
    	Alumno alumno = null;
		try {
			alumno = tvAlumnos.getSelectionModel().getSelectedItem();
			if (alumno != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_ALUMNO, "¿Estás seguro de que quieres borrar el alumno?", null)) {
				controladorMVC.borrar(alumno);
				alumnos.remove(alumno);
				prestamosAlumno.clear();
				actualizaAlumnos();
				Dialogos.mostrarDialogoInformacion(BORRAR_ALUMNO, "Alumno borrado satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_ALUMNO, e.getMessage());
		}
    }
    
	@FXML
	void anadirLibro(ActionEvent event) throws IOException {
		crearAnadirLibro();
		anadirLibro.showAndWait();
	}
	
    @FXML
    void borrarLibro(ActionEvent event) {
    	Libro libro = null;
		try {
			libro = tvLibros.getSelectionModel().getSelectedItem();
			if (libro != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_LIBRO, "¿Estás seguro de que quieres borrar el libro?", null)) {
				controladorMVC.borrar(libro);
				libros.remove(libro);
				prestamosLibro.clear();
				actualizaLibros();
				Dialogos.mostrarDialogoInformacion(BORRAR_LIBRO, "Libro borrado satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_LIBRO, e.getMessage());
		}
    }
    
	@FXML
	void anadirPrestamo(ActionEvent event) throws IOException {
		crearAnadirPrestamo();
		anadirPrestamo.showAndWait();
	}
	
	@FXML
	void anadirPrestamoAlumno(ActionEvent event) throws IOException {
		crearAnadirPrestamoAlumno();
		anadirPrestamo.showAndWait();
	}
	
	@FXML
	void anadirPrestamoLibro(ActionEvent event) throws IOException {
		crearAnadirPrestamoLibro();
		anadirPrestamo.showAndWait();
	}
	
    @FXML
    void devolverPrestamoAlumno(ActionEvent event) {
    	Prestamo prestamo = null;
		try {
			prestamo = tvPrestamosAlumno.getSelectionModel().getSelectedItem();
			if (prestamo != null && Dialogos.mostrarDialogoConfirmacion(DEVOLVER_PRESTAMO, "¿Estás seguro de que quieres devolver el libro?", null)) {
				controladorMVC.devolver(prestamo, LocalDate.now());
				actualizaAlumnos();
				actualizaPrestamos();
				Dialogos.mostrarDialogoInformacion(DEVOLVER_PRESTAMO, "Libro devuelto satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(DEVOLVER_PRESTAMO, e.getMessage());
		}
    }
    
    @FXML
    void devolverPrestamoLibro(ActionEvent event) {
    	Prestamo prestamo = null;
		try {
			prestamo = tvPrestamosLibro.getSelectionModel().getSelectedItem();
			if (prestamo != null && Dialogos.mostrarDialogoConfirmacion(DEVOLVER_PRESTAMO, "¿Estás seguro de que quieres devolver el libro?", null)) {
				controladorMVC.devolver(prestamo, LocalDate.now());
				actualizaLibros();
				actualizaPrestamos();
				Dialogos.mostrarDialogoInformacion(DEVOLVER_PRESTAMO, "Libro devuelto satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(DEVOLVER_PRESTAMO, e.getMessage());
		}
    }
    
    @FXML
    void devolverPrestamoGlobal(ActionEvent event) {
    	Prestamo prestamo = null;
		try {
			prestamo = tvPrestamos.getSelectionModel().getSelectedItem();
			if (prestamo != null && Dialogos.mostrarDialogoConfirmacion(DEVOLVER_PRESTAMO, "¿Estás seguro de que quieres devolver el libro?", null)) {
				controladorMVC.devolver(prestamo, LocalDate.now());
				actualizaPrestamos();
				Dialogos.mostrarDialogoInformacion(DEVOLVER_PRESTAMO, "Libro devuelto satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(DEVOLVER_PRESTAMO, e.getMessage());
		}
    }
    
    @FXML
    void borrarPrestamoAlumno(ActionEvent event) {
    	Prestamo prestamo = null;
		try {
			prestamo = tvPrestamosAlumno.getSelectionModel().getSelectedItem();
			if (prestamo != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_PRESTAMO, "¿Estás seguro de que quieres borrar el prestamo?", null)) {
				controladorMVC.borrar(prestamo);
				prestamos.remove(prestamo);
				actualizaAlumnos();
				Dialogos.mostrarDialogoInformacion(BORRAR_PRESTAMO, "Prestamo borrado satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_PRESTAMO, e.getMessage());
		}
    }
    
    @FXML
    void borrarPrestamoLibro(ActionEvent event) {
    	Prestamo prestamo = null;
		try {
			prestamo = tvPrestamosLibro.getSelectionModel().getSelectedItem();
			if (prestamo != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_PRESTAMO, "¿Estás seguro de que quieres borrar el prestamo?", null)) {
				controladorMVC.borrar(prestamo);
				prestamos.remove(prestamo);
				actualizaLibros();
				Dialogos.mostrarDialogoInformacion(BORRAR_PRESTAMO, "Prestamo borrado satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_PRESTAMO, e.getMessage());
		}
    }
    
    @FXML
    void borrarPrestamoGlobal(ActionEvent event) {
    	Prestamo prestamo = null;
		try {
			prestamo = tvPrestamos.getSelectionModel().getSelectedItem();
			if (prestamo != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_PRESTAMO, "¿Estás seguro de que quieres borrar el prestamo?", null)) {
				controladorMVC.borrar(prestamo);
				prestamos.remove(prestamo);
				actualizaAlumnos();
				actualizaLibros();
				Dialogos.mostrarDialogoInformacion(BORRAR_PRESTAMO, "Prestamo borrado satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_PRESTAMO, e.getMessage());
		}
    }
    
    public void actualizaAlumnos() {
    	prestamosLibro.clear();
    	tvLibros.getSelectionModel().clearSelection();
        alumnos.setAll(controladorMVC.getAlumnos());
    }
    
    public void actualizaLibros() {
    	prestamosAlumno.clear();
    	tvAlumnos.getSelectionModel().clearSelection();
        libros.setAll(controladorMVC.getLibros());
    }
    
    public void actualizaPrestamos() {
    	prestamos.setAll(controladorMVC.getPrestamos());
    	actualizaEstadisticasMensuales();
    }
    
    public void actualizaPrestamosPorMes(LocalDate mes) {
    	prestamos.setAll(controladorMVC.getPrestamos(mes));
    	actualizaEstadisticasMensuales();
    }
    
    public void actualizaEstadisticasMensuales() {
    	if (dpMes.getValue()!=null) {
        	primeroESO.setText("Estadísticas por cursos: "+controladorMVC.getEstadisticaMensualPorCurso(dpMes.getValue()).toString());
    	} else {
    		primeroESO.setText(controladorMVC.getEstadisticaMensualPorCurso(LocalDate.now()).toString());
    	}
    }
    
    public void mostrarPrestamosAlumno(Alumno alumno) {
    	try {
    		if (alumno != null) {
    			prestamosAlumno.setAll(controladorMVC.getPrestamos(alumno));
    		}
		} catch (IllegalArgumentException e) {
			prestamosAlumno.setAll(FXCollections.observableArrayList());
		}
    	actualizaPrestamos();
    }
    
    public void mostrarPrestamosLibro(Libro libro) {
    	try {
    		if (libro != null) {
    			prestamosLibro.setAll(controladorMVC.getPrestamos(libro));
    		}
		} catch (IllegalArgumentException e) {
			prestamosLibro.setAll(FXCollections.observableArrayList());
		}
    	actualizaPrestamos();
    }
    
    public void mostrarPrestamosMes(LocalDate mes) {
    	if (mes!=null) {
    		actualizaPrestamosPorMes(mes);
    	}
    }
    
    public void mostrarTodos(boolean selected) {
    	if (selected) {
    		dpMes.setDisable(false);
    		mostrarPrestamosMes(dpMes.getValue());
    	} else {
    		dpMes.setDisable(true);
    		actualizaPrestamos();
    	}
    }
	
	private String getLibroString(Libro libro) {
		String paginasDuracion = "";
		if (libro instanceof LibroEscrito) {
			paginasDuracion = Integer.toString(((LibroEscrito) libro).getNumPaginas())+"p";
		} else if (libro instanceof AudioLibro) {
			paginasDuracion = Integer.toString(((AudioLibro) libro).getDuracion())+"m";
		}
		return paginasDuracion;
	}
	
	private void crearAnadirAlumno() throws IOException {
		if (anadirAlumno == null) {
			anadirAlumno = new Stage();
			FXMLLoader cargadorAnadirAlumno = new FXMLLoader(
					LocalizadorRecursos.class.getResource("vistas/AnadirAlumno.fxml"));
			VBox raizAnadirAlumno = cargadorAnadirAlumno.load();
			cAnadirAlumno = cargadorAnadirAlumno.getController();
			cAnadirAlumno.setControladorMVC(controladorMVC);
			cAnadirAlumno.setAlumnos(alumnos);
			cAnadirAlumno.inicializa();
			Scene escenaAnadirAlumno = new Scene(raizAnadirAlumno);
			anadirAlumno.setTitle("Añadir Alumno");
			anadirAlumno.initModality(Modality.APPLICATION_MODAL); 
			anadirAlumno.setScene(escenaAnadirAlumno);
		} else {
			cAnadirAlumno.inicializa();
		}
	}
	
	private void crearAnadirLibro() throws IOException {
		if (anadirLibro == null) {
			anadirLibro = new Stage();
			FXMLLoader cargadorAnadirLibro = new FXMLLoader(
					LocalizadorRecursos.class.getResource("vistas/AnadirLibro.fxml"));
			VBox raizAnadirLibro = cargadorAnadirLibro.load();
			cAnadirLibro = cargadorAnadirLibro.getController();
			cAnadirLibro.setControladorMVC(controladorMVC);
			cAnadirLibro.setLibros(libros);
			cAnadirLibro.inicializa();
			Scene escenaAnadirLibro = new Scene(raizAnadirLibro);
			anadirLibro.setTitle("Añadir Libro");
			anadirLibro.initModality(Modality.APPLICATION_MODAL); 
			anadirLibro.setScene(escenaAnadirLibro);
		} else {
			cAnadirLibro.inicializa();
		}
	}
	
	private void crearAnadirPrestamo() throws IOException {
		if (anadirPrestamo == null) {
			anadirPrestamo = new Stage();
			FXMLLoader cargadorAnadirPrestamo = new FXMLLoader(
					LocalizadorRecursos.class.getResource("vistas/AnadirPrestamo.fxml"));
			VBox raizAnadirPrestamo = cargadorAnadirPrestamo.load();
			cAnadirPrestamo = cargadorAnadirPrestamo.getController();
			cAnadirPrestamo.setControladorMVC(controladorMVC);
			cAnadirPrestamo.setAlumnos(alumnos);
			cAnadirPrestamo.setLibros(libros);
			cAnadirPrestamo.setPadre(this);
			cAnadirPrestamo.inicializa();
			Scene escenaAnadirPrestamo = new Scene(raizAnadirPrestamo);
			anadirPrestamo.setTitle("Añadir Prestamo");
			anadirPrestamo.initModality(Modality.APPLICATION_MODAL); 
			anadirPrestamo.setScene(escenaAnadirPrestamo);
		} else {
			cAnadirPrestamo.setAlumnos(alumnos);
			cAnadirPrestamo.setLibros(libros);
			cAnadirPrestamo.inicializa();
		}
	}
	
	private void crearAnadirPrestamoAlumno() throws IOException {
		if (anadirPrestamo == null) {
			anadirPrestamo = new Stage();
			FXMLLoader cargadorAnadirPrestamo = new FXMLLoader(
					LocalizadorRecursos.class.getResource("vistas/AnadirPrestamo.fxml"));
			VBox raizAnadirPrestamo = cargadorAnadirPrestamo.load();
			cAnadirPrestamo = cargadorAnadirPrestamo.getController();
			cAnadirPrestamo.setControladorMVC(controladorMVC);
			cAnadirPrestamo.setAlumnos(alumnos);
			cAnadirPrestamo.setLibros(libros);
			cAnadirPrestamo.setPadre(this);
			cAnadirPrestamo.inicializa();
			Scene escenaAnadirPrestamo = new Scene(raizAnadirPrestamo);
			anadirPrestamo.setTitle("Añadir Prestamo");
			anadirPrestamo.initModality(Modality.APPLICATION_MODAL); 
			anadirPrestamo.setScene(escenaAnadirPrestamo);
		} else {
			cAnadirPrestamo.setAlumnosBool(true);
			cAnadirPrestamo.setLibrosBool(false);
			cAnadirPrestamo.setAlumnos(alumnos);
			cAnadirPrestamo.setLibros(libros);
			cAnadirPrestamo.inicializa();
		}
	}
	
	private void crearAnadirPrestamoLibro() throws IOException {
		if (anadirPrestamo == null) {
			anadirPrestamo = new Stage();
			FXMLLoader cargadorAnadirPrestamo = new FXMLLoader(
					LocalizadorRecursos.class.getResource("vistas/AnadirPrestamo.fxml"));
			VBox raizAnadirPrestamo = cargadorAnadirPrestamo.load();
			cAnadirPrestamo = cargadorAnadirPrestamo.getController();
			cAnadirPrestamo.setControladorMVC(controladorMVC);
			cAnadirPrestamo.setAlumnos(alumnos);
			cAnadirPrestamo.setLibros(libros);
			cAnadirPrestamo.setPadre(this);
			cAnadirPrestamo.inicializa();
			Scene escenaAnadirPrestamo = new Scene(raizAnadirPrestamo);
			anadirPrestamo.setTitle("Añadir Prestamo");
			anadirPrestamo.initModality(Modality.APPLICATION_MODAL); 
			anadirPrestamo.setScene(escenaAnadirPrestamo);
		} else {
			cAnadirPrestamo.setLibrosBool(true);
			cAnadirPrestamo.setAlumnosBool(false);
			cAnadirPrestamo.setAlumnos(alumnos);
			cAnadirPrestamo.setLibros(libros);
			cAnadirPrestamo.inicializa();
		}
	}
	
}
