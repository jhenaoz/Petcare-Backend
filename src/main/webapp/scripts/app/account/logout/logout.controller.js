'use strict';

angular.module('petcareApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
