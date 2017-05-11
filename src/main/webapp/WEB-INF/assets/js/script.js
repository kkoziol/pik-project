var app = angular.module("webSearchEngine", ["ngRoute"]);
app.config(function($routeProvider, $httpProvider) {
    $routeProvider
        .when("/", {
            templateUrl : "home.html",
            controller : "homeCtrl"
        })
        .when("/orders", {
            templateUrl : "orders.html",
            controller : "ordersCtrl"
        })
        .when("/eBay", {
            templateUrl : "eBay.html",
            controller : "eBayCtrl"
        })
        .when("/history", {
            templateUrl : "history.html",
            controller : "historyCtrl"
        })
        .when("/settings", {
            templateUrl : "settings.html",
            controller : "settingsCtrl"
        })
        .when("/login",{
        	templateUrl : "login.html",
        	controller : "loginCtrl"
        });
    
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
});

app.controller("homeCtrl", function ($http) {
	var self = this;
	$http.get('/test/').success(function(data) {
		self.test = data;
	});
});

app.controller("ordersCtrl", function ($http) {
	
});

app.controller("eBayCtrl", function ($scope,$http) {

});

app.controller("historyCtrl", function ($scope, $http) {

});

app.controller("settingsCtrl", function ($scope, $http) {

});

app.controller('loginCtrl', 
		function($rootScope, $scope, $http, $location){
	
	var authenticate = function(credentials, callback) {
		
		var headers = credentials ? {authorization : "Basic "
	        + btoa(credentials.username + ":" + credentials.password)
	    } : {};
		
	    $http.get('user', {headers : headers}).success(function(data) {
	      if (data.name) {
	    	  console.log("Success authentication");
	        $rootScope.authenticated = true;
	      } else {
	    	  console.log("Authentication failed");
	        $rootScope.authenticated = false;
	      }
	      callback && callback();
	    }).error(function() {
	      $rootScope.authenticated = false;
	      callback && callback();
	    });
	}
	
	$scope.login = function() {
	    $http.post('login-post', $scope.credentials, {
	      headers : {
	        "content-type" : "application/json",
	        'Accept': 'application/json'
	      }
	    }).success(function(data) {
	      authenticate($scope.credentials,function() {
	        if ($rootScope.authenticated) {
	          $location.path("/");
	          $scope.error = false;
	        } else {
	          $location.path("/login");
	          $scope.error = true;
	        }
	      });
	    }).error(function(data) {
	      $location.path("/login");
	      $scope.error = true;
	      $rootScope.authenticated = false;
	    })
	  };
	  
	  $rootScope.logout = function() {
		  console.log("Logout action");
		  $http.post('logout', {}).success(function() {
		    $rootScope.authenticated = false;
		    $location.path("/");
		  }).error(function(data) {
		    $rootScope.authenticated = false;
		  });
	  };
	
});




