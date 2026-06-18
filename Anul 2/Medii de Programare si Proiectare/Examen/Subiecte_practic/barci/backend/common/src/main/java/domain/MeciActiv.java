package domain;

public class MeciActiv
{
    private Long id;
    private String alias;
    private Integer puncte;
    private String pozitii;
    private Integer ghicite;
    private String pozitiiBarca;
    private String dataOra;
    private Integer nrIncercari;
    private String[] tabla;

    public MeciActiv(Long id, String alias, String pozitiiBarca)
    {
        this.id = id;
        this.alias = alias;
        this.puncte = 0;
        this.pozitii = "";
        this.ghicite = 0;
        this.pozitiiBarca = pozitiiBarca;
        this.dataOra = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.nrIncercari = 0;
        this.tabla = new String[25];
        for (int i = 0; i < 25; i++) {
            this.tabla[i] = " ";
        }
    }

    public Long getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }

    public Integer getPuncte() {
        return puncte;
    }

    public String getPozitii() {
        return pozitii;
    }

    public Integer getGhicite() {
        return ghicite;
    }

    public String getPozitiiBarca() {
        return pozitiiBarca;
    }

    public String getDataOra() {
        return dataOra;
    }

    public Integer getNrIncercari() {
        return nrIncercari;
    }

    public String[] getTabla() {
        return tabla;
    }

    public void incearca(Integer linie, Integer coloana) {
        nrIncercari++;
        pozitii += linie + "," + coloana + ";";
    }

    public void adaugaPuncte(Integer puncte) {
        this.puncte += puncte;
    }

    public void adaugaGhicite() {
        this.ghicite++;
    }

    public void actualizeazaTabla(Integer linie, Integer coloana, String simbol) {
        int index = linie * 5 + coloana;
        tabla[index] = simbol;
    }
}
