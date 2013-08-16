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
    };

    this.findCafesList = function () {
        return $http.get('demande/cafes');
    };

});

cafeApp.controller('CafeCtrl', function ($scope, $http, InitService) {
    "use strict";
    var activeInt = [], initial = {};


    function isActive(cafe) {
        return cafe.activeInt && cafe.activeMode && cafe.activeProfil;
    }

    function initCafes(cafes) {
        var cafe;
        for (cafe in cafes) {
            if (cafes.hasOwnProperty(cafe)) {
                cafes[cafe].activeInt = true;
                cafes[cafe].activeMode = true;
                cafes[cafe].activeProfil = true;
            }
        }
        return cafes;
    }

    function init() {
        InitService.findCafesList().success(function (data) {
            initial.cafes = initCafes(data.cafes);
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
        if (filter.dis) {
            activeInt.push(filter.force);
        } else {
            activeInt.splice(activeInt.indexOf(filter.force), 1);
        }
        isEmpty = activeInt.length === 0;
        for (idx in $scope.cafes) {
            if ($scope.cafes.hasOwnProperty(idx)) {
                $scope.cafes[idx].activeInt = activeInt.indexOf($scope.cafes[idx].intensite) !== -1 || isEmpty;
            }
        }
        filter.dis = !filter.dis;
    };

    $scope.switchMode = function (mode) {
        var idx;
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
        for (idx in $scope.cafes) {
            if ($scope.cafes.hasOwnProperty(idx)) {
                if ($scope.filters.mode[mode]) {
                    $scope.cafes[idx].activeMode = true;
                } else {
                    $scope.cafes[idx].activeMode = $scope.cafes[idx].categorie === mode.toUpperCase() ? true : false;
                }
            }
        }
    };

    $scope.switchProfil = function (profil) {
        var idx;
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
        for (idx in $scope.cafes) {
            if ($scope.cafes.hasOwnProperty(idx)) {
                if ($scope.filters.profil[profil]) {
                    $scope.cafes[idx].activeProfil = true;
                } else {
                    $scope.cafes[idx].activeProfil = $scope.cafes[idx].profilAromatique === profil.toUpperCase() ? true : false;
                }
            }
        }
    };

    $scope.resolveCoffeeClass = function (cafe) {
        $scope.displayInfos(cafe);
        return isActive(cafe) ? 'active' : 'darken';
    };

    $scope.resolveColor = function (filter) {
        return filter.dis ? 'color:#808681' : filter.color;
    };

    $scope.resolveBtn = function (btn) {
        return btn ? 'btn' : 'btn btn-inverse';
    };

    $scope.resetFilters = function () {
        $scope.cafes = initCafes($scope.cafes);
        $scope.filters = InitService.filter();
    };

    $scope.resetCmd = function () {
        $scope.cafes = angular.copy(initial.cafes);
        $scope.demande = angular.copy(initial.demande);
        $scope.filters = InitService.filter();
    };

    $scope.submitCmd = function () {
        var cafe, cafesDTO = [], cafeDTO, msg = {};
        if ($scope.demande.nombreBoites !== 0) {
            for (cafe in $scope.cafes) {
                if ($scope.cafes.hasOwnProperty(cafe) && $scope.cafes[cafe].nb !== 0) {
                    cafeDTO = {};
                    cafeDTO.id = $scope.cafes[cafe].id;
                    cafeDTO.nb = $scope.cafes[cafe].nb;
                    cafesDTO.push(cafeDTO);
                }
            }
            $http.post('demande', {demande: $scope.demande, cafes: cafesDTO}).
                success(function (response) {
                    window.location = response.data;
                }).
                error(function (response) {
                    jUtils.displayError(response);
                });
        } else if ($scope.demande.id !== null) {
            $http.delete('demande/' + $scope.demande.id).
                success(function (response) {
                    window.location = response.data;
                }).
                error(function (response) {
                    jUtils.displayError(response);
                });
        } else {
            msg.message = 'Veuillez sélectionner au moins un café.';
            jUtils.displayError(msg);
        }
    };

    $scope.displayInfos = function(cafe){
        $('#popCafe'+cafe.id).popover({
            html:true,
            delay: {
                show: 500,
                hide: 200
            } ,
            title:cafe.nom,
            content:
                "Catégorie : " + cafe.categorie + "<br/>" +
                "Description : " + cafe.description + "<br/>" +
                 (cafe.profilAromatique? "Profil aromatique : " + cafe.profilAromatique  + "<br/>" : "")+
                "Intensité : " + cafe.intensite
        });
    };
});

