'use strict';

angular.module('petcareApp')
    .controller('LostController', function ($scope, Lost, Principal, User ) {
    	$scope.lost = {};
        $scope.losts = [];
        /*$scope.loadAll = function() {
            Lost.query(function(result) {
               $scope.losts = result;
            });
        };
        $scope.loadAll();*/
        
        $scope.init = function(){
            Lost.query(function(result) {
               $scope.losts = result;
            });
            Principal.identity(true).then(function(response){
                User.get({login:response.login}, function(currentUser){
                    $scope.user = currentUser;
                    $scope.lost.have = currentUser;
                });
            })            
        };
    	
    	$scope.save = function(lost){
            if ($scope.sponsor.id != null) {
            	Lost.update($scope.lost, function(result){
                    console.log(result);
                    $scope.losts.push(result);
                    $scope.lost = {};
                });
            } else {
            	Lost.save($scope.lost, function(result){
                    console.log(result);
                    $scope.losts.push(result);
                    $scope.lost = {};
                });
            }
    	};

        $scope.delete = function (id) {
            Lost.get({id: id}, function(result) {
                $scope.lost = result;
                $('#deleteLostConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Lost.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteLostConfirmation').modal('hide');
                    $scope.clear();
                });
        };
        /*
        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };*/

        $scope.clear = function () {
            $scope.lost = {name: null, species: null, age: null, gender: null, size: null, description: null, phone: null, lostDate: null, image: null, id: null};
        };

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
        
        
        $scope.init();
    });
