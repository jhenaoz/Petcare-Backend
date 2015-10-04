'use strict';

angular.module('petcareApp').controller('SponsorDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Sponsor', 'User',
        function($scope, $stateParams, $modalInstance, entity, Sponsor, User) {

        $scope.sponsor = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Sponsor.get({id : id}, function(result) {
                $scope.sponsor = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('petcareApp:sponsorUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.sponsor.id != null) {
                Sponsor.update($scope.sponsor, onSaveFinished);
            } else {
                Sponsor.save($scope.sponsor, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
