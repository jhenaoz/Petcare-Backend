'use strict';

angular.module('petcareApp')
    .controller('AdoptionDetailController', function ($scope, $rootScope, $stateParams, entity, Adoption, User) {
        $scope.adoption = entity;
        $scope.load = function (id) {
            Adoption.get({id: id}, function(result) {
                $scope.adoption = result;
            });
        };
        $rootScope.$on('petcareApp:adoptionUpdate', function(event, result) {
            $scope.adoption = result;
        });
    });
