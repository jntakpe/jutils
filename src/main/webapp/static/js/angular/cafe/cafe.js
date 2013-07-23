var cafeApp = angular.module('cafeApp', []);

cafeApp.factory('CafesFactory', function ($http) {
    "use strict";
    return {
        findAll: function () {
            return $http.get('demande/cafes');
        }
    };
});

cafeApp.factory('FiltersFactory', function () {
    "use strict";
    var filters = {};
    filters.int = [];
    var colors = ['98E9B0', '7ADF98', '34C84D', 'DFCA3F', 'DFAA3F', 'DF843F', 'CA4C1F', 'CD252B', '820005', '000000'];
    for (var i = 0; i < 10; i++) {
        filters.int[i] = {
            color: 'color:#' + colors[i],
            force: i + 1,
            dis: true
        };
    }

    filters.mode = {
        decaffeinato: true,
        espresso: true,
        lungo: true,
        variations :true
    }

    filters.profil = {
        intense: true,
        equilibre: true,
        fruite: true
    }

    return filters;
});

cafeApp.controller('CafeCtrl', function ($scope, CafesFactory, FiltersFactory) {
    "use strict";
    $scope.cafes = [];
    $scope.activeInt = [];

    CafesFactory.findAll().success(function (data) {
        var cafe;
        if (data) {
            for (cafe in data) {
                if (data.hasOwnProperty(cafe)) {
                    data[cafe].nb = 0;
                    data[cafe].activeInt = true;
                    data[cafe].activeMode = true;
                    data[cafe].activeProfil = true;
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

    $scope.filters = FiltersFactory;

    $scope.switchFilterInt = function (filter) {
        var isEmpty;
        if (filter.dis) {
            $scope.activeInt.push(filter.force);
        } else {
            $scope.activeInt.splice($scope.activeInt.indexOf(filter.force), 1);
        }
        isEmpty = $scope.activeInt.length === 0;
        for (var idx in $scope.cafes) {
            if ($scope.activeInt.indexOf($scope.cafes[idx].intensite) === -1 && !isEmpty) {
                $scope.cafes[idx].activeInt = false;
            } else {
                $scope.cafes[idx].activeInt = true;
            }
        }
        filter.dis = !filter.dis;
    }

    $scope.switchMode = function (mode) {
        if ($scope.filters.mode[mode]) {
            switch (mode) {
                case 'decaffeinato' :
                    $scope.filters.mode.espresso = true;
                    $scope.filters.mode.lungo = true;
                    $scope.filters.mode.variations = true;
                    break;
                case 'espresso' :
                    $scope.filters.mode.decaffeinato = true;
                    $scope.filters.mode.lungo = true;
                    $scope.filters.mode.variations = true;
                    break;
                case 'lungo' :
                    $scope.filters.mode.decaffeinato = true;
                    $scope.filters.mode.espresso = true;
                    $scope.filters.mode.variations = true;
                    break;
                case 'variations' :
                    $scope.filters.mode.decaffeinato = true;
                    $scope.filters.mode.espresso = true;
                    $scope.filters.mode.lungo = true;
                    break;
            }
        }
        $scope.filters.mode[mode] = !$scope.filters.mode[mode];
        for (var idx in $scope.cafes) {
            if ($scope.filters.mode[mode]) {
                $scope.cafes[idx].activeMode = true;
            } else {
                $scope.cafes[idx].activeMode = $scope.cafes[idx].categorie === mode.toUpperCase() ? true : false;
            }
        }
    }

    $scope.resolveCoffeeClass = function (cafe) {
        return cafe.activeInt && cafe.activeMode && cafe.activeProfil ? 'active' : 'darken';
    }

    $scope.resolveColor = function (filter) {
        return filter.dis ? 'color:#808681' : filter.color;
    };

    $scope.resolveBtn = function (btn) {
        return btn ? 'btn' : 'btn btn-inverse';
    }
});

