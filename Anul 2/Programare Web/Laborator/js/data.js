const teamsByCountry = {
    "Spania" : ["FC Barcelona", "Real Madrid", "Baskonia"],
    "Turcia": ["Anadolu Efes", "Fenerbahce"],
    "Serbia": ["Crvena Zvezda", "Partizan"],
    "Franta": ["ASVEL", "AS Monaco"],
}

const positionConstraints = {
    "PG" : {minHeight: 175, maxHeight: 205},
    "SG" : {minHeight: 190, maxHeight: 210},
    "SF" : {minHeight: 193, maxHeight: 213},
    "PF" : {minHeight: 195, maxHeight: 216},
    "C"  : {minHeight: 200, maxHeight: 230},
}

const euroleagueStandings = [
    { pozitie: 1, nume: "Anadolu Efes Istanbul", logo: "/images/efes_logo.png", victorii: 20, infrangeri: 6, puncte: 46 },
    { pozitie: 3, nume: "Crvena Zvezda Belgrade", logo: "/images/crvena_zvezda_logo.png", victorii: 18, infrangeri: 8, puncte: 44 },
    { pozitie: 2, nume: "AS Monaco", logo: "/images/monaco_logo.png", victorii: 19, infrangeri: 7, puncte: 45 },
];


const carouselData = [
    {
        link: "../html/tickets.htm",
        text: "Nu rata Final Four! Cumpără bilete acum!",
        img: "../images/final_four.png"
    },

    {
        link: "../html/dashboard.htm",
        text: "Vezi statistici și analize pe Dashboard!",
        img: "../images/mike_james_play.png"
    },

    {
        link: "../html/contact.htm",
        text: "Ești următorul star? Trimite-ne profilul de scout.",
        img: "../images/scouting.png"
    },

    {
        link: "../html/adminPage5.htm",
        text: "Panou administrare: Gestioneaza echipele Euroleague",
        img: "../images/admin.png"
    }
];

const quizMatches = [
    {
        echipa1: "Anadolu Efes", logo1: "../images/efes_logo.png",
        echipa2: "Real Madrid", logo2: "../images/real_madrid.png",
        data: "14 Februarie 2024", scorCorect: "82 - 76",
        variante: ["82 - 76", "75 - 80", "90 - 88", "70 - 72"]
    },
    {
        echipa1: "AS Monaco", logo1: "../images/monaco_logo.png",
        echipa2: "FC Barcelona", logo2: "../images/barcelona.png",
        data: "21 Martie 2024", scorCorect: "91 - 71",
        variante: ["85 - 84", "91 - 71", "77 - 82", "95 - 90"]
    },
    {
        echipa1: "Panathinaikos", logo1: "../images/panathinaikos.png",
        echipa2: "Fenerbahce", logo2: "../images/fenerbahce.png",
        data: "05 Aprilie 2024", scorCorect: "89 - 81",
        variante: ["80 - 83", "89 - 81", "92 - 85", "74 - 78"]
    },
    {
        echipa1: "Crvena Zvezda", logo1: "../images/crvena_zvezda_logo.png",
        echipa2: "Olympiacos", logo2: "../images/olympiacos.png",
        data: "10 Aprilie 2024", scorCorect: "86 - 89",
        variante: ["86 - 89", "90 - 80", "75 - 77", "82 - 84"]
    },
    {
        echipa1: "Maccabi Tel Aviv", logo1: "../images/maccabi.png",
        echipa2: "Virtus Bologna", logo2: "../images/virtus_bologna.png",
        data: "12 Ianuarie 2024", scorCorect: "95 - 78",
        variante: ["95 - 78", "88 - 90", "102 - 95", "70 - 75"]
    },
    {
        echipa1: "Zalgiris Kaunas", logo1: "../images/zalgiris.png",
        echipa2: "Baskonia", logo2: "../images/baskonia.png",
        data: "10 Februarie 2024", scorCorect: "79 - 75",
        variante: ["82 - 80", "70 - 71", "79 - 75", "85 - 90"]
    },
    {
        echipa1: "Partizan Belgrade", logo1: "../images/partizan.png",
        echipa2: "Olympia Milano", logo2: "../images/olympia_milano.png",
        data: "08 Martie 2024", scorCorect: "82 - 69",
        variante: ["82 - 69", "75 - 78", "90 - 95", "88 - 82"]
    },
    {
        echipa1: "Bayern Munich", logo1: "../images/bayern.png",
        echipa2: "ASVEL Villeurbanne", logo2: "../images/asvel.png",
        data: "25 Ianuarie 2024", scorCorect: "64 - 76",
        variante: ["80 - 70", "64 - 76", "72 - 78", "68 - 82"]
    },
    {
        echipa1: "Valencia Basket", logo1: "../images/valencia.png",
        echipa2: "Real Madrid", logo2: "../images/real_madrid.png",
        data: "01 Martie 2024", scorCorect: "73 - 80",
        variante: ["73 - 80", "80 - 89", "85 - 90", "65 - 75"]
    },
    {
        echipa1: "Fenerbahce", logo1: "../images/fenerbahce.png",
        echipa2: "FC Barcelona", logo2: "../images/barcelona.png",
        data: "15 Martie 2024", scorCorect: "88 - 74",
        variante: ["80 - 81", "88 - 74", "92 - 90", "70 - 75"]
    },
    {
        echipa1: "Olympiacos", logo1: "../images/olympiacos.png",
        echipa2: "AS Monaco", logo2: "../images/monaco_logo.png",
        data: "04 Ianuarie 2024", scorCorect: "75 - 73",
        variante: ["75 - 73", "69 - 80", "70 - 75", "85 - 82"]
    },
    {
        echipa1: "Virtus Bologna", logo1: "../images/virtus_bologna.png",
        echipa2: "Panathinaikos", logo2: "../images/panathinaikos.png",
        data: "29 Martie 2024", scorCorect: "79 - 81",
        variante: ["85 - 85", "70 - 72", "79 - 81", "82 - 88"]
    },
    {
        echipa1: "Baskonia", logo1: "../images/baskonia.png",
        echipa2: "Anadolu Efes", logo2: "../images/efes_logo.png",
        data: "28 Martie 2024", scorCorect: "76 - 97",
        variante: ["80 - 85", "76 - 97", "90 - 95", "70 - 80"]
    },
    {
        echipa1: "Olympia Milano", logo1: "../images/olympia_milano.png",
        echipa2: "Zalgiris Kaunas", logo2: "../images/zalgiris.png",
        data: "30 Noiembrie 2023", scorCorect: "70 - 83",
        variante: ["75 - 93", "70 - 83", "80 - 90", "65 - 70"]
    },
    {
        echipa1: "Real Madrid", logo1: "../images/real_madrid.png",
        echipa2: "Partizan Belgrade", logo2: "../images/partizan.png",
        data: "21 Decembrie 2023", scorCorect: "91 - 75",
        variante: ["91 - 75", "85 - 80", "100 - 95", "67 - 79"]
    }
];