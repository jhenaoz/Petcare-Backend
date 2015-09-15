'use strict';

angular.module('petcareApp').controller('AdoptionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Adoption', 'User',
        function($scope, $stateParams, $modalInstance, entity, Adoption, User) {

        $scope.adoption = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Adoption.get({id : id}, function(result) {
                $scope.adoption = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('petcareApp:adoptionUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.adoption.id != null) {
                Adoption.update($scope.adoption, onSaveFinished);
            } else {
                Adoption.save($scope.adoption, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
