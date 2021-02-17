package org.iesalandalus.programacion.biblioteca.mvc.modelo;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AlumnoTest;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibroTest;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.CursoTest;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscritoTest;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.PrestamoTest;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros.AlumnosTest;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros.LibrosTest;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros.PrestamosTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AlumnoTest.class, AudioLibroTest.class, LibroEscritoTest.class, PrestamoTest.class, CursoTest.class,
				AlumnosTest.class, LibrosTest.class, PrestamosTest.class,
				ModeloTest.class })
public class AllTests {

}
