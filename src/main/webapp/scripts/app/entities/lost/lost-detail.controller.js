'use strict';

angular.module('petcareApp')
    .controller('LostDetailController', function ($scope, $rootScope, $stateParams, entity, Lost, User) {
        $scope.lost = entity;
        $scope.load = function (id) {
            Lost.get({id: id}, function(result) {
                $scope.lost = result;
            });
        };
        $rootScope.$on('petcareApp:lostUpdate', function(event, result) {
            $scope.lost = result;
        });
    });
