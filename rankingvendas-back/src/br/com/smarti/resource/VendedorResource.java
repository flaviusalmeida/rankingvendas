package br.com.smarti.resource;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.google.gson.Gson;

import br.com.smarti.dao.VendedorDAO;
import br.com.smarti.enumeration.Acao;
import br.com.smarti.enumeration.Mensagens;
import br.com.smarti.objectJson.VendedorJson;
import br.com.smarti.util.AppUtil;
import br.com.smarti.validacao.ValidacaoUtil;
import br.com.smarti.vo.VendedorVo;

/**
 * @author flavius.filipe
 */
@Path("vendedor")
public class VendedorResource implements Resource {

    static Logger logger = Logger.getLogger(VendedorResource.class);

    @Path("cadastrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String cadastrar(String form) {
	JSONObject statusRetorno = new JSONObject();
	try {
	    JSONObject dados = new JSONObject(form);

	    VendedorVo vendedorVo = new VendedorVo(dados, Acao.ACAO_INSERIR.getValor());

	    VendedorDAO.getInstancia().inserir(vendedorVo);

	    AppUtil.adicionarStatusRetorno(statusRetorno, true,
		    Mensagens.SUCESSO_CADASTRO.getDescricaoDinamica("Vendedor"));

	} catch (Exception e) {
	    e.printStackTrace();
	    AppUtil.adicionarStatusRetorno(statusRetorno, e, logger);
	}

	return statusRetorno.toString();
    }

    @Path("{pesquisar}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String pesquisar(String filtro) {
	String resultado = null;
	try {
	    JSONObject dados = new JSONObject(filtro);

	    VendedorVo vendedorVo = new VendedorVo();
	    preencherFiltro(dados, vendedorVo);

	    ArrayList<VendedorVo> listaVendedores = new ArrayList<VendedorVo>(VendedorDAO.getInstancia()
		    .pesquisar(vendedorVo, VendedorDAO.JOIN_PESSOA | VendedorDAO.JOIN_AUDITORIA));

	    ArrayList<VendedorJson> listaCendedorJson = new ArrayList<VendedorJson>();

	    for (VendedorVo vo : listaVendedores) {
		listaCendedorJson.add(new VendedorJson(vo.getId(), vo.getPessoaVo().getId(), vo.getPessoaVo().getNome(),
			vo.getPessoaVo().getEmail(), vo.getPessoaVo().getTelefone(), vo.getPessoaVo().getTipo()));
	    }

	    resultado = new Gson().toJson(listaCendedorJson);

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

	    VendedorVo vendedorVo = new VendedorVo(dados, Acao.ACAO_ALTERAR.getValor());
	    VendedorDAO.getInstancia().alterar(vendedorVo);

	    AppUtil.adicionarStatusRetorno(statusRetorno, true,
		    Mensagens.SUCESSO_ALTERACAO.getDescricaoDinamica("Vendedor"));

	} catch (Exception e) {
	    e.printStackTrace();
	    AppUtil.adicionarStatusRetorno(statusRetorno, e, logger);
	}

	return statusRetorno.toString();

    }

    private void preencherFiltro(JSONObject dados, VendedorVo vo) {
	vo.setFiltro(new HashMap<String, Object>());
	if (dados.has("idVendedor") && ValidacaoUtil.isPreenchido(dados.get("idVendedor").toString())) {
	    vo.setId(new Long(dados.get("idVendedor").toString()));
	}
	if (dados.has("nome")) {
	    vo.getFiltro().put("nome", dados.get("nome").toString().toUpperCase());
	}
	if (dados.has("tipo")) {
	    vo.getFiltro().put("tipo", dados.get("tipo").toString());
	}

    }

}