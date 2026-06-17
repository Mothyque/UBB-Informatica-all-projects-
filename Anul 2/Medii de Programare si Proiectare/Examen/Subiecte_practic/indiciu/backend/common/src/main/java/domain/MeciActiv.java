package domain;

import java.time.LocalTime;

public class MeciActiv {
    private Long idJoc;
    private String alias;
    private Integer nrIncercari;
    private String pozitiiPropuse;
    private Configuratie configuratie;
    private String[] tabla;
    private String dataOraInceput;

    public MeciActiv(Long idJoc, String alias, Integer nrIncercari, String pozitiiPropuse, Configuratie configuratie) {
        this.idJoc = idJoc;
        this.alias = alias;
        this.nrIncercari = nrIncercari;
        this.pozitiiPropuse = pozitiiPropuse;
        this.configuratie = configuratie;
        this.tabla = new String[15];
        for (int i = 0; i < 15; i++) {
            this.tabla[i] = " ";
        }
        this.dataOraInceput = LocalTime.now().toString();
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
}
