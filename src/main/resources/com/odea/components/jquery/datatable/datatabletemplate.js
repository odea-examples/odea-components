var oTable;

$(document).ready(function () {
    oTable = $('${selector}').dataTable({
        "oLanguage":{
            "sLengthMenu":"Mostrar _MENU_ registros por p&aacute;gina",
            "sZeroRecords":"No se encontro ningun resultado",
            "sInfo":"Mostrando del _START_ al _END_ de _TOTAL_ registros",
            "sInfoEmpty":"Mostrando del 0 al 0 de 0 registros",
            "sInfoFiltered":"(filtrado de _MAX_ registros totales)",
            "oPaginate": {
                "sPrevious": "Anterior",
                "sNext": "Siguiente"
            }
        },

        "bSortClasses": false,
        "bFilter": true ,
        "bProcessing": true,
        "sAjaxSource": '${callbackUrl}',
        "bDeferRender": true ,
        "aoColumns": ${columns}

    });

});