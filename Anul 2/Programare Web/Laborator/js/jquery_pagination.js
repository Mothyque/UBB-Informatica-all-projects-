$(document).ready(function() {
    var RECORDS_PER_PAGE = 5; 
    var currentPage = 1;
    var totalPages = 1;

    function loadPage(page) {
        $.ajax({
            url: '../php/logs_json.php',
            method: 'GET',
            data: { page: page, per_page: RECORDS_PER_PAGE },
            dataType: 'json',
            beforeSend: function() {
                $('#btn-prev, #btn-next').prop('disabled', true);
                $('#error-banner').hide().text('');
            },
            success: function(data) {
                currentPage = data.page;
                totalPages = data.total_pages;

                var $tbody = $('#logs-body');
                $tbody.empty();

                $.each(data.records, function(index, row) {
                    $tbody.append(
                        '<tr>' + 
                            '<td>' + row.id + '</td>' +
                            '<td>' + row.event + '</td>' +
                            '<td>' + row.time + '</td>' +
                        '</tr>'
                    );
                });
                
                $('#btn-prev').prop('disabled', currentPage <= 1);
                $('#btn-next').prop('disabled', currentPage >= totalPages);
                $('#page-info').text('Pagina ' + currentPage + ' / ' + totalPages);
            },
            error: function(xhr, status, error) {
                $('#error-banner').text('Eroare la încărcarea datelor.' + error).show();
                $('#btn-prev, #btn-next').prop('disabled', false);
            }
        });
    }
    
    $('#btn-prev').click(function() { if (currentPage > 1) loadPage(currentPage - 1); });
    $('#btn-next').click(function() { if (currentPage < totalPages) loadPage(currentPage + 1); });

    loadPage(1);
});