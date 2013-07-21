var richMail = angular.module('richMail', []);

function MailCtrl($scope) {
    "use strict";
    var sopraText, otherText;

    sopraText = "Création d'un mail avec émetteur SopraGroup ou SopraBanking";
    otherText = "Création d'un mail avec émetteur différent de Sopra";

    $scope.showSopra = true;
    $scope.legendText = sopraText;

    $scope.enableSopra = function () {
        $scope.showSopra = true;
        $scope.legendText = sopraText;
    };

    $scope.enableOther = function () {
        $scope.showSopra = false;
        $scope.legendText = otherText;
    };
}