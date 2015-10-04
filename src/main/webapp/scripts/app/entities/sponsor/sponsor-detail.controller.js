'use strict';

angular.module('petcareApp')
    .controller('SponsorDetailController', function ($scope, $rootScope, $stateParams, entity, Sponsor, User) {
        $scope.sponsor = entity;
        $scope.load = function (id) {
            Sponsor.get({id: id}, function(result) {
                $scope.sponsor = result;
            });
        };
        $rootScope.$on('petcareApp:sponsorUpdate', function(event, result) {
            $scope.sponsor = result;
        });
    });
