package br.com.smarti.resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.google.gson.Gson;

import br.com.smarti.dao.VendaDAO;
import br.com.smarti.enumeration.Acao;
import br.com.smarti.enumeration.Mensagens;
import br.com.smarti.objectJson.RankingJson;
import br.com.smarti.objectJson.VendaJson;
import br.com.smarti.util.AppUtil;
import br.com.smarti.util.DataUtil;
import br.com.smarti.validacao.ValidacaoUtil;
import br.com.smarti.vo.VendaVo;

/**
 * @author flavius.filipe
 */
@Path("venda")
public class VendaResource implements Resource {

    static Logger logger = Logger.getLogger(VendaResource.class);
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Path("cadastrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String cadastrar(String form) {
	JSONObject statusRetorno = new JSONObject();
	try {
	    JSONObject dados = new JSONObject(form);

	    VendaVo vendaVo = new VendaVo(dados, Acao.ACAO_INSERIR.getValor());

	    VendaDAO.getInstancia().inserir(vendaVo);

	    AppUtil.adicionarStatusRetorno(statusRetorno, true,
		    Mensagens.SUCESSO_CADASTRO.getDescricaoDinamica("venda"));

	} catch (Exception e) {
	    e.printStackTrace();
	    AppUtil.adicionarStatusRetorno(statusRetorno, e, logger);
	}

	return statusRetorno.toString();
    }

    @Path("pesquisar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String pesquisar(String filtro) {
	String resultado = null;
	try {
	    JSONObject dados = new JSONObject(filtro);

	    VendaVo vendaVo = new VendaVo();
	    preencherFiltro(dados, vendaVo);

	    ArrayList<VendaVo> listaVendas = new ArrayList<VendaVo>(
		    VendaDAO.getInstancia().pesquisar(vendaVo, VendaDAO.JOIN_VENDEDOR | VendaDAO.JOIN_PESSOA));

	    ArrayList<VendaJson> listaVendasJson = new ArrayList<VendaJson>();

	    for (VendaVo vo : listaVendas) {
		String dataVenda = sdf.format(vo.getDataVenda());
		String dataEntrega = sdf.format(vo.getDataEntrega());

		listaVendasJson.add(new VendaJson(vo.getId(), vo.getNumeroProposta(), vo.getNomeCliente(),
			vo.getCPFTitular(), vo.getEnderecoCliente(), vo.getTelefoneCliente(), dataVenda, dataEntrega,
			vo.getQtdeVidas(), vo.getValor().toString(), vo.getVendedorVo().getId(),
			vo.getVendedorVo().getPessoaVo().getNome()));
	    }

	    resultado = new Gson().toJson(listaVendasJson);

	} catch (Exception e) {
	    e.printStackTrace();
	    return Mensagens.ERRO_INESPERADO.getDescricao();
	}
	if (ValidacaoUtil.isPreenchido(resultado)) {
	    return resultado;
	}
	return Mensagens.CONSULTA_SEM_RESULTADOS.getDescricao();

    }

    @Path("alterar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String alterar(String registro) {
	JSONObject statusRetorno = new JSONObject();
	try {
	    JSONObject dados = new JSONObject(registro);

	    VendaVo vendaVo = new VendaVo(dados, Acao.ACAO_ALTERAR.getValor());

	    VendaDAO.getInstancia().alterar(vendaVo);

	    AppUtil.adicionarStatusRetorno(statusRetorno, true,
		    Mensagens.SUCESSO_ALTERACAO.getDescricaoDinamica("venda"));
	} catch (Exception e) {
	    e.printStackTrace();
	    AppUtil.adicionarStatusRetorno(statusRetorno, e, logger);
	}

	return statusRetorno.toString();
    }

    @Path("ranking")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String ranking(String filtro) {
	try {
	    ArrayList<RankingJson> ranking;
	    ranking = VendaDAO.getInstancia().getRanking();
	    if (ValidacaoUtil.isPreenchido(ranking)) {
		return new Gson().toJson(ranking);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    private void preencherFiltro(JSONObject dados, VendaVo vo) throws Exception {
	vo.setFiltro(new HashMap<String, Object>());
	if (dados.has("idVenda") && ValidacaoUtil.isPreenchido(dados.get("idVenda").toString())) {
	    vo.setId(new Long(dados.get("idVenda").toString()));
	}
	if (dados.has("numeroProposta") && ValidacaoUtil.isPreenchido(dados.get("numeroProposta").toString())) {
	    vo.setNumeroProposta(new Long(dados.get("numeroProposta").toString()));
	}
	if (dados.has("nomeCliente") && ValidacaoUtil.isPreenchido(dados.get("nomeCliente").toString())) {
	    vo.getFiltro().put("nomeCliente", dados.get("nomeCliente").toString().toUpperCase());
	}
	if (dados.has("CPFTitular") && ValidacaoUtil.isPreenchido(dados.get("CPFTitular").toString())) {
	    vo.setCPFTitular(dados.get("CPFTitular").toString());
	}
	if (dados.has("dataVendaInicial") && ValidacaoUtil.isPreenchido(dados.get("dataVendaInicial").toString())) {
	    Date data = DataUtil.truncDate(dados.get("dataVendaInicial").toString());
	    vo.getFiltro().put("dataVendaInicial", data);
	}
	if (dados.has("dataVendaFinal") && ValidacaoUtil.isPreenchido(dados.get("dataVendaFinal").toString())) {
	    Date data = DataUtil.truncFinalDate(dados.get("dataVendaFinal").toString());
	    vo.getFiltro().put("dataVendaFinal", data);
	}
	if (dados.has("dataEntregaInicial") && ValidacaoUtil.isPreenchido(dados.get("dataEntregaInicial").toString())) {
	    Date data = DataUtil.truncDate(dados.get("dataEntregaInicial").toString());
	    vo.getFiltro().put("dataEntregaInicial", data);
	}
	if (dados.has("dataEntregaFinal") && ValidacaoUtil.isPreenchido(dados.get("idVendedor").toString())) {
	    Date data = DataUtil.truncFinalDate(dados.get("dataEntregaFinal").toString());
	    vo.getFiltro().put("dataEntregaFinal", data);
	}
	if (dados.has("idVendedor") && ValidacaoUtil.isPreenchido(dados.get("idVendedor").toString())) {
	    vo.getFiltro().put("idVendedor", new Long(dados.get("idVendedor").toString()));
	}
    }

}