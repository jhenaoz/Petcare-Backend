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
       
      
    	
       $scope.clear = function () {
         $scope.adoption = {name: null, species: null, age: null, gender: null, size: null, description: null, id: null};
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
     
     $scope.abbreviate = function (text) {
         if (!angular.isString(text)) {
             return '';
         }
         if (text.length < 30) {
             return text;
         }
         return text ? (text.substring(0, 15) + '...' + text.slice(-10)) : '';
     };

     $scope.byteSize = function (base64String) {
         if (!angular.isString(base64String)) {
             return '';
         }
         function endsWith(suffix, str) {
             return str.indexOf(suffix, str.length - suffix.length) !== -1;
         }
         function paddingSize(base64String) {
             if (endsWith('==', base64String)) {
                 return 2;
             }
             if (endsWith('=', base64String)) {
                 return 1;
             }
             return 0;
         }
         function size(base64String) {
             return base64String.length / 4 * 3 - paddingSize(base64String);
         }
         function formatAsBytes(size) {
             return size.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ") + " bytes";
         }

         return formatAsBytes(size(base64String));
     };
     
     $scope.setImage = function ($files, adoption) {
         if ($files[0]) {
             var file = $files[0];
             var fileReader = new FileReader();
             fileReader.readAsDataURL(file);
             fileReader.onload = function (e) {
                 var data = e.target.result;
                 var base64Data = data.substr(data.indexOf('base64,') + 'base64,'.length);
                 $scope.$apply(function() {
                     adoption.image = base64Data;
                 });
             };
         }
     };
     
        $scope.init();
    });
