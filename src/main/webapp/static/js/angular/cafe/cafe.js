var cafeApp = angular.module('cafeApp', []);

cafeApp.factory('CafesFactory', function ($http) {
    "use strict";
    return {
        findAll: function () {
            return $http.get('demande/cafes');
        }
    };
});

cafeApp.factory('IntensiteFactory', function ($scope) {
    "use strict";
    var intensites = [{},{}];
});

cafeApp.controller('CafeCtrl', function ($scope, CafesFactory) {
    "use strict";
    $scope.cafes = [];
    CafesFactory.findAll().success(function (data) {
        var cafe;
        if (data) {
            for (cafe in data) {
                if (data.hasOwnProperty(cafe)) {
                    data[cafe].nb = 0;
                    data[cafe].active = true;
                }
            }
        }
        $scope.cafes = data;
    });

    $scope.increment = function (cafe) {
        cafe.nb = cafe.nb + 1;
    };

    $scope.decrement = function (cafe) {
        if (cafe.nb !== 0) {
            cafe.nb = cafe.nb - 1;
        }
    };

    $scope.recapFilter = function (cafe) {
        return cafe.nb !== 0;
    };

});

