var cafeApp = angular.module('cafeApp', []);

cafeApp.service('InitService', function ($http) {
    "use strict";

    this.filter = function () {
        var filters = {}, i = 0, colors = ['98E9B0', '7ADF98', '34C84D', 'DFCA3F', 'DFAA3F', 'DF843F', 'CA4C1F', 'CD252B', '820005', '000000'];
        filters.int = [];
        for (i; i < 10; i++) {
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
            variations: true
        };

        filters.profil = {
            intense: true,
            equilibre: true,
            fruite: true
        };

        return filters;
    }

    this.findCafesList = function () {
        return $http.get('demande/cafes');
    }

});

cafeApp.controller('CafeCtrl', function ($scope, InitService) {
    "use strict";
    var activeInt = [], initial = {};


    function isActive(cafe) {
        return cafe.activeInt && cafe.activeMode && cafe.activeProfil;
    }

    function initCafes(cafes, isNew) {
        for (var cafe in cafes) {
            if (cafes.hasOwnProperty(cafe)) {
                if (isNew) {
                    cafes[cafe].nb = 0;
                }
                cafes[cafe].activeInt = true;
                cafes[cafe].activeMode = true;
                cafes[cafe].activeProfil = true;
            }
        }
        return cafes;
    }

    function init() {
        InitService.findCafesList().success(function (data) {
            initial.cafes = initCafes(data.cafes, data.demande.id ? false : true);
            initial.demande = data.demande;
            $scope.cafes = angular.copy(initial.cafes);
            $scope.demande = angular.copy(initial.demande);
        });
    }

    init();
    $scope.filters = InitService.filter();

    $scope.increment = function (cafe) {
        if (isActive(cafe)) {
            cafe.nb++;
            $scope.demande.nombreBoites++;
            $scope.demande.montantTotal = $scope.demande.montantTotal + (cafe.prix * 10);
        }
    };

    $scope.decrement = function (cafe) {
        if (isActive(cafe)) {
            if (cafe.nb !== 0) {
                cafe.nb--;
                $scope.demande.nombreBoites--;
                $scope.demande.montantTotal = $scope.demande.montantTotal - (cafe.prix * 10);
            }
        }
    };

    $scope.recapFilter = function (cafe) {
        return cafe.nb !== 0;
    };

    $scope.switchFilterInt = function (filter) {
        var isEmpty, idx;
        filter.dis ?  activeInt.push(filter.force) :  activeInt.splice(activeInt.indexOf(filter.force), 1);
        isEmpty = activeInt.length === 0;
        for (idx in $scope.cafes) {
            if ($scope.cafes.hasOwnProperty(idx)) {
                $scope.cafes[idx].activeInt = activeInt.indexOf($scope.cafes[idx].intensite) !== -1 || isEmpty;
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

    $scope.switchProfil = function (profil) {
        if ($scope.filters.profil[profil]) {
            switch (profil) {
                case 'intense' :
                    $scope.filters.profil.equilibre = true;
                    $scope.filters.profil.fruite = true;
                    break;
                case 'equilibre' :
                    $scope.filters.profil.intense = true;
                    $scope.filters.profil.fruite = true;
                    break;
                case 'fruite' :
                    $scope.filters.profil.equilibre = true;
                    $scope.filters.profil.intense = true;
                    break;
            }
        }
        $scope.filters.profil[profil] = !$scope.filters.profil[profil];
        for (var idx in $scope.cafes) {
            if ($scope.filters.profil[profil]) {
                $scope.cafes[idx].activeProfil = true;
            } else {
                $scope.cafes[idx].activeProfil = $scope.cafes[idx].profilAromatique === profil.toUpperCase() ? true : false;
            }
        }
    }

    $scope.resolveCoffeeClass = function (cafe) {
        return isActive(cafe) ? 'active' : 'darken';
    }

    $scope.resolveColor = function (filter) {
        return filter.dis ? 'color:#808681' : filter.color;
    };

    $scope.resolveBtn = function (btn) {
        return btn ? 'btn' : 'btn btn-inverse';
    }

    $scope.resetFilters = function () {
        $scope.cafes = initCafes($scope.cafes, false);
        $scope.filters = InitService.filter();
    };

    $scope.resetCmd = function () {
        $scope.cafes = angular.copy(initial.cafes);
        $scope.demande = angular.copy(initial.demande);
        $scope.filters = InitService.filter();
    };
});

