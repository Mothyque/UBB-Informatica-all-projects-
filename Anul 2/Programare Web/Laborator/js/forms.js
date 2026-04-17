document.addEventListener("DOMContentLoaded", function() {
    const adminForm = document.getElementById("adminForm");
    if (adminForm) {
        adminForm.addEventListener("submit", function(event) {
            event.preventDefault();
            let isValid = true;

            const nameInput = document.querySelector('input[name="Player Name"]');
            if (nameInput.value.trim() === "" || nameInput.value.length < 3) {
                nameInput.style.border = "2px solid red";
                isValid = false;
            } 
            else {
                nameInput.style.border = "1px solid #ccc"; 
            }
            const heightInput = document.querySelector('input[name="Player Height"]');
            const height = parseInt(heightInput.value);
            
            const positionRadios = document.querySelectorAll('input[name="Player Position"]');
            let selectedPosition = "";
            positionRadios.forEach(radio => {
                if (radio.checked) selectedPosition = radio.value;
            });

            if (isNaN(height) || height < 150 || height > 230) {
                heightInput.style.border = "2px solid red";
                isValid = false;
            } 
            else if (selectedPosition === "C" && height < 200) {
                alert("Un jucător pe poziția de Centru (C) trebuie să aibă minim 200cm!");
                heightInput.style.border = "2px solid red";
                isValid = false;
            } 
            else if (selectedPosition === "PG" && height < 175) {
                alert("Un jucător pe poziția de Point Guard (PG) trebuie să aibă minim 175cm!");
                heightInput.style.border = "2px solid red";
                isValid = false;
            }
            else if (selectedPosition === "SG" && height < 190) {
                alert("Un jucător pe poziția de Shooting Guard (SG) trebuie să aibă minim 190cm!");
                heightInput.style.border = "2px solid red";
                isValid = false;
            }
            else if (selectedPosition === "SF" && height < 193) {
                alert("Un jucător pe poziția de Small Forward (SF) trebuie să aibă minim 193cm!");
                heightInput.style.border = "2px solid red";
                isValid = false;
            }
            else if (selectedPosition === "PF" && height < 195) {
                alert("Un jucător pe poziția de Power Forward (PF) trebuie să aibă minim 195cm!");
                heightInput.style.border = "2px solid red";
                isValid = false;
            }
            else {
                heightInput.style.border = "1px solid #ccc";
            }
            if (isValid) {
                alert("Jucător adăugat cu succes!");
            } 
        });
    }

    const ticketsForm = document.getElementById("ticketsForm");
    const countrySelect = document.getElementById("countrySelect");
    const teamSelect = document.getElementById("teamSelect");

    if (countrySelect && teamSelect) {
        countrySelect.addEventListener("change", function() {
            const selectedCountry = this.value;
            const teams = teamsByCountry[selectedCountry] || []; 

            teamSelect.innerHTML = '<option value="">-- Alege o echipă --</option>';
            
            teams.forEach(function(team) {
                const option = document.createElement("option");
                option.value = team;
                option.textContent = team;
                teamSelect.appendChild(option);
            });
        });
    }

    if (ticketsForm) {
        ticketsForm.addEventListener("submit", function(event) {
            event.preventDefault();
            let isValid = true;

            const ticketCount = document.getElementById("ticketCount");
            if (ticketCount.value === "" || ticketCount.value < 1 || ticketCount.value > 5) {
                ticketCount.style.border = "2px solid red";
                isValid = false;
            } 
            else {
                ticketCount.style.border = "1px solid #ccc";
            }

            const ticketEmail = document.getElementById("ticketEmail");
            if (!ticketEmail.value.includes("@")) {
                ticketEmail.style.border = "2px solid red";
                isValid = false;
            } 
            else {
                ticketEmail.style.border = "1px solid #ccc";
            }

            if (isValid) alert("Biletele au fost rezervate cu succes!");
            else alert("Verificați câmpurile roșii!");
        });
    }

    const contactForm = document.getElementById("contactForm");
    if (contactForm) {
        contactForm.addEventListener("submit", function(event) {
            event.preventDefault();
            let isValid = true;

            const password = document.getElementById("scoutPassword");
            if (password.value.length < 6) {
                password.style.border = "2px solid red";
                isValid = false;
            } 
            else {
                password.style.border = "1px solid #ccc";
            }

            const terms = document.getElementById("scoutTerms");
            if (!terms.checked) {
                terms.style.outline = "2px solid red";
                isValid = false;
            }
            else {
                terms.style.outline = "none";
            }

            if (isValid) alert("Aplicația a fost trimisă!");
            else alert("Atenție: Parola prea scurtă sau termenii ne-bifați (marcaj roșu)!");
        });
    }

});