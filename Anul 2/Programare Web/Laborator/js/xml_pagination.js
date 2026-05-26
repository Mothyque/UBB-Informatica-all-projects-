document.addEventListener('DOMContentLoaded', function() {
    var RECORDS_PER_PAGE = 5;
    var currentPage = 1;
    var totalPages = 1;

    var tbody = document.getElementById('logs-body');
    var btnPrev = document.getElementById('btn-prev');
    var btnNext = document.getElementById('btn-next');
    var pageInfo = document.getElementById('page-info');
    var errBanner = document.getElementById('error-banner');

    function loadPage(page) {
        var url = '/php/logs_xml.php?page=' + page + '&per_page=' + RECORDS_PER_PAGE;
        var xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);

        xhr.onload = function() {
            if (xhr.status === 200) {
                var xml = xhr.responseXML; 

                if (!xml) {
                    errBanner.textContent = 'Răspunsul XML nu a putut fi parsat de browser.';
                    errBanner.style.display = 'block';
                    return; 
                }

                var pageNodes = xml.getElementsByTagName('page');
                var totalPagesNodes = xml.getElementsByTagName('total_pages');
                var recordNodes = xml.getElementsByTagName('record');

                if (!pageNodes.length || !totalPagesNodes.length) {
                    errBanner.textContent = 'Structura răspunsului XML este invalidă.';
                    errBanner.style.display = 'block';
                    return;
                }

                currentPage = parseInt(pageNodes[0].textContent, 10);
                totalPages = parseInt(totalPagesNodes[0].textContent, 10);
                
                tbody.innerHTML = '';
                
                for (var i = 0; i < recordNodes.length; i++) {
                    var id = recordNodes[i].getElementsByTagName('id')[0].textContent;
                    var event = recordNodes[i].getElementsByTagName('event')[0].textContent;
                    var time = recordNodes[i].getElementsByTagName('time')[0].textContent;
                    
                    tbody.innerHTML += '<tr><td>'+id+'</td><td>'+event+'</td><td>'+time+'</td></tr>';
                }
                
                btnPrev.disabled = (currentPage <= 1);
                btnNext.disabled = (currentPage >= totalPages);
                pageInfo.textContent = 'Pagina ' + currentPage + ' din ' + totalPages;
            } else {
                errBanner.textContent = "Eroare la încărcarea XML.";
                errBanner.style.display = 'block';
            }
        };
        xhr.send();
    }

    btnPrev.addEventListener('click', function() { if(currentPage > 1) loadPage(currentPage - 1); });
    btnNext.addEventListener('click', function() { if(currentPage < totalPages) loadPage(currentPage + 1); });

    loadPage(1);
});