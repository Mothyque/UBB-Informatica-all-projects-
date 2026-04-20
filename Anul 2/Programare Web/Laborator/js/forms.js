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

    const echipe = document.querySelectorAll('.echipa-lista');
    echipe.forEach(item => {
        const titlu = item.querySelector('strong');
        titlu.addEventListener('click', () => {
            item.classList.toggle('expandat');
        })
    })

});

document.addEventListener("DOMContentLoaded", function() {
    renderTable(euroleagueStandings);
    renderVerticalTable(euroleagueStandings);
});

function renderTable(data) {
    const body = document.getElementById("standingsBody");
    if (!body) return;
    
    body.innerHTML = "";
    
    data.forEach(item => {
        const row = `<tr>
            <td>${item.pozitie}</td>
            <td>${item.nume}</td>
            <td><img src="${item.logo}" width="200" height="200" alt="${item.nume} logo" /></td>
            <td>${item.victorii}</td>
            <td>${item.infrangeri}</td>
        </tr>`;
        body.innerHTML += row;
    });
}

function renderVerticalTable(data) {
    const rowNume = document.getElementById("row-nume");
    const rowVictorii = document.getElementById("row-victorii");

    if (!rowNume || !rowVictorii) return;

    rowNume.innerHTML = `<th onclick="sortVerticalTable('nume')">Echipa (Sortează)</th>`;
    rowVictorii.innerHTML = `<th onclick="sortVerticalTable('victorii')">Victorii (Sortează)</th>`;

    data.forEach(item => {
        const cellNume = `<td>${item.nume}</td>`;
        const cellVictorii = `<td>${item.victorii}</td>`;

        rowNume.innerHTML += cellNume;
        rowVictorii.innerHTML += cellVictorii;
    });
}

let sortDirection = true;
function sortTable(columnIndex) {
    const keys = ["pozitie", "nume", "victorii", "infrangeri"];
    const key = keys[columnIndex];
    
    euroleagueStandings.sort((a, b) => {
        let valA = a[key];
        let valB = b[key];
        
        if (typeof valA === 'string') {
            return sortDirection ? valA.localeCompare(valB) : valB.localeCompare(valA);
        } else {
            return sortDirection ? valA - valB : valB - valA;
        }
    });
    
    sortDirection = !sortDirection;
    renderTable(euroleagueStandings);
    
    const headers = document.querySelectorAll("#standingsTable th");
    headers.forEach(h => h.style.backgroundColor = ""); 
    headers[columnIndex].style.backgroundColor = "#ff5c00"; 
}

let vertSortDir = true;
function sortVerticalTable(criteriu) {
    euroleagueStandings.sort((a, b) =>{
        let valA = a[criteriu];
        let valB = b[criteriu];

        if (typeof valA === 'string') {
            return vertSortDir ? valA.localeCompare(valB) : valB.localeCompare(valA);
        } 
        else {
            return vertSortDir ? valA - valB : valB - valA;
        }
    });

    vertSortDir = !vertSortDir;
    renderVerticalTable(euroleagueStandings);

    const allVerticalsRows = document.querySelectorAll("#verticalStandingsTable tr");
    allVerticalsRows.forEach(row => row.style.backgroundColor = "");

    const selectedRow = document.getElementById(`row-${criteriu}`);
    if (selectedRow) {
        selectedRow.querySelector('th').style.backgroundColor = "#ff5c00";
    }
}


let currentSlide = 0;

function updateCarousel(){
    const slide = carouselData[currentSlide];
    const linkElement = document.getElementById("carousel-link");
    const textElement = document.getElementById("carousel-text");
    const imgElement = document.getElementById("carousel-image");

    if (linkElement && textElement && imgElement) {
        linkElement.href = slide.link;
        textElement.innerText = slide.text;
        imgElement.style.backgroundImage = `url('${slide.img}')`;
    }
}

function changeSlide(direction){
    currentSlide += direction;
    if (currentSlide >= carouselData.length) {
        currentSlide = 0;
    }
    if (currentSlide < 0) {
        currentSlide = carouselData.length - 1;
    }
    updateCarousel();
}

let autoSlide = setInterval(() => {
    changeSlide(1);
}, 5000);

function manualChange(direction){
    clearInterval(autoSlide);
    changeSlide(direction);
    autoSlide = setInterval(() => {
        changeSlide(1);
    }, 5000);
}

window.addEventListener('load', updateCarousel);

let currentMatch = null;

function loadNewQuiz(){

    const quizOptions = document.getElementById("quiz-options");
    if (!quizOptions) return;

    document.getElementById("quiz-feedback").innerText = ""

    const randomIndex = Math.floor(Math.random() * quizMatches.length);
    currentMatch = quizMatches[randomIndex];

    document.getElementById("quiz-date").innerText = currentMatch.data;
    document.getElementById("name1").innerText = currentMatch.echipa1;
    document.getElementById("name2").innerText = currentMatch.echipa2;
    document.getElementById("logo1").src = currentMatch.logo1;
    document.getElementById("logo2").src = currentMatch.logo2;

    let shuffledOptions = [...currentMatch.variante];
    for (let i = shuffledOptions.length - 1; i > 0; i--){
        const j = Math.floor(Math.random() * (i + 1));
        [shuffledOptions[i], shuffledOptions[j]] = [shuffledOptions[j], shuffledOptions[i]];
    }

    const optionsContainer = document.getElementById("quiz-options");
    optionsContainer.innerHTML = "";

    shuffledOptions.forEach(option => {
        const button = document.createElement("button");
        button.innerText = option;
        button.classList.add("quiz-btn");
        button.onclick = function() { checkAnswer(this, option); };
        optionsContainer.appendChild(button);
    });
}

function checkAnswer(selectedBtn, chosenOption) {
    const allButtons = document.querySelectorAll(".quiz-btn");
    allButtons.forEach(btn => btn.disabled = true);

    if (chosenOption === currentMatch.scorCorect) {
        selectedBtn.classList.add("correct");
        document.getElementById("quiz-feedback").innerHTML = "<p style='color:green; font-weight:bold;'>Corect!</p>";
    } 
    else {
        selectedBtn.classList.add("wrong");
        allButtons.forEach(btn => {
            if (btn.innerText === currentMatch.scorCorect) {
                btn.classList.add("correct");
            }
        });
        document.getElementById("quiz-feedback").innerHTML = "<p style='color:red; font-weight:bold;'>Greșit! Răspunsul corect este: " + currentMatch.scorCorect + "</p>";
    }

    setTimeout(() => {
        loadNewQuiz();
    }, 5000);
}

document.addEventListener("DOMContentLoaded", function(){
    loadNewQuiz();


    const quizBox = document.getElementById("quiz-content-wrapper");
    const showBtn = document.getElementById("quiz-show-btn");
    const hideBtn = document.getElementById("quiz-hide-btn");

    console.log("Quiz Box gasit: ", quizBox);
    console.log("Show Button gasit: ", showBtn);
    console.log("Hide Button gasit: ", hideBtn);

    if (quizBox && showBtn && hideBtn) {
        hideBtn.addEventListener("click", function(){
            quizBox.style.display = "none";
            showBtn.style.display = "block";
        });
        showBtn.addEventListener("click", function(){
            quizBox.style.display = "block";
            showBtn.style.display = "none";
        });
    }
});