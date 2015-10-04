'use strict';

angular.module('petcareApp')
    .factory('Lost', function ($resource, DateUtils) {
        return $resource('api/losts/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.lostDate = DateUtils.convertLocaleDateFromServer(data.lostDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.lostDate = DateUtils.convertLocaleDateToServer(data.lostDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.lostDate = DateUtils.convertLocaleDateToServer(data.lostDate);
                    return angular.toJson(data);
                }
            }
        });
    });
