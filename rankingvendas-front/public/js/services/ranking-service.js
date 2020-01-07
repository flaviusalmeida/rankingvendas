angular.module('alurapic')
    .service('RankingService', function($http) {
        
        var urlBase = 'http://172.16.21.171:8080/RankingVendas/';
        
        // CARREGAR RANKING MENSAL
        this.carregarRankingMesAtual = function(){
            $http.post('http://localhost:8080/RankingVendas/venda/ranking', {})
            .success(function(success) {
                console.log(success);
                return success;
            })
            .error(function(erro) {
                console.log(erro);
                return erro;
            })
        };
        
        
})