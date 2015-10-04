'use strict';

angular.module('petcareApp')
    .factory('Sponsor', function ($resource, DateUtils) {
        return $resource('api/sponsors/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
