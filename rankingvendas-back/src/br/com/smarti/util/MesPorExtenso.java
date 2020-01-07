package br.com.smarti.util;

/**
 * @author flavius.filipe
 */
public class MesPorExtenso {
    public static String mesExtenso(int m) {
	String mes = "";
	if (m == 0) {
	    mes = "janeiro";
	}
	if (m == 1) {
	    mes = "fevereiro";
	}
	if (m == 2) {
	    mes = "março";
	}
	if (m == 3) {
	    mes = "abril";
	}
	if (m == 4) {
	    mes = "maio";
	}
	if (m == 5) {
	    mes = "junho";
	}
	if (m == 6) {
	    mes = "julho";
	}
	if (m == 7) {
	    mes = "agosto";
	}
	if (m == 8) {
	    mes = "setembro";
	}
	if (m == 9) {
	    mes = "outubro";
	}
	if (m == 10) {
	    mes = "novembro";
	}
	if (m == 11) {
	    mes = "dezembro";
	}
	return mes;
    }
}