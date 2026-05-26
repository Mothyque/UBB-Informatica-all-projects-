$(document).ready(function() {
    var originalData = {};
    var isDirty = false;

    $.ajax({
        url: '/php/logs_edit_api.php',
        method: 'GET',
        dataType: 'json',
        success: function(ids) {
            $.each(ids, function(index, item) {
                $('#log-selector').append('<option value="' + item.id + '">ID: ' + item.id + '</option>');
            });
        }
    });

    $('#log-selector').on('change', function() {
        var selectedId = $(this).val();
        
        if (isDirty && !confirm("Ai modificări nesalvate. Sigur continui?")) {
            $(this).val(originalData.id);
            return;
        }

        if (selectedId) {
            $.ajax({
                url: '/php/logs_edit_api.php',
                method: 'GET',
                data: { id: selectedId },
                dataType: 'json',
                success: function(data) {
                    originalData = data;
                    $('#log-id').val(data.id);
                    $('#log-event').val(data.event);
                    $('#log-time').val(data.time);
                    $('#save-btn').prop('disabled', true);
                    isDirty = false;
                }
            });
        }
    });

    $('#edit-form input').on('input', function() {
        isDirty = ($('#log-event').val() !== originalData.event || 
                   $('#log-time').val() !== originalData.time);
        $('#save-btn').prop('disabled', !isDirty);
    });

    $('#save-btn').on('click', function() {
        $.ajax({
            url: '/php/logs_edit_api.php',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                id: $('#log-id').val(),
                event: $('#log-event').val(),
                time: $('#log-time').val()
            }),
            success: function() {
                alert("Salvat cu succes!");
                originalData = { 
                    id: $('#log-id').val(), 
                    event: $('#log-event').val(), 
                    time: $('#log-time').val() 
                };
                $('#save-btn').prop('disabled', true);
                isDirty = false;
            }
        });
    });
});