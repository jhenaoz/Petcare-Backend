'use strict';

angular.module('petcareApp')
    .controller('AdoptionController', function ($scope, Adoption, Principal, User) {
    	$scope.adoption = {};
        $scope.adoptions = [];

        $scope.init = function(){
            Adoption.query(function(result) {
               $scope.adoptions = result;
            });
            Principal.identity(true).then(function(response){
                User.get({login:response.login}, function(currentUser){
                    $scope.user = currentUser;
                    $scope.adoption.have = currentUser;
                });
            })            
        };

    	
    	$scope.save = function(adoption){
            if ($scope.adoption.id != null) {
                Adoption.update($scope.adoption, function(result){
                    console.log(result);
                    $scope.adoptions.push(result);
                    $scope.adoption = {};
                });
            } else {
                Adoption.save($scope.adoption, function(result){
                    console.log(result);
                    $scope.adoptions.push(result);
                    $scope.adoption = {};
                });
            }
    	};
       $scope.remove = function (id) {
           Adoption.get({id: id}, function(result) {
               $scope.adoption = result;
               $('#deleteAdoptionConfirmation').modal('show');
           });
       };

       $scope.confirmDelete = function (id) {
           Adoption.delete({id: id},
               function () {
                   $('#deleteAdoptionConfirmation').modal('hide');
                   $scope.init();
               });
       };
    	
    	
    	
//        $scope.adoptions = [];
//        $scope.loadAll = function() {
//            Adoption.query(function(result) {
//               $scope.adoptions = result;
//            });
//        };
//        $scope.loadAll();
//

//

//
//        $scope.refresh = function () {
//            $scope.loadAll();
//            $scope.clear();
//        };
//
//        $scope.clear = function () {
//            $scope.adoption = {name: null, species: null, age: null, gender: null, size: null, description: null, id: null};
//        };
        $scope.init();
    });
