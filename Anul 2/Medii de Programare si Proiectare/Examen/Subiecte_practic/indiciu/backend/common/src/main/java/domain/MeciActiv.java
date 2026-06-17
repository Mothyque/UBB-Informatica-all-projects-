package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MeciActiv {
    private Long idJoc;
    private String alias;
    private Integer nrIncercari;
    private String pozitiiPropuse;
    private Configuratie configuratie;
    private String[] tabla;
    private String dataOraInceput;

    public MeciActiv(Long idJoc, String alias, Configuratie configuratie) {
        this.idJoc = idJoc;
        this.alias = alias;
        this.nrIncercari = 0;
        this.pozitiiPropuse = "";
        this.configuratie = configuratie;
        this.tabla = new String[16];
        for (int i = 0; i < 16; i++) {
            this.tabla[i] = " ";
        }
        this.dataOraInceput = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Long getIdJoc() {
        return idJoc;
    }

    public String getAlias() {
        return alias;
    }

    public Integer getNrIncercari() {
        return nrIncercari;
    }

    public String getPozitiiPropuse() {
        return pozitiiPropuse;
    }

    public Configuratie getConfiguratie() {
        return configuratie;
    }

    public String[] getTabla() {
        return tabla;
    }

    public String getDataOraInceput() {
        return dataOraInceput;
    }

    public void incearca(Integer linie, Integer coloana) {
        nrIncercari++;
        pozitiiPropuse += linie + "," + coloana + ";";
    }

    public void actualizeazaTabla(Integer linie, Integer coloana, Double distanta)
    {
        int index = linie * 4 + coloana;
        tabla[index] = "" + distanta;
    }
}
