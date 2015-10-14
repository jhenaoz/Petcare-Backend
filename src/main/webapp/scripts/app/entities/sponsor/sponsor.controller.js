'use strict';

angular.module('petcareApp')
    .controller('SponsorController', function ($scope, Sponsor, Principal, User) {
    	$scope.sponsor = {};
        $scope.sponsors = [];
       /* $scope.loadAll = function() {
            Sponsor.query(function(result) {
               $scope.sponsors = result;
            });
        };
        $scope.loadAll();*/

        $scope.init = function(){
            Sponsor.query(function(result) {
               $scope.sponsors = result;
            });
            Principal.identity(true).then(function(response){
                User.get({login:response.login}, function(currentUser){
                    $scope.user = currentUser;
                    $scope.sponsor.have = currentUser;
                });
            })
        };

        // $scope.save = function () {
        //     if ($scope.sponsor.id != null) {
        //         Sponsor.update($scope.sponsor, onSaveFinished);
        //     } else {
        //         Sponsor.save($scope.sponsor, onSaveFinished);
        //     }
        // };

    	$scope.save = function(sponsor){
            $scope.sponsor.image = "testImage";
            $scope.sponsor.id = null;
            if ($scope.sponsor.id != null) {
            	Sponsor.update($scope.sponsor, function(result){
                    console.log(result);
                    $scope.sponsors.push(result);
                    $scope.sponsor = {};
                });
            } else {
            	Sponsor.save($scope.sponsor, function(result){
                    console.log(result);
                    $scope.sponsors.push(result);
                    $scope.sponsor = {};
                });
            }
    	};
        /*$scope.delete = function (id) {
            Sponsor.get({id: id}, function(result) {
                $scope.sponsor = result;
                $('#deleteSponsorConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Sponsor.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSponsorConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };*/

    	$scope.remove = function (id) {
            Sponsor.get({id: id}, function(result) {
                $scope.sponsor = result;
                $('#deleteSponsorConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Sponsor.delete({id: id},
                function () {
                    $('#deleteSponsorConfirmation').modal('hide');
                    $scope.init();
                });
        };

        $scope.clear = function () {
            $scope.sponsor = {name: null, species: null, age: null, gender: null, size: null, description: null, image: null, id: null};
        };

        $scope.init();

    });
