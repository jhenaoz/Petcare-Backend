/* globals $ */
'use strict';

angular.module('petcareApp')
    .directive('petcareAppPagination', function() {
        return {
            templateUrl: 'scripts/components/form/pagination.html'
        };
    });
