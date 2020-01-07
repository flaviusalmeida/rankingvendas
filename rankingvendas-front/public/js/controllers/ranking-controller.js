angular.module('alurapic')
	.controller('RankingController',  function($scope, $http) {
    
    var urlBase = 'http://localhost:8080/RankingVendas/';

    $scope.venda = {};
    $scope.ranking = {};
    $scope.mensagemSucesso = '';
    $scope.mensagemErro = '';

    // CARREGAR RANKING MENSAL
    $scope.carregarRankingMesAtual = function(){
        var url = urlBase + "venda/ranking";
        $http.post(url, $scope.venda)
        .success(function(success) {
            $scope.ranking = success;
            $scope.totalVendas = 0;

            for (var i = 0; i < $scope.ranking.length; i++) {
                $scope.totalVendas = $scope.totalVendas + $scope.ranking[i].qtdeVidas;
                $scope.ranking[i].posicao = i + 1;
            };
        })
        .error(function(erro) {
            $scope.mensagemErro = success.mensagemRetorno;
        });
    }

    $scope.carregarRankingMesAtual();

    $scope.teste = function(){
        var c = document.getElementById('tabela');
        document.getElementById('the_canvas_element_id').toDataURL()
    }



  
});