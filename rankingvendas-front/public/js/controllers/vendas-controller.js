angular.module('alurapic')
	.controller('VendasController',  function($scope, $http, $routeParams) {
    
    var urlBase = 'http://localhost:8080/RankingVendas/';
    
    $scope.venda = {};
    $scope.consulta = {};
    $scope.listaVendedores = {};
    $scope.filtro;
    $scope.mensagemSucesso = '';
    $scope.mensagemErro = '';
    $scope.tituloTela = 'Cadastro';
    $scope.linkHrefVoltar = '/venda/pesquisar';

      // CARREGAR LISTA DE VENDEDORES
    $scope.carregarListaVendedores = function(){
        var obj = {
            "situacao": "1" // ATIVO
        }
        var url = urlBase + "vendedor/pesquisar";
        $http.post(url, obj)
        .success(function(success) {
            $scope.listaVendedores = success;
        })
        .error(function(erro) {
            $scope.mensagemErro = success.mensagemRetorno;
        });
    }

    $scope.carregarListaVendedores();
   
     // CARREGAR VENDA A SER ALTERADA
     if($routeParams.idVenda) {
        $scope.tituloTela = 'Alteração';
        $scope.venda.idVenda = $routeParams.idVenda;
        var url = urlBase + "venda/pesquisar";
        $http.post(url, $scope.venda)
        .success(function(success) {
            var obj = success.some(function(obj, i) {
                if(obj.idVenda == $scope.venda.idVenda){
                    $scope.venda = obj;
                } else {
                    $scope.mensagemErro = "Ocorreu um erro inesperado. Contacte o adminstrador do sistema.";
                }
            });
        })
        .error(function(erro) {
            $scope.mensagemErro = success.mensagemRetorno;
        });
    }  

    if($routeParams.idVendedor){
        $scope.linkHrefVoltar = '/vendedor/detalheVendas/' + $routeParams.idVendedor;
    }

    // CADASTRAR NOVA VENDA
    $scope.cadastrarAlterar = function(){
        if($scope.venda.idVenda){
            var url = urlBase + "venda/alterar";
            $http.post(url, $scope.venda)
            .success(function(success) {
                $scope.mensagemSucesso = success.mensagemRetorno;
                $scope.venda = {};
            })
            .error(function(erro) {
                $scope.mensagemErro = success.mensagemRetorno;
            });
        } else {
            var url = urlBase + "venda/cadastrar";
            $http.post(url, $scope.venda)
            .success(function(success) {
                $scope.mensagemSucesso = success.mensagemRetorno;
                $scope.venda = {};
            })
            .error(function(erro) {
                $scope.mensagemErro = success.mensagemRetorno;
            });
        }
    }

    // PESQUISAR VENDA
    $scope.pesquisar = function(){
        var url = urlBase + "venda/pesquisar";
        $http.post(url, $scope.venda)
        .success(function(success) {
            $scope.consulta = success;
            contadorVendas($scope.consulta);
        })
        .error(function(erro) {
            $scope.mensagemErro = success.mensagemRetorno;
        });
    }

    $scope.aplicarMascara = function() {
        setTimeout(function() {
              v = $scope.venda.valor;
              v=v.replace(/\D/g,"");
              v=v.replace(/(\d)(\d{8})$/,"$1.$2");
              v=v.replace(/(\d)(\d{5})$/,"$1.$2");
              v=v.replace(/(\d)(\d{2})$/,"$1,$2");
              $scope.venda.valor = v
              $scope.$apply();
          }, 1)
    }

    function contadorVendas(lista){
        $scope.totalVendas = 0;
        for (var i = 0; i < lista.length; i++) {
            $scope.totalVendas = $scope.totalVendas + lista[i].qtdeVidas;
        };
    }

  
});