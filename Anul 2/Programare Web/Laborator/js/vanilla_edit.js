document.addEventListener('DOMContentLoaded', function() {
    var selector = document.getElementById('log-selector');
    var saveBtn = document.getElementById('save-btn');
    var form = document.getElementById('edit-form');
    var originalData = {};
    var isDirty = false;

    fetch('/php/logs_edit_api.php')
        .then(res => res.json())
        .then(ids => {
            ids.forEach(item => {
                var opt = document.createElement('option');
                opt.value = item.id;
                opt.textContent = "ID: " + item.id;
                selector.appendChild(opt);
            });
        });

    selector.addEventListener('change', function(e) {
        if (isDirty && !confirm("Ai modificări nesalvate. Ești sigur că vrei să continui?")) {
            e.target.value = originalData.id;
            return;
        }
        
        if (this.value) {
            fetch('/php/logs_edit_api.php?id=' + this.value)
                .then(res => res.json())
                .then(data => {
                    originalData = data;
                    document.getElementById('log-id').value = data.id;
                    document.getElementById('log-event').value = data.event;
                    document.getElementById('log-time').value = data.time;
                    saveBtn.disabled = true;
                    isDirty = false;
                });
        }
    });

    form.addEventListener('input', function() {
        var currentEvent = document.getElementById('log-event').value;
        var currentTime = document.getElementById('log-time').value;
        
        isDirty = (currentEvent !== originalData.event || currentTime !== originalData.time);
        saveBtn.disabled = !isDirty;
    });

    saveBtn.addEventListener('click', function() {
        var payload = {
            id: document.getElementById('log-id').value,
            event: document.getElementById('log-event').value,
            time: document.getElementById('log-time').value
        };

        fetch('/php/logs_edit_api.php', {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {'Content-Type': 'application/json'}
        }).then(() => {
            alert("Salvat cu succes!");
            originalData = payload; 
            saveBtn.disabled = true;
            isDirty = false;
        });
    });
});