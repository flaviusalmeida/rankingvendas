package br.com.smarti.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import br.com.smarti.dao.SupervisorDAO;
import br.com.smarti.enumeration.Mensagens;
import br.com.smarti.enumeration.TipoPessoa;
import br.com.smarti.util.AppUtil;
import br.com.smarti.validacao.ValidacaoUtil;
import br.com.smarti.vo.AuditoriaVo;
import br.com.smarti.vo.PessoaVo;
import br.com.smarti.vo.SupervisorVo;

/**
 * @author flavius.filipe
 */
@Path("cadastroSupervisor")
public class SupervisorResource implements Resource {

    static Logger logger = Logger.getLogger(SupervisorResource.class);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String cadastrar(String form) {
	JSONObject statusRetorno = new JSONObject();
	try {
	    JSONObject dados = new JSONObject(form);
	    SupervisorVo supervisorVo = new SupervisorVo();

	    supervisorVo.setAuditoriaVo(new AuditoriaVo());
	    supervisorVo.setPessoaVo(new PessoaVo());
	    if (ValidacaoUtil.isPreenchido(dados.get("email"))) {
		supervisorVo.getPessoaVo().setEmail(dados.get("email").toString());
	    }
	    supervisorVo.getPessoaVo().setNome(dados.get("nome").toString());
	    supervisorVo.getPessoaVo().setTipo(TipoPessoa.SUPERVISOR.getValor());
	    supervisorVo.getPessoaVo().setTelefone(dados.get("telefone").toString());

	    SupervisorDAO.getInstancia().inserir(supervisorVo);

	    AppUtil.adicionarStatusRetorno(statusRetorno, true,
		    Mensagens.SUCESSO_CADASTRO.getDescricaoDinamica("supervisor"));

	} catch (Exception e) {
	    e.printStackTrace();
	    AppUtil.adicionarStatusRetorno(statusRetorno, e, logger);
	}

	return statusRetorno.toString();
    }

    @Override
    public String pesquisar(String filtro) {
	return Mensagens.ERRO_INESPERADO.getDescricao();
    }

    @Override
    public String alterar(String registro) {
	return Mensagens.ERRO_INESPERADO.getDescricao();
    }
}