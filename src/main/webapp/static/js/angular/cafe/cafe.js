var cafeApp = angular.module('cafeApp', []);

cafeApp.factory('CafesFactory', function ($http) {
    "use strict";
    return {
        findAll: function () {
            return $http.get('demande/cafes');
        }
    };
});

cafeApp.factory('IntensiteFactory', function () {
    "use strict";
    var filters = [];
    var colors = ['98E9B0', '7ADF98', '34C84D', 'DFCA3F', 'DFAA3F', 'DF843F', 'CA4C1F', 'CD252B', '820005', '000000'];
    for (var i = 0; i < 10; i++) {
        filters[i] = {
            color: 'color:#' + colors[i],
            force: i + 1,
            dis: true
        };
    }
    return filters;
});

cafeApp.controller('CafeCtrl', function ($scope, CafesFactory, IntensiteFactory) {
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

    $scope.filters = IntensiteFactory;

    $scope.switchFilter = function (filter) {
        filter.dis = !filter.dis;
    }

    $scope.resolveColor= function (filter){
        return filter.dis ? 'color:#808681' : filter.color;
    }
});

