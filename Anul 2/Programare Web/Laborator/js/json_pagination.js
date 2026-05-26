document.addEventListener('DOMContentLoaded', function() {
    var RECORDS_PER_PAGE = 5; 
    var currentPage = 1;
    var totalPages = 1;

    var tbody = document.getElementById('logs-body');
    var btnPrev = document.getElementById('btn-prev');
    var btnNext = document.getElementById('btn-next');
    var pageInfo = document.getElementById('page-info');
    var errBanner = document.getElementById('error-banner');

    function showError(msg) {
        errBanner.textContent = msg;
        errBanner.style.display = 'block';
    }

    function hideError() {
        errBanner.style.display = 'none';
        errBanner.textContent = '';
    }

    function renderRows(records) {
        tbody.innerHTML = ''; 

        for (var i = 0; i < records.length; i++) {
            var row = records[i];
            var tr = document.createElement('tr');

            var tdId = document.createElement('td');
            var tdEvent = document.createElement('td');
            var tdTime = document.createElement('td');

            tdId.textContent = row.id; 
            tdEvent.textContent = row.event;
            tdTime.textContent = row.time;

            tr.appendChild(tdId); 
            tr.appendChild(tdEvent);
            tr.appendChild(tdTime);
            
            tbody.appendChild(tr);
        }
    }

    function updateControls() {
        btnPrev.disabled = (currentPage <= 1);
        
        btnNext.disabled = (currentPage >= totalPages);
        
        pageInfo.textContent = 'Pagina ' + currentPage + ' / ' + totalPages;
    }

    function loadPage(page) {
        hideError();
        
        btnPrev.disabled = true;
        btnNext.disabled = true;

        var url = '../php/logs_json.php?page=' + page + '&per_page=' + RECORDS_PER_PAGE;
        
        var xhr = new XMLHttpRequest(); 
        xhr.open('GET', url, true); 

        xhr.onload = function() {
            if (xhr.status >= 200 && xhr.status < 300) {
                try {
                    var data = JSON.parse(xhr.responseText);
                    currentPage = data.page;
                    totalPages = data.total_pages;
                    
                    renderRows(data.records);
                    updateControls();
                } catch(e) {
                    showError('Eroare la procesarea datelor de la server.');
                    updateControls();
                }
            } else {
                var errorMessage = 'Eroare HTTP: ' + xhr.status;
                try {
                    var errorData = JSON.parse(xhr.responseText);
                    if (errorData.error) {
                        errorMessage += " -> " + errorData.error;
                    }
                } catch(e) {
                    errorMessage += " -> Răspunsul serverului nu e JSON: " + xhr.responseText;
                }
                
                showError(errorMessage);
                updateControls();
            }
        };
        xhr.onerror = function() {
            showError('Conexiunea a picat! Verificați-vă conexiunea la Internet.');
            updateControls();
        };

        xhr.timeout = 5000; 
        xhr.ontimeout = function() {
            showError('Serverul nu a răspuns la timp. Încercați mai târziu.');
            updateControls();
        };

        xhr.send();
    }

    btnPrev.addEventListener('click', function() {
        if (currentPage > 1) {
            loadPage(currentPage - 1);
        }
    });

    btnNext.addEventListener('click', function() {
        if (currentPage < totalPages) {
            loadPage(currentPage + 1);
        }
    });

    loadPage(1);
});