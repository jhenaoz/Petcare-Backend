'use strict';

angular.module('petcareApp')
    .controller('SponsorController', function ($scope, Sponsor) {
        $scope.sponsors = [];
        $scope.loadAll = function() {
            Sponsor.query(function(result) {
               $scope.sponsors = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
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
        };

        $scope.clear = function () {
            $scope.sponsor = {name: null, species: null, age: null, gender: null, size: null, description: null, image: null, id: null};
        };
    });
