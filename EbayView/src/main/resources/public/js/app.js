var app = angular.module("webSearchEngine", ["ngRoute", "ngCookies"]);
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
    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
});

//app.run(function($rootScope, $http, $location, $cookies) {
//    // keep user logged in after page refresh
//    if ($cookies.get("access_token")) {
//        $http.defaults.headers.common.Authorization = 'Bearer ' + $cookies.get("access_token");
//    }
//
//    // redirect to login page if not logged in and trying to access a restricted page
//    $rootScope.$on('$locationChangeStart', function (event, next, current) {
//        var publicPages = ['/login'];
//        var restrictedPage = publicPages.indexOf($location.path()) === -1;
//        if (restrictedPage && !$cookies.get("access_token")) {
//            $location.path('/login');
//        }
//    });
//});

app.service("AuthenticationService", function($http, $cookies, $httpParamSerializer){
	var service = {};
	 
    service.Login = Login;
    service.Logout = Logout;

    return service;

    function Login(username, password, callback) {
    	
    	
    	var data = {
    	        grant_type:"password", 
    	        username: username, 
    	        password: password
    	    };
    	    var encoded = btoa("pik-webapp-client:secret");
    	
    	    	
    	$http.post("http://localhost:8090/oauth/token",$httpParamSerializer(data), {
	      headers : {
	        'Accept': 'application/json',
	        "Authorization": "Basic " + encoded,
	        "Content-Type" : "application/x-www-form-urlencoded"
	      }
	    }).success(function(response){
    		console.log(response);
	    	var data = angular.fromJson(response);
    		 // login successful if there's a token in the response
    		if(data['access_token']){
    			 console.log("Access token:  "+data['access_token']);
    			 $cookies.put("access_token", data['access_token']);
    			 // add jwt token to auth header for all requests made by the $http service
                 $http.defaults.headers.common.Authorization = 'Bearer ' + data['access_token'];
                 $http.defaults.headers.common.Authorization =  "Basic "+ btoa(username + ":" + password);

                 // execute callback with true to indicate successful login
                 callback(true);
    		}else{
    			// execute callback with false to indicate failed login
                callback(false);
    		}
    		
    	}).error(function(response){
    		callback(false);
    	});
        
//    	$http.post('/api/authenticate', { username: username, password: password })
//            .success(function (response) {
//                // login successful if there's a token in the response
//                if (response.token) {
//                    // store username and token in local storage to keep user logged in between page refreshes
//                    $localStorage.currentUser = { username: username, token: response.token };
//
//                    // add jwt token to auth header for all requests made by the $http service
//                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.token;
//
//                    // execute callback with true to indicate successful login
//                    callback(true);
//                } else {
//                    // execute callback with false to indicate failed login
//                    callback(false);
//                }
//            });
    }

    function Logout() {
        // remove user from local storage and clear http auth header
        $cookies.remove("access_token");
        $http.defaults.headers.common.Authorization = '';
        
    }
});

app.controller("homeCtrl", function ($http) {
	var self = this;
	$http.get('http://localhost:8090/api/test').success(function(data) {
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
		function($rootScope, $scope, AuthenticationService, $location){
	
//	var authenticate = function(credentials, callback) {
//		
//		var headers = credentials ? {authorization : "Basic "
//	        + btoa(credentials.username + ":" + credentials.password)
//	    } : {};
//		
//	    $http.get('user', {headers : headers}).success(function(data) {
//	      if (data.name) {
//	    	  console.log("Success authentication");
//	        $rootScope.authenticated = true;
//	      } else {
//	    	  console.log("Authentication failed");
//	        $rootScope.authenticated = false;
//	      }
//	      callback && callback();
//	    }).error(function() {
//	      $rootScope.authenticated = false;
//	      callback && callback();
//	    });
//	}
	
//	$scope.login = function() {
//	    $http.post('login-post', $scope.credentials, {
//	      headers : {
//	        "content-type" : "application/json",
//	        'Accept': 'application/json'
//	      }
//	    }).success(function(data) {
//	      authenticate($scope.credentials,function() {
//	        if ($rootScope.authenticated) {
//	          $location.path("/");
//	          $scope.error = false;
//	        } else {
//	          $location.path("/login");
//	          $scope.error = true;
//	        }
//	      });
//	    }).error(function(data) {
//	      $location.path("/login");
//	      $scope.error = true;
//	      $rootScope.authenticated = false;
//	    })
//	  };
//	  
//	  $rootScope.logout = function() {
//		  console.log("Logout action");
//		  $http.post('logout', {}).success(function() {
//		    $rootScope.authenticated = false;
//		    $location.path("/");
//		  }).error(function(data) {
//		    $rootScope.authenticated = false;
//		  });
//	  };

	 
     $scope.login = login;

     initController();

     function initController() {
         // reset login status
         AuthenticationService.Logout();
     };

     function login() {
         AuthenticationService.Login($scope.credentials.username, $scope.credentials.password, function (result) {
             if (result === true) {
            	 $rootScope.authenticated = true;
                 $location.path('/');
             } else {
                 $scope.error = true;
                 $rootScope.authenticated = false;
             }
         });
     };
     
     $rootScope.logout = function(){
    	 AuthenticationService.Logout; 
    	 $rootScope.authenticated = false;
    	 $location.path("/");
     };
});




