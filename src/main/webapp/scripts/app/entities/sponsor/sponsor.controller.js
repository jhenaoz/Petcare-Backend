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
//            $scope.sponsor.id = null;
            if ($scope.sponsor.id != null) {
            	Sponsor.update($scope.sponsor, function(result){
                    console.log(result);
//                    $scope.sponsors.push(result);
                    $scope.init();
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


        $scope.setImage = function ($files, lost) {
            if ($files[0]) {
                var file = $files[0];
                var fileReader = new FileReader();
                fileReader.readAsDataURL(file);
                fileReader.onload = function (e) {
                    var data = e.target.result;
                    var base64Data = data.substr(data.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        lost.image = base64Data;
                    });
                };
            }
        };


        $scope.clear = function () {
            $scope.sponsor = {name: null, species: null, age: null, gender: null, size: null, description: null, image: null, id: null};
        };

        $scope.init();

    });
