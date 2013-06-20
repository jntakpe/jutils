//Scope de l'application
var jUtils = {
    ajax: {
        /**
         * Ajoute l'identifiant des formulaires aux requêtes de contrôle 'remote' de jQuery validation.
         * @param url de la fonction de contrôle
         * @returns {{url: *, data: {id: (*|jQuery)}}}
         */
        control: function (url) {
            "use strict";
            return {
                url: url,
                data: {
                    id: $('#id').val()
                }
            };
        }
    }
};