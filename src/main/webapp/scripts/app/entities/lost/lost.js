'use strict';

angular.module('petcareApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('lost', {
                parent: 'entity',
                url: '/losts',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Losts'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lost/losts.html',
                        controller: 'LostController'
                    }
                },
                resolve: {
                }
            })
            .state('lost.detail', {
                parent: 'entity',
                url: '/lost/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Lost'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lost/lost-detail.html',
                        controller: 'LostDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Lost', function($stateParams, Lost) {
                        return Lost.get({id : $stateParams.id});
                    }]
                }
            })
            .state('lost.new', {
                parent: 'lost',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/lost/lost-dialog.html',
                        controller: 'LostDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, species: null, age: null, gender: null, size: null, description: null, phone: null, lostDate: null, image: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('lost', null, { reload: true });
                    }, function() {
                        $state.go('lost');
                    })
                }]
            })
            .state('lost.edit', {
                parent: 'lost',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/lost/lost-dialog.html',
                        controller: 'LostDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Lost', function(Lost) {
                                return Lost.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('lost', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
