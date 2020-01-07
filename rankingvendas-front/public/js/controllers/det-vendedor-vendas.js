angular.module('alurapic')
	.controller('DetVendedorVendas',  function($scope, $http, $routeParams) {
    
    var urlBase = 'http://localhost:8080/RankingVendas/';

    $scope.listaVendas = {};
    $scope.filtro = {};
    $scope.filtro.dataVendaInicial = getDataInicioMes();
    $scope.filtro.dataVendaFinal = getDataFimMes();
    

    if($routeParams.idVendedor) {
        $scope.filtro.idVendedor = $routeParams.idVendedor;
        var url = urlBase + "venda/pesquisar";
        $http.post(url, $scope.filtro)
        .success(function(success) {
            $scope.listaVendas = success;
            $scope.nomeVendedor = $scope.listaVendas[0].nomeVendedor;
            contadorVendas($scope.listaVendas);
        })
        .error(function(erro) {
            $scope.mensagem = success.mensagemRetorno;
        });
    }

     // PESQUISAR VENDA
     $scope.pesquisar = function(){
        var url = urlBase + "venda/pesquisar";
        $http.post(url, $scope.filtro)
        .success(function(success) {
            $scope.listaVendas = success;
            contadorVendas($scope.listaVendas);
        })
        .error(function(erro) {
            $scope.mensagemErro = success.mensagemRetorno;
        });
    }

    // TODO: Criar Factory ou Service?
    function getDataInicioMes() {
        var date = new Date();
        var mes = date.getMonth() + 1;
        if (mes < 9) {
            mes = "0" + mes;
        }
        var ano = date.getFullYear();
        var dataInicioMes = "1/" + mes + "/" + ano;

        return dataInicioMes;
    }

    // TODO: Criar Factory ou Service?
    function getDataFimMes() {
        var date = new Date();
        var ano = date.getFullYear();
        var mes = date.getMonth();
        date = new Date (ano, mes+1, 0);

        var dia = date.getDate();
        mes = date.getMonth() + 1;
        if (mes < 9) {
            mes = "0" + mes;
        }
        ano = date.getFullYear();
        var dataFimMes = dia + "/" + mes + "/" + ano;

        return dataFimMes;
    }

    function contadorVendas(lista){
        $scope.totalVendas = 0;
        for (var i = 0; i < lista.length; i++) {
            $scope.totalVendas = $scope.totalVendas + lista[i].qtdeVidas;
        };
    }
    
});