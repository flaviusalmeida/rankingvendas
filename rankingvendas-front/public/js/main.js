angular.module('alurapic', ['minhasDiretivas','ngAnimate', 'ngRoute', 'ngResource', 'ngMask', 'angularjs-datetime-picker'])
	.config(function($routeProvider, $locationProvider) {

		$locationProvider.html5Mode(true);

		// LOGIN
		$routeProvider.when('/login', {
			templateUrl: 'partials/login/login.html',
			controller: 'LoginController'
		});

		// RANKING
		$routeProvider.when('/ranking', {
			templateUrl: 'partials/ranking/ranking.html',
			controller: 'RankingController'
		});

		// VENDA
		$routeProvider.when('/venda/pesquisar', {
			templateUrl: 'partials/vendas/pesquisaVendas.html',
			controller: 'VendasController'
		});

		$routeProvider.when('/venda/novo', {
			templateUrl: 'partials/vendas/cadastroVendas.html',
			controller: 'VendasController'
		});

		$routeProvider.when('/venda/editar/:idVenda', {
			templateUrl: 'partials/vendas/cadastroVendas.html',
			controller: 'VendasController'
		});

		$routeProvider.when('/venda/editar/:idVendedor/:idVenda', {
			templateUrl: 'partials/vendas/cadastroVendas.html',
			controller: 'VendasController'
		});

		// VENDEDOR
		$routeProvider.when('/vendedor/pesquisar', {
			templateUrl: 'partials/vendedor/pesquisaVendedor.html',
			controller: 'VendedorController'
		});

		$routeProvider.when('/vendedor/novo', {
			templateUrl: 'partials/vendedor/cadastroVendedor.html',
			controller: 'VendedorController'
		});

		$routeProvider.when('/vendedor/editar/:idVendedor', {
			templateUrl: 'partials/vendedor/cadastroVendedor.html',
			controller: 'VendedorController'
		});

		// DETALHE VENDEDOR/VENDAS
		$routeProvider.when('/vendedor/detalheVendas/:idVendedor', {
			templateUrl: 'partials/vendedor/detVendedorVendas.html',
			controller: 'DetVendedorVendas'
		});

		$routeProvider.otherwise({redirectTo: '/ranking'});

	});