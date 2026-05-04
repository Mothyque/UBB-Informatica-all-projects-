$(document).ready(function() {

    const $adminForm = $("#adminForm");
    if ($adminForm.length) {
        $adminForm.on("submit", function(event) {
            event.preventDefault();
            let isValid = true;
            
            const $nameInput = $('input[name="Player Name"]');
            if ($nameInput.val().trim() === "" || $nameInput.val().length < 3) {
                $nameInput.css("border", "2px solid red");
                isValid = false;
            } else {
                $nameInput.css("border", "1px solid #ff5c00");
            }

            const $heightInput = $('input[name="Player Height"]');
            const height = parseInt($heightInput.val());
            const selectedPosition = $('input[name="Player Position"]:checked').val();

            if (isNaN(height) || height < 150 || height > 230) {
                $heightInput.css("border", "2px solid red");
                isValid = false;
            } else if (selectedPosition === "C" && height < 200) {
                alert("Un jucător pe poziția de Centru (C) trebuie să aibă minim 200cm!");
                $heightInput.css("border", "2px solid red");
                isValid = false;
            } else if (selectedPosition === "PG" && height < 175) {
                alert("Un jucător pe poziția de Point Guard (PG) trebuie să aibă minim 175cm!");
                $heightInput.css("border", "2px solid red");
                isValid = false;
            } else if (selectedPosition === "SG" && height < 190) {
                alert("Un jucător pe poziția de Shooting Guard (SG) trebuie să aibă minim 190cm!");
                $heightInput.css("border", "2px solid red");
                isValid = false;
            } else if (selectedPosition === "SF" && height < 193) {
                alert("Un jucător pe poziția de Small Forward (SF) trebuie să aibă minim 193cm!");
                $heightInput.css("border", "2px solid red");
                isValid = false;
            } else if (selectedPosition === "PF" && height < 195) {
                alert("Un jucător pe poziția de Power Forward (PF) trebuie să aibă minim 195cm!");
                $heightInput.css("border", "2px solid red");
                isValid = false;
            } else {
                $heightInput.css("border", "1px solid #ff5c00");
            }

            if (isValid) {
                alert("Jucător adăugat cu succes!");
            }
            else
            {
                alert("Verifică câmpurile roșii!");
            }
        });
    }

    const $ticketsForm = $("#ticketsForm");
    if ($ticketsForm.length) {
        $ticketsForm.on("submit", function(event) {
            event.preventDefault();
            let isValid = true;
            
            const $ticketCount = $("#ticketCount");
            if ($ticketCount.val() === "" || $ticketCount.val() < 1 || $ticketCount.val() > 5) {
                $ticketCount.css("border", "2px solid red");
                isValid = false;
            } else {
                $ticketCount.css("border", "1px solid #ccc");
            }

            const $ticketEmail = $("#ticketEmail");
            if (!$ticketEmail.val().includes("@")) {
                $ticketEmail.css("border", "2px solid red");
                isValid = false;
            } else {
                $ticketEmail.css("border", "1px solid #ccc");
            }

            if (isValid) alert("Biletele au fost rezervate cu succes!");
            else alert("Verifică câmpurile roșii!");
        });
    }

    const $contactForm = $("#contactForm");
    if ($contactForm.length) {
        $contactForm.on("submit", function(event) {
            event.preventDefault();
            let isValid = true;
            
            const $password = $("#scoutPassword");
            if ($password.val().length < 6) {
                $password.css("border", "2px solid red");
                isValid = false;
            } else {
                $password.css("border", "1px solid #ccc");
            }

            const $terms = $("#scoutTerms");
            if (!$terms.is(":checked")) {
                $terms.css("outline", "2px solid red"); 
                isValid = false;
            } else {
                $terms.css("outline", "none");
            }

            if (isValid) alert("Aplicația a fost trimisă!");
            else alert("Atenție: Parola prea scurtă sau termenii ne-bifați (marcaj roșu)!");
        });
    }

    const $positionRadios = $('input[name="Player Position"]');
    const $heightInput = $('input[name="Player Height"]');

    if ($positionRadios.length && $heightInput.length) {
        $positionRadios.on("change", function() {
            const selectedPosition = $(this).val();
            const constraints = positionConstraints[selectedPosition];

            if (constraints) {
                $heightInput.attr("placeholder", `Min: ${constraints.minHeight} - Max: ${constraints.maxHeight}`);
                $heightInput.css("border", "1px solid #ccc");
            }
        });

        $positionRadios.filter(':checked').trigger("change");
    }

    const $countrySelect = $("#countrySelect");
    const $teamSelect = $("#teamSelect");

    if ($countrySelect.length && $teamSelect.length) {
        $countrySelect.on("change", function() {
            const selectedCountry = $(this).val();
            
            const teams = teamsByCountry[selectedCountry] || [];
            
            $teamSelect.empty().append('<option value="">-- Alege o echipă --</option>');
            
            $.each(teams, function(index, team) {
                $teamSelect.append($('<option></option>').val(team).text(team));
            });
        });
    }

    if ($('.carousel-container').length) {
        let currentSlide = 0;
        let autoSlide;

        function updateCarousel() {
            const slide = carouselData[currentSlide]
            $("#carousel-image").fadeOut(200, function() {
                $("#carousel-link").attr("href", slide.link);
                $("#carousel-text").text(slide.text);
                $(this).css("background-image", `url('${slide.img}')`).fadeIn(200);
            });
        }

        function changeSlide(direction) {
            currentSlide += direction;
            if (currentSlide >= carouselData.length) {
                currentSlide = 0;
            }
            else if (currentSlide < 0) {
                currentSlide = carouselData.length - 1;
            }
            updateCarousel();
        }

        function startCarousel() {
            autoSlide = setInterval(() => {
                changeSlide(1);
            }, 5000);
        }

        updateCarousel();
        startCarousel();

        $('.prev').on('click', function(e){
            e.preventDefault();
            clearInterval(autoSlide);
            changeSlide(-1);
            startCarousel();
        });

        $('.next').on('click', function(e){
            e.preventDefault();
            clearInterval(autoSlide);
            changeSlide(1);
            startCarousel();
        });
    }

    if ($("#standingsTable").length) {
        let sortDirection = true;

        function renderTable(data) {
            const $body = $("#standingsBody");
            $body.empty();
            
            $.each(data, function(index, item) {
                const row = `<tr>
                    <td>${item.pozitie}</td>
                    <td>${item.nume}</td>
                    <td><img src="../${item.logo.replace('/images/', 'images/')}" width="70" height="70" alt="${item.nume} logo" style="object-fit: contain;"/></td>
                    <td>${item.victorii}</td>
                    <td>${item.infrangeri}</td>
                </tr>`;
                $body.append(row);
            });
        }

        renderTable(euroleagueStandings);

        $("#standingsTable th[data-key]").on("click", function() {
            const key = $(this).data("key");
            
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
            
            $("#standingsTable th").css("background-color", ""); 
            $(this).css("background-color", "#ff5c00"); 
        });
    }

    if ($("#verticalStandingsTable").length) {
        let vertSortDir = true;

        function renderVerticalTable(data) {
            const $rowNume = $("#row-nume");
            const $rowVictorii = $("#row-victorii");

            $rowNume.find("td").remove();
            $rowVictorii.find("td").remove();

            $.each(data, function(index, item) {
                $rowNume.append(`<td>${item.nume}</td>`);
                $rowVictorii.append(`<td>${item.victorii}</td>`);
            });
        }

        renderVerticalTable(euroleagueStandings);

        $("#verticalStandingsTable th[data-key]").on("click", function() {
            const key = $(this).data("key");

            euroleagueStandings.sort((a, b) => {
                let valA = a[key];
                let valB = b[key];

                if (typeof valA === 'string') {
                    return vertSortDir ? valA.localeCompare(valB) : valB.localeCompare(valA);
                } else {
                    return vertSortDir ? valA - valB : valB - valA;
                }
            });

            vertSortDir = !vertSortDir; 
            renderVerticalTable(euroleagueStandings); 

            $("#verticalStandingsTable th").css("background-color", "");
            $(this).css("background-color", "#ff5c00");
        });
    }

    if ($('.echipa-lista').length) {
        $('.echipa-lista strong').on('click', function() {
            $(this).parent('.echipa-lista').toggleClass('expandat');
        });
    }

    let currentMatch = null;

    if ($("#quiz-content-wrapper").length) {
        
        function loadNewQuiz() {
            const $quizOptions = $("#quiz-options");
            if (!$quizOptions.length) return;

            $("#quiz-feedback").empty();

            const randomIndex = Math.floor(Math.random() * quizMatches.length);
            currentMatch = quizMatches[randomIndex];

            $("#quiz-date").text(currentMatch.data);
            $("#name1").text(currentMatch.echipa1);
            $("#name2").text(currentMatch.echipa2);
            
            $("#logo1").attr("src", currentMatch.logo1);
            $("#logo2").attr("src", currentMatch.logo2);

            let shuffledOptions = [...currentMatch.variante];
            shuffledOptions.sort(function() {
                return 0.5 - Math.random();
            });

            $quizOptions.empty();

            $.each(shuffledOptions, function(index, option) {
                const $button = $('<button></button>')
                    .text(option)
                    .addClass("quiz-btn")
                    .on("click", function() {
                        checkAnswer($(this), option); 
                    });
                $quizOptions.append($button);
            });
        }

        function checkAnswer($selectedBtn, chosenOption) {
            const $allButtons = $(".quiz-btn");
            
            $allButtons.prop("disabled", true);

            if (chosenOption === currentMatch.scorCorect) {
                $selectedBtn.addClass("correct");
                $("#quiz-feedback").html("<p style='color:green; font-weight:bold;'>Corect!</p>");
            } else {
                $selectedBtn.addClass("wrong");
                
                $allButtons.filter(function() {
                    return $(this).text() === currentMatch.scorCorect;
                }).addClass("correct");
                
                $("#quiz-feedback").html("<p style='color:red; font-weight:bold;'>Greșit! Răspunsul corect este: " + currentMatch.scorCorect + "</p>");
            }

            setTimeout(() => {
                loadNewQuiz();
            }, 5000);
        }

        loadNewQuiz();

        const $quizBox = $("#quiz-content-wrapper");
        const $showBtn = $("#quiz-show-btn");
        const $hideBtn = $("#quiz-hide-btn");

        $hideBtn.on("click", function() {
            $quizBox.fadeOut(300, function() {
                $showBtn.fadeIn(300);
            });
        });

        $showBtn.on("click", function() {
            $showBtn.fadeOut(300, function() {
                $quizBox.fadeIn(300);
            });
        });
    }

});