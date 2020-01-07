package br.com.smarti.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import br.com.smarti.validacao.ValidacaoUtil;

/**
 * Classe responsável por validações e manipulação de data
 * 
 * @author flavius.filipe
 */
public class DataUtil {
    public static final int POSTERIOR_IGUAL = 1;
    public static final int POSTERIOR = 2;
    public static final int ANTERIOR_IGUAL = 3;
    public static final int ANTERIOR = 4;

    public static boolean validate(Date data1, int validacao, Date data2) {
	if (ValidacaoUtil.isPreenchido(data1) && ValidacaoUtil.isPreenchido(data2)) {
	    Date date1 = truncDate(data1);
	    Date date2 = truncDate(data2);

	    switch (validacao) {
	    case POSTERIOR_IGUAL:
		if (!date1.before(date2))
		    return true;
		break;
	    case POSTERIOR:
		if (date1.after(date2))
		    return true;
		break;
	    case ANTERIOR_IGUAL:
		if (!date1.after(date2))
		    return true;
		break;
	    case ANTERIOR:
		if (date1.before(date2))
		    return true;
		break;
	    }
	}
	return false;
    }

    public static Date add(Date date, int field, int amount) {
	GregorianCalendar calendar = new GregorianCalendar();
	calendar.setTime(date);
	calendar.add(field, amount);
	return calendar.getTime();
    }

    public static int get(Date date, int field) {
	GregorianCalendar calendar = new GregorianCalendar();
	calendar.setTime(date);
	return calendar.get(field);
    }

    public static Date truncDate(Date date) {
	if (date != null) {
	    GregorianCalendar calendar = new GregorianCalendar();
	    calendar.setTime(date);
	    calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
	    calendar.set(GregorianCalendar.MINUTE, 0);
	    calendar.set(GregorianCalendar.SECOND, 0);
	    calendar.set(GregorianCalendar.MILLISECOND, 0);
	    return calendar.getTime();
	}
	return date;
    }

    public static Date truncFinalDate(Date date) {
	if (date != null) {
	    GregorianCalendar calendar = new GregorianCalendar();
	    calendar.setTime(date);

	    calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
	    calendar.set(GregorianCalendar.MINUTE, 0);
	    calendar.set(GregorianCalendar.SECOND, 0);
	    calendar.set(GregorianCalendar.MILLISECOND, 0);

	    calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
	    calendar.add(GregorianCalendar.MILLISECOND, -1);

	    return calendar.getTime();
	}
	return date;
    }

    public static Date truncDate(String data) throws Exception {
	Date date = stringToDate(data);

	return date;
    }

    public static Date truncFinalDate(String data) throws Exception {
	Date date = stringToDate(data);
	if (date != null) {
	    GregorianCalendar calendar = new GregorianCalendar();
	    calendar.setTime(date);

	    calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
	    calendar.set(GregorianCalendar.MINUTE, 0);
	    calendar.set(GregorianCalendar.SECOND, 0);
	    calendar.set(GregorianCalendar.MILLISECOND, 0);

	    calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
	    calendar.add(GregorianCalendar.MILLISECOND, -1);

	    return calendar.getTime();
	}
	return date;
    }

    public static String getStringTimestamp(Date data) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	return sdf.format(data);
    }

    public static String getStringTimestampWithMilliseconds(Date data) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	return sdf.format(data);
    }

    public static String getDataFormatada(Date data) {
	if (data != null) {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    return sdf.format(data);
	}
	return "";
    }

    public static String getDataHoraFormatada(Date data) {
	if (data != null) {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    return sdf.format(data);
	}
	return "";
    }

    public static Date getDate(int year, int month, int day) {
	GregorianCalendar calendar = new GregorianCalendar();
	calendar.set(GregorianCalendar.YEAR, year);
	calendar.set(GregorianCalendar.MONTH, month);
	calendar.set(GregorianCalendar.DAY_OF_MONTH, day);
	return calendar.getTime();
    }

    public static Date getPrimeiroDia(Date data) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(data);
	int year = calendar.get(Calendar.YEAR);
	int month = calendar.get(Calendar.MONTH);
	return getPrimeiroDia(year, month);
    }

    public static Date getPrimeiroDia(int year, int month) {
	GregorianCalendar calendar = new GregorianCalendar();
	calendar.set(GregorianCalendar.YEAR, year);
	calendar.set(GregorianCalendar.MONTH, month);
	calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);

	calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
	calendar.set(GregorianCalendar.MINUTE, 0);
	calendar.set(GregorianCalendar.SECOND, 0);
	calendar.set(GregorianCalendar.MILLISECOND, 0);

	return calendar.getTime();
    }

    public static Date getUltimoDia(Date data) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(data);
	int year = calendar.get(Calendar.YEAR);
	int month = calendar.get(Calendar.MONTH);
	return getUltimoDia(year, month);
    }

    public static Date getUltimoDia(int year, int month) {
	GregorianCalendar calendar = new GregorianCalendar();
	calendar.set(GregorianCalendar.YEAR, year);
	calendar.set(GregorianCalendar.MONTH, month);
	calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);

	calendar.set(GregorianCalendar.HOUR_OF_DAY, 23);
	calendar.set(GregorianCalendar.MINUTE, 59);
	calendar.set(GregorianCalendar.SECOND, 59);
	calendar.set(GregorianCalendar.MILLISECOND, 999);

	calendar.set(GregorianCalendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

	return calendar.getTime();
    }

    public static Date setTime(Date date, int hour, int minute) {
	GregorianCalendar calendar = new GregorianCalendar();
	calendar.setTime(date);
	calendar.set(GregorianCalendar.HOUR_OF_DAY, hour);
	calendar.set(GregorianCalendar.MINUTE, minute);
	calendar.set(GregorianCalendar.SECOND, 0);
	calendar.set(GregorianCalendar.MILLISECOND, 0);
	return calendar.getTime();
    }

    public static boolean validarIntervalo(Date dataInicial, Date dataFinal) {
	if (dataInicial != null && dataFinal != null && validate(dataInicial, POSTERIOR, dataFinal)) {
	    return false;
	}
	return true;
    }

    /**
     * Calcula a diferença de duas datas em dias <br>
     * <b>Importante:</b> Quando realiza a diferença em dias entre duas datas, este
     * método considera as horas restantes e as converte em fração de dias.
     * 
     * @param dataInicial
     * @param dataFinal
     * @return quantidade de dias existentes entre a dataInicial e dataFinal.
     */
    public static double diferencaEmDias(Date dataInicial, Date dataFinal, boolean arredondar) {
	long diferenca = dataFinal.getTime() - dataInicial.getTime();
	// O resultado é diferença entre as datas em dias
	double diferencaEmDias = (diferenca / 1000) / 60 / 60 / 24;
	// Calcula as horas restantes
	long horasRestantes = (diferenca / 1000) / 60 / 60 % 24;
	if (arredondar) {
	    if (horasRestantes > 0) {
		return diferencaEmDias + 1;
	    } else {
		return diferencaEmDias;
	    }
	} else {
	    // Transforma as horas restantes em fração de dias
	    return diferencaEmDias + (horasRestantes / 24d);
	}
    }

    /**
     * Calcula a diferença de duas datas em horas <br>
     * <b>Importante:</b> Quando realiza a diferença em horas entre duas datas, este
     * método considera os minutos restantes e os converte em fração de horas.
     * 
     * @param dataInicial
     * @param dataFinal
     * @return quantidade de horas existentes entre a dataInicial e dataFinal.
     */
    public static double diferencaEmHoras(Date dataInicial, Date dataFinal, boolean arredondar) {
	long diferenca = dataFinal.getTime() - dataInicial.getTime();
	long diferencaEmHoras = (diferenca / 1000) / 60 / 60;
	long minutosRestantes = (diferenca / 1000) / 60 % 60;
	double horasRestantes = minutosRestantes / 60d;

	if (arredondar) {
	    if (horasRestantes > 0) {
		return diferencaEmHoras + 1;
	    } else {
		return diferencaEmHoras;
	    }
	} else {
	    return diferencaEmHoras + (horasRestantes);
	}
    }

    public static int diferencaEmMeses(Date dataInicial, Date dataFinal) {
	/*
	 * Valor de um mês em milisegundos, sendo que os valores são: 30 dias no mês, 24
	 * horas no dia, 60 minutos por hora, 60 segundos por minuto e 1000 milisegundos
	 */
	final double MES_EM_MILISEGUNDOS = 30.0 * 24.0 * 60.0 * 60.0 * 1000.0;
	// final double MES_EM_MILISEGUNDOS = 2592000000.0;
	Double numeroDeMeses = (dataFinal.getTime() - dataInicial.getTime()) / MES_EM_MILISEGUNDOS;
	return numeroDeMeses.intValue();
    }

    /**
     * Calcula a diferença em anos entre duas data. obs: Não considera horário e nem
     * os meses das datas passadas como parametro.
     */
    public static int diferencaEmAnos(Date dataInicial, Date dataFinal) throws Exception {

	GregorianCalendar GregorianInicial = new GregorianCalendar();
	GregorianInicial.setTime(dataInicial);

	GregorianCalendar GregorianFinal = new GregorianCalendar();
	GregorianFinal.setTime(dataFinal);

	int anoInicial = GregorianInicial.get(GregorianCalendar.YEAR);

	int anoFinal = GregorianFinal.get(GregorianCalendar.YEAR);

	return anoFinal - anoInicial;
    }

    /**
     * Retorna data escrita por extenso.
     * 
     * @param
     * @return "99 de MES de 9999" ou "99 de MES de 9999 às HH:MM"
     */
    public static String getDataPorExtenso(Date date, boolean exibeHora) {

	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	String dia = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
	int mes = calendar.get(Calendar.MONTH);
	String mesExtenso = MesPorExtenso.mesExtenso(mes);
	String ano = Integer.toString(calendar.get(Calendar.YEAR));

	if (exibeHora) {
	    String hora = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
	    String minuto;
	    if (calendar.get(Calendar.MINUTE) < 10) {
		minuto = "0" + calendar.get(Calendar.MINUTE);
	    } else {
		minuto = Integer.toString(calendar.get(Calendar.MINUTE));
	    }
	    return dia + " de " + mesExtenso + " de " + ano + " às " + hora + ":" + minuto;
	}
	return dia + " de " + mesExtenso + " de " + ano;
    }

    /**
     * Converte um string em data
     */
    public static Date stringToDate(String data) throws Exception {
	if (data == null || data.equals("")) {
	    return null;
	}
	Date date = null;
	try {
	    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    date = formatter.parse(data);
	} catch (ParseException e) {
	    throw e;
	}
	return date;
    }

    public static Date addDiasUteis(Date data, Integer quantidadeDias) {
	Calendar dataInicial = Calendar.getInstance();
	dataInicial.setTime(data);
	while (quantidadeDias > 0) {
	    dataInicial.add(Calendar.DAY_OF_MONTH, 1);
	    int diaDaSemana = dataInicial.get(Calendar.DAY_OF_WEEK);
	    if (diaDaSemana != Calendar.SATURDAY && diaDaSemana != Calendar.SUNDAY) {
		--quantidadeDias;
	    }
	}
	return dataInicial.getTime();
    }
}