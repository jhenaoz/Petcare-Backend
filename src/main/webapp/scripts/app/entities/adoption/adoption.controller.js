'use strict';

angular.module('petcareApp')
    .controller('AdoptionController', function ($scope, Adoption) {
        $scope.adoptions = [];
        $scope.loadAll = function() {
            Adoption.query(function(result) {
               $scope.adoptions = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Adoption.get({id: id}, function(result) {
                $scope.adoption = result;
                $('#deleteAdoptionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Adoption.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAdoptionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.adoption = {name: null, species: null, age: null, gender: null, size: null, description: null, id: null};
        };
    });
