'use strict';

angular.module('petcareApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('adoption', {
                parent: 'entity',
                url: '/adoptions',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Adoptions'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/adoption/adoptions.html',
                        controller: 'AdoptionController'
                    }
                },
                resolve: {
                }
            })
            .state('adoption.detail', {
                parent: 'entity',
                url: '/adoption/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Adoption'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/adoption/adoption-detail.html',
                        controller: 'AdoptionDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Adoption', function($stateParams, Adoption) {
                        return Adoption.get({id : $stateParams.id});
                    }]
                }
            })
            .state('adoption.new', {
                parent: 'adoption',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/adoption/adoption-dialog.html',
                        controller: 'AdoptionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, species: null, age: null, gender: null, size: null, description: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('adoption', null, { reload: true });
                    }, function() {
                        $state.go('adoption');
                    })
                }]
            })
            .state('adoption.edit', {
                parent: 'adoption',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/adoption/adoption-dialog.html',
                        controller: 'AdoptionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Adoption', function(Adoption) {
                                return Adoption.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('adoption', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
