package com.santisvs.contadorvisitas.servidor.servlets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Realizar un Sevlet que nos cree un fichero de texto en el servidor que vaya
 * almacenando un contador de visitas. Cada vez que solicitemos el servlet,
 * abrirá el fichero y aumentará el contador. Cada vez que se aumente el
 * contador, guardaremos la información en el fichero de texto.
 * 
 * @author santisvs
 *
 */
@WebServlet(description = "Sevlet que nos cree un fichero de texto en el servidor que vaya almacenando un contador de visitas.", urlPatterns = {
		"/contadorVisitas" })
public class contadorVisitas extends HttpServlet {

	/**
	 * Variables del servlet <code>escribir</code>: Puntero de escritura en
	 * Fichero <code>leer</code>: Puntero de lectura en Fichero
	 * <code>archivo</code>: Fichero donde gusardamos el dato
	 * <code>contador</code> <code>contador</code>: Cuenta el número de visitas
	 */
	static DataOutputStream escribir;
	static DataInputStream leer;
	static File archivo;
	static int contador;

	/**
	 * Método doPost Método al que llamamos desde el formulario
	 * <code>form1</code> desde la página index.jsp
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Gestionar(request, response);
	}

	/**
	 * Método que desarrolla el proceso contador y devuelve el código HTML al
	 * cliente(PC)
	 * 
	 * 1/ Crear el <code>archivo</code> Si (<code>archivo</code> no existe)
	 * Crear puntero <code>escribir</code> Inicialiar a 0 el
	 * <code>contador</code> Cerrar el puntero <code>escribir</code> sino Crear
	 * puntero <code>leer</code> Asignar valor a <code>contador</code> Cerrar el
	 * puntero <code>leer</code>
	 * 
	 * 2/ Crear puntero <code>escribir</code> Incrementar +1
	 * <code>contador</code> Cerrar el puntero <code>escribir</code>
	 * 
	 * 3/ Devolver código HTML a cliente(PC) con el valor de
	 * <code>contador</code>
	 * 
	 * @param request
	 *            = Parametro de entrada con los datos referentes a la llamada
	 *            al servlet desde <code>form1</code>
	 * @param response
	 *            = Parametro de salida con el código HTML que debe interpretar
	 *            el navegador del cliente(PC)
	 * @throws IOException
	 *             = No aplica
	 * @throws ServletException
	 *             = No aplica
	 */
	public void Gestionar(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		archivo = new File("C:\\desarrollo\\workspace\\Ficheros\\Visitas.txt");
		DecimalFormat formato = new DecimalFormat("00000");
		if (!archivo.exists()) {
			escribir = new DataOutputStream(new FileOutputStream("C:\\desarrollo\\workspace\\Ficheros\\Visitas.txt"));
			try {
				escribir.writeInt(0);
				escribir.close();
			} catch (IOException e) {
			}
		} else {
			leer = new DataInputStream(new FileInputStream(archivo));
			try {
				contador = leer.readInt();
				leer.close();
			} catch (IOException e) {
			}
		}
		escribir = new DataOutputStream(new FileOutputStream("C:\\desarrollo\\workspace\\Ficheros\\Visitas.txt"));
		try {
			escribir.writeInt(++contador);
			escribir.close();
		} catch (IOException e) {
		}
		ServletOutputStream flujo = response.getOutputStream();
		response.setContentType("text/html");
		flujo.println("<html>");
		flujo.println("<head>");
		flujo.println("<title>Respuesta Servlet: contadorVisitas</title>");
		flujo.println("</head>");
		flujo.println("<body>");
		String s_cont = formato.format(contador);
		flujo.println("<br><h2>Estado del contador de  visitas:<br><br>");
		flujo.println("<table>");
		flujo.println("<tr align=center><td><h2>" + s_cont + "</h2></td></tr>");
		flujo.println("</table>");
		flujo.println("</body>");
		flujo.println("</html>");
		flujo.close();
	}
}
