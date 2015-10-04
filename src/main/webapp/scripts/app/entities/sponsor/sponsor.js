'use strict';

angular.module('petcareApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sponsor', {
                parent: 'entity',
                url: '/sponsors',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Sponsors'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sponsor/sponsors.html',
                        controller: 'SponsorController'
                    }
                },
                resolve: {
                }
            })
            .state('sponsor.detail', {
                parent: 'entity',
                url: '/sponsor/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Sponsor'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sponsor/sponsor-detail.html',
                        controller: 'SponsorDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Sponsor', function($stateParams, Sponsor) {
                        return Sponsor.get({id : $stateParams.id});
                    }]
                }
            })
            .state('sponsor.new', {
                parent: 'sponsor',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/sponsor/sponsor-dialog.html',
                        controller: 'SponsorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, species: null, age: null, gender: null, size: null, description: null, image: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('sponsor', null, { reload: true });
                    }, function() {
                        $state.go('sponsor');
                    })
                }]
            })
            .state('sponsor.edit', {
                parent: 'sponsor',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/sponsor/sponsor-dialog.html',
                        controller: 'SponsorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Sponsor', function(Sponsor) {
                                return Sponsor.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('sponsor', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
