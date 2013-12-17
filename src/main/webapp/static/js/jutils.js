//Scope de l'application
var jUtils = {

    /**
     * Racine du contexte de l'application
     */
    contextRoot: "/jutils",

    /**
     * Messages d'erreurs par défaut
     */
    defaultMessage: {
        error: "Une erreur inconnue est survenue",
        success: "Opération effectuée avec succès"
    },

    /**
     * Timeout par défaut en ms
     */
    defaultTimeout: 10000,

    /**
     * Timeout courant
     */
    currentTimeout: 0,

    /**
     * Affiche un message de succès dans le bandeau
     * @param [message] à afficher
     */
    displaySuccess: function (message) {
        "use strict";
        var alertDiv = $("#alert"), alertIcon = alertDiv.children('i');
        if (!message) {
            message = "Opération effectuée avec succès";
        }
        clearTimeout(jUtils.currentTimeout);
        alertDiv.children('span').text(message);
        if (alertDiv.hasClass('alert-error')) {
            alertDiv.removeClass('alert-error');
        }
        if (!alertDiv.hasClass('alert-success')) {
            alertDiv.addClass('alert-success');
        }
        if (alertIcon.hasClass('icon-warning-sign')) {
            alertIcon.removeClass('icon-warning-sign');
        }
        if (!alertIcon.hasClass('icon-ok')) {
            alertIcon.addClass('icon-ok');
        }
        alertDiv.addClass('in');
        jUtils.currentTimeout = window.setTimeout(function () {
            alertDiv.removeClass('in');
        }, jUtils.defaultTimeout);
    },

    /**
     * Affiche un message d'erreur dans le bandeau
     * @param [response] ResponseMessage contenant le message
     */
    displayError: function (response) {
        "use strict";
        var alertDiv = $("#alert"), alertIcon = alertDiv.children('i'), message;
        message = response && response.message ? response.message : "Une erreur inconnue est survenue";
        clearTimeout(jUtils.currentTimeout);
        alertDiv.children('span').text(message);
        if (alertDiv.hasClass('alert-success')) {
            alertDiv.removeClass('alert-success');
        }
        if (!alertDiv.hasClass('alert-error')) {
            alertDiv.addClass('alert-error');
        }
        if (alertIcon.hasClass('icon-ok')) {
            alertIcon.removeClass('icon-ok');
        }
        if (!alertIcon.hasClass('icon-warning-sign')) {
            alertIcon.addClass('icon-warning-sign');
        }
        alertDiv.addClass('in');
    },

    /**
     * Enregistrement la ligne avec jQuery.data
     * @param id identifiant de la ligne
     * @param event évennement
     */
    storeCurrentRow: function (id, event) {
        "use strict";
        var table = $('table[id^=dt_]');
        table.data("currentRowId", id);
        table.data("currentRow", event.parents('tr')[0]);
    },

    /**
     * Supprime la ligne de jQuery.data
     */
    removeCurrentRow: function () {
        "use strict";
        var table = $('table[id^=dt_]');
        table.removeData("currentRowId");
        table.removeData("currentRow");
    },

    /**
     * Renvoi la ligne courante
     * @returns {*}
     */
    getCurrentRow: function () {
        "use strict";
        return $('table[id^=dt_]').data("currentRow");
    },

    /**
     * Renvoi l'identifiant de la ligne courante
     * @returns {*}
     */
    getCurrentRowId: function () {
        "use strict";
        return $('table[id^=dt_]').data("currentRowId");
    },

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
    },

    /**
     * Affiche le contenu de la colonne action. Appelle une popup pour l'édition de la ligne
     * @returns {{mData: string, sWidth: number, bSearchable: boolean, bSortable: boolean, sClass: string, mRender: Function}}
     */
    actionPopup: function () {
        "use strict";
        return {
            mData: "id",
            sWidth: 25,
            bSearchable: false,
            bSortable: false,
            sClass: "center",
            mRender: function (data) {
                var btnEdit, fct, btnDelete;
                btnEdit = "<a href='javascript:;' class='edit-btn' " +
                    "onclick='jUtils.displayEditPopup(" + data + ",$(this))'><i class='icon-edit icon-large'></i></a>";
                fct = "jUtils.displayConfirmPopup(" + data + ",$(this))";
                btnDelete = "<a href='javascript:;' onclick='" + fct + "'><i class='icon-trash icon-large pull-right'></i></a>";
                return btnEdit + btnDelete;
            }
        };
    },

    /**
     * Affiche le contenu de la colonne action. Appelle une nouvelle page pour l'édition d'une ligne
     * @returns {{mData: string, sWidth: number, bSearchable: boolean, bSortable: boolean, sClass: string, mRender: Function}}
     */
    actionEdit: function () {
        "use strict";
        return {
            mData: "id",
            sWidth: 25,
            bSearchable: false,
            bSortable: false,
            sClass: "center",
            mRender: function (data) {
                var path = window.location.pathname, editUrl, btnEdit, fct, btnDelete;
                editUrl = path.match(/\/$/) ? path + data : path + "/" + data;
                btnEdit = "<a href='" + editUrl + "'><i class='icon-edit icon-large'></i></a>";
                fct = "jUtils.displayConfirmPopup(" + data + ",$(this))";
                btnDelete = "<a href='javascript:;' onclick='" + fct + "'><i class='icon-trash icon-large pull-right'></i></a>";
                return btnEdit + btnDelete;
            }
        };
    },

    /**
     * Affiche le contenu d'une colonne de détail avec débranchement vers écran détail
     * @returns {{mData: string, sWidth: number, bSearchable: boolean, bSortable: boolean, sClass: string, mRender: Function}}
     */
    detailCol: function () {
        "use strict";
        return {
            mData: "id",
            sWidth: 25,
            bSearchable: false,
            bSortable: false,
            sClass: "center",
            mRender: function (data) {
                var path = window.location.pathname, detailUrl;
                detailUrl = path.match(/\/$/) ? path + data + "/detail" : path + "/" + data + "/detail";
                return "<a href='" + detailUrl + "'><i class='icon-search icon-large'></i></a>";
            }
        };
    },

    /**
     * Affiche le contenu d'une colonne de modification avec débranchement vers une popup
     * @returns {{mData: string, sWidth: number, bSearchable: boolean, bSortable: boolean, sClass: string, mRender: Function}}
     */
    popupCol: function (){
        "use strict";
        return {
            mData: "id",
            sWidth: 25,
            bSearchable: false,
            bSortable: false,
            sClass: "center",
            mRender: function (data) {
                var fct = "jUtils.displayEditPopup(" + data + ", $(this))";
                return "<a href='javascript:;' class='edit-btn' onclick='" + fct + "'><i class='icon-edit icon-large'></i></a>";
            }
        };
    },

    /**
     * Affiche le contenu d'une colonne de édition avec débranchement vers écran détail
     * @returns {{mData: string, sWidth: number, bSearchable: boolean, bSortable: boolean, sClass: string, mRender: Function}}
     */
    editCol: function () {
        "use strict";
        return {
            mData: "id",
            sWidth: 25,
            bSearchable: false,
            bSortable: false,
            sClass: "center",
            mRender: function (data) {
                var path = window.location.pathname, editUrl;
                editUrl = path.match(/\/$/) ? path + data : path + "/" + data;
                return "<a class='edit-btn' href='" + editUrl + "'><i class='icon-edit icon-large'></i></a>";
            }
        };
    },

    /**
     * Affiche le contenu d'une colonne de suppression
     * @returns {{mData: string, sWidth: number, bSearchable: boolean, bSortable: boolean, sClass: string, mRender: Function}}
     */
    deleteCol: function () {
        "use strict";
        return {
            mData: "id",
            sWidth: 25,
            bSearchable: false,
            bSortable: false,
            sClass: "center",
            mRender: function (data) {
                var fct = "jUtils.displayConfirmPopup(" + data + ",$(this))";
                return "<a href='javascript:;' onclick='" + fct + "'><i class='icon-trash icon-large'></i></a>";
            }
        };
    },

    /**
     * Affiche un bouton dans une colonne
     * @param icon icon à afficher
     * @param text texte à afficher
     * @returns {{mData: string, sWidth: number, bSearchable: boolean, bSortable: boolean, sClass: string, mRender: Function}}
     */
    button: function (icon, text) {
        "use strict";
        return {
            mData: "id",
            sWidth: 80,
            bSearchable: false,
            bSortable: false,
            sClass: "center",
            mRender: function (data) {
                var link = window.location.pathname + "/" + data;
                return '<a class="btn btn-primary" href="' + link + '"><i class="' + icon + '"></i> ' + text + '</a>';
            }
        };
    },

    /**
     * Affiche la popup de confirmation d'une suppression de ligne
     * @param id identifiant de la ligne à supprimer
     * @param event évennement
     */
    displayConfirmPopup: function (id, event) {
        "use strict";
        jUtils.storeCurrentRow(id, event);
        $('#confirmDeletePopup').modal();
    },

    /**
     * Affiche la popup d'édition d'une ligne
     * @param id identifiant de la ligne à éditer
     * @param event évennement
     */
    displayEditPopup: function (id, event) {
        "use strict";
        var dataUrl, popup = $("#popup");
        $('#popupTitle').text($('#editTitle').val());
        popup.modal();
        if (window.location.pathname.match(/\/$/)) {
            dataUrl = window.location.pathname + id + "/popup";
        } else {
            dataUrl = window.location.pathname + "/" + id + "/popup";
        }
        $.ajax(dataUrl).done(function (response) {
            var field, data = response.data;
            if (response.success) {
                for (field in data) {
                    if (data.hasOwnProperty(field)) {
                        $('#popupForm').find('input[name="' + field + '"]').val(data[field]);
                    }
                }
                jUtils.storeCurrentRow(id, event);
            } else {
                jUtils.displayError(response);
                popup.modal('hide');
            }
        })
            .error(function (response) {
                if (oTable) {
                    oTable.fnReloadAjax();
                } else {
                    $('table[id^=dt_]').dataTable().fnReloadAjax();
                }
                jUtils.displayError(response);
            });
    },

    /**
     * Edition d'une ligne de la table via envoi ajax
     * @param form formulaire a envoyer
     * @param datatable table surlaquelle les modifications seront effectuées
     */
    saveRow: function (form, datatable) {
        "use strict";
        $(form).ajaxSubmit({
            success: function (response) {
                if (response.success) {
                    if (jUtils.getCurrentRow()) {
                        datatable.fnDeleteRow(jUtils.getCurrentRow());
                    }
                    datatable.fnAddData(response.data);
                    jUtils.displaySuccess(response.message);
                } else {
                    jUtils.displayError(response);
                }
                $('#popup').modal('hide');
            },
            error: function (response) {
                datatable.fnReloadAjax();
                if (response.message) {
                    jUtils.displayError(response);
                } else {
                    jUtils.displayError();
                }
                $('#popup').modal('hide');
            }
        });
    },

    /**
     * Appel ajax du serveur pour supprimer une ligne de la table
     * @param [url] url à appeler en cas méthode spécifique pour la suppression
     */
    remove: function (url) {
        "use strict";
        var dataTable, removeUrl;
        if (oTable) {
            dataTable = oTable;
        } else {
            dataTable = $('table[id^=dt_]').dataTable();
        }
        if (url) {
            removeUrl = url;
        } else {
            if (window.location.pathname.match(/\/$/)) {
                removeUrl = window.location.pathname + jUtils.getCurrentRowId();
            } else {
                removeUrl = window.location.pathname + "/" + jUtils.getCurrentRowId();
            }
        }
        $.ajax({
            type: 'DELETE',
            url: removeUrl
        }).done(
            function (response) {
                if (response.success) {
                    if (!dataTable.fnDeleteRow(jUtils.getCurrentRow())) {
                        dataTable.fnReloadAjax();
                    }
                    jUtils.displaySuccess(response.message);
                } else {
                    dataTable.fnReloadAjax();
                    jUtils.displayError(response.message);
                }
                $('#confirmDeletePopup').modal('hide');
            }
        ).error(
            function (response) {
                dataTable.fnReloadAjax();
                if (response.message) {
                    jUtils.displayError(response.message);
                } else {
                    jUtils.displayError();
                }
                $('#confirmDeletePopup').modal('hide');
            }
        );
    },

    /**
     * Retourne à l'écran liste
     */
    back: function () {
        "use strict";
        window.location.pathname = window.location.pathname.substring(0, window.location.pathname.lastIndexOf("/"));
    },

    /**
     * Redirige vers l'écran de création d'une entité
     */
    detailNew: function () {
        "use strict";
        var path = window.location.pathname;
        window.location.pathname = path.match(/\/$/) ? path + "new" : path + "/" + "new";
    },

    /**
     * Affiche la popup de création d'une nouvelle entité
     */
    popupNew: function () {
        "use strict";
        $('#popupTitle').text($('#newTitle').val());
        $('#popup').modal('show');
    }
};


/**
 * Au chargement de la page
 */
$(function () {
    "use strict";
    var alert = $('#alert');

    //Fermeture automatique des alertes
    if (alert.is(':visible') && alert.hasClass('success')) {
        setTimeout(function () {
            $('#alert').removeClass('in');
            clearTimeout(jUtils.currentTimeout);
        }, jUtils.defaultTimeout);
    }

    //Fermeture des alertes après click sur 'close'
    $('#close-alert').click(function () {
        $(this).parent('.alert').removeClass('in');
    });

    //Activation des tooltips
    $('a[data-toggle=tooltip]').tooltip();

    //Fermeture de la popup d'ajout/édition
    $('#popup').on('hidden', function () {
        var form = $('#popupForm');
        if (popupForm) {
            popupForm.resetForm();
        } else {
            form.validate().resetForm();
        }
        form[0].reset();
        form.find('.control-group').removeClass('error');
        form.find(':input').removeData("previousValue");
        $("#id").val("");
        $("#version").val("");
        jUtils.removeCurrentRow();
    });

    //Fermeture de la popup de confirmation
    $('#confirmDeletePopup').on('hidden', function () {
        jUtils.removeCurrentRow();
    });
});

