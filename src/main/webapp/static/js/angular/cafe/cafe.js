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

    this.findCafesList = function (){
        return $http.get('demande/cafes');
    }
});

cafeApp.controller('CafeCtrl', function ($scope, InitService) {
    "use strict";


    function isActive(cafe) {
        return cafe.activeInt && cafe.activeMode && cafe.activeProfil;
    }

    $scope.cafes = [];
    $scope.activeInt = [];

    InitService.findCafesList().success(function (data) {
        var cafe, newDemande;
        if (data) {
            newDemande = data.demande.id ? false : true;
            for (cafe in data.cafes) {
                if (data.cafes.hasOwnProperty(cafe)) {
                    if (newDemande) {
                        data.cafes[cafe].nb = 0;
                    }
                    data.cafes[cafe].activeInt = true;
                    data.cafes[cafe].activeMode = true;
                    data.cafes[cafe].activeProfil = true;
                }
            }
            $scope.cafes = data.cafes;
            $scope.demande = data.demande;
        }
    });

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

    $scope.filters = InitService.filter();

    $scope.switchFilterInt = function (filter) {
        var isEmpty, idx;
        if (filter.dis) {
            $scope.activeInt.push(filter.force);
        } else {
            $scope.activeInt.splice($scope.activeInt.indexOf(filter.force), 1);
        }
        isEmpty = $scope.activeInt.length === 0;
        for (idx in $scope.cafes) {
            if ($scope.cafes.hasOwnProperty(idx)) {
                if ($scope.activeInt.indexOf($scope.cafes[idx].intensite) === -1 && !isEmpty) {
                    $scope.cafes[idx].activeInt = false;
                } else {
                    $scope.cafes[idx].activeInt = true;
                }
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
        for (var cafe in $scope.cafes) {
            $scope.cafes[cafe].activeInt = true;
            $scope.cafes[cafe].activeMode = true;
            $scope.cafes[cafe].activeProfil = true;
        }
        $scope.filters = InitService.filter();
        console.log();
    };

});

