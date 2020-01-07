angular.module('alurapic')
	.controller('VendedorController', function($scope, $http, $routeParams) {
		
		var urlBase = 'http://localhost:8080/RankingVendas/';

		$scope.vendedor = {};
		$scope.mensagem = '';
		$scope.consulta = {};
		$scope.tituloTela = 'Cadastro';

		if($routeParams.idVendedor) {
			$scope.tituloTela = 'Alteração';
			$scope.vendedor.idVendedor = $routeParams.idVendedor;
			var url = urlBase + "vendedor/pesquisar";
			$http.post(url, $scope.vendedor)
			.success(function(success) {
				var obj = success.some(function(obj, i) {
					if(obj.idVendedor == $scope.vendedor.idVendedor){
						$scope.vendedor = obj;
					} else {
						$scope.mensagem = "Ocorreu um erro inesperado. Contacte o adminstrador do sistema.";
					}
				});
			})
			.error(function(erro) {
				$scope.mensagem = success.mensagemRetorno;
			});
		}

		$scope.cadastrar = function(){
			if($scope.vendedor.idVendedor){
				var url = urlBase + "vendedor/alterar";
				$http.post(url, $scope.vendedor)
				.success(function(success) {
					$scope.mensagem = success.mensagemRetorno;
				})
				.error(function(erro) {
					$scope.mensagem = success.mensagemRetorno;
				});
			} else {
				var url = urlBase + "vendedor/cadastrar";
				$scope.vendedor.email = 'default@gmail.com'
				$http.post(url, $scope.vendedor)
				.success(function(success) {
					$scope.mensagem = success.mensagemRetorno;
				})
				.error(function(erro) {
					$scope.mensagem = success.mensagemRetorno;
				});
			}
			$scope.vendedor = {};
		}

		$scope.pesquisar = function(){
			var url = urlBase + "vendedor/pesquisar";
			$http.post(url, $scope.vendedor)
			.success(function(success) {
				$scope.consulta = success;
			})
			.error(function(erro) {
				$scope.mensagem = success.mensagemRetorno;
			});
		}
});