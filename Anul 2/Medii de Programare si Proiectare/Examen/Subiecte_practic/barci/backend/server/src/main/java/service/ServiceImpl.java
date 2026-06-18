package service;

import domain.Barca;
import domain.Joc;
import domain.Jucator;
import domain.MeciActiv;
import jakarta.persistence.criteria.CriteriaBuilder;
import observer.Observable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.BarcaRepository;
import repository.JocRepository;
import repository.JucatorRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceImpl extends Observable implements Service
{
    private final Logger logger = LogManager.getLogger(ServiceImpl.class);

    private final BarcaRepository barcaRepository;
    private final JocRepository jocRepository;
    private final JucatorRepository jucatorRepository;

    private final Map<Long, MeciActiv> meciuriActive = new HashMap<>();
    private AtomicLong idGenerator = new AtomicLong(1);


    public ServiceImpl(BarcaRepository barcaRepository, JocRepository jocRepository, JucatorRepository jucatorRepository)
    {
        this.barcaRepository = barcaRepository;
        this.jocRepository = jocRepository;
        this.jucatorRepository = jucatorRepository;
    }

    @Override
    public MeciActiv pornesteJoc(String alias)
    {
        logger.info("Service: Starting game for player with alias: " + alias);
        Jucator jucator = jucatorRepository.find(alias).orElseGet(() -> {
            Jucator nou = new Jucator(alias);
            jucatorRepository.save(nou);
            logger.info("New player created with alias: " + alias);
            return nou;
        });
        Long idJoc = idGenerator.getAndIncrement();
        Barca barca = genereazaBarca();
        MeciActiv meciActiv = new MeciActiv(idJoc, jucator.getAlias(), barca.getPozitie());
        meciuriActive.put(idJoc, meciActiv);
        return meciActiv;
    }

    private Barca genereazaBarca()
    {
        List<Barca> barci = barcaRepository.findAll();
        if (!barci.isEmpty()) {
            Collections.shuffle(barci);
        }
        return barci.getFirst();
    }

    @Override
    public MeciActiv efectueazaAlegere(Long idJoc, Integer linie, Integer coloana)
    {
        if (linie == null || coloana == null || linie < 0 || coloana < 0 || linie > 4 || coloana > 4) {
            throw new IllegalArgumentException("Invalid line or column values");
        }

        MeciActiv meciActiv = meciuriActive.get(idJoc);
        meciActiv.incearca(linie, coloana);
        Integer[] sirPozitii = getSirPozitii(meciActiv.getPozitiiBarca());
        boolean ghicit = false;
        if (meciActiv.getTabla()[linie * 5 + coloana] == "B") {
            throw new RuntimeException("Already hit this position");
        }
        for (int i = 0; i < sirPozitii.length; i += 2) {
            if (sirPozitii[i] == linie && sirPozitii[i + 1] == coloana) {
                meciActiv.actualizeazaTabla(linie, coloana, "B");
                meciActiv.adaugaGhicite();
                meciActiv.adaugaPuncte(5);
                ghicit = true;
                break;
            }
        }
        if (!ghicit)
        {

            meciActiv.actualizeazaTabla(linie, coloana, getDistanta(linie, coloana, meciActiv.getPozitiiBarca()).toString());
            meciActiv.adaugaPuncte(-3);
        }
        if (meciActiv.getGhicite() == 3 || meciActiv.getNrIncercari() >= 3)
        {
            jocRepository.save(new Joc(meciActiv.getAlias(), meciActiv.getPuncte(), meciActiv.getPozitii(),meciActiv.getGhicite(), meciActiv.getPozitiiBarca(), meciActiv.getDataOra()));
            meciuriActive.remove(idJoc);
            this.notifyObservers();
            return meciActiv;
        }
        return meciActiv;
    }

    private Integer[] getSirPozitii(String pozitii) {
        String[] split = pozitii.split(";");
        Integer[] sirPozitii = new Integer[split.length * 2];
        for (int i = 0; i < split.length; i++) {
            String[] linieColoana = split[i].split(",");
            sirPozitii[i * 2] = Integer.parseInt(linieColoana[0]);
            sirPozitii[i * 2 + 1] = Integer.parseInt(linieColoana[1]);
        }
        return sirPozitii;
    }

    private Double getDistanta(Integer linie, Integer coloana, String pozitii)
    {
        Integer[] sirPozitii = getSirPozitii(pozitii);
        Double[] distante = new Double[sirPozitii.length / 2];
        for (int i = 0; i < sirPozitii.length; i += 2) {
            distante[i / 2] = Math.sqrt(Math.pow(linie - sirPozitii[i], 2) + Math.pow(coloana - sirPozitii[i + 1], 2));
        }
        Double minDistanta = Double.MAX_VALUE;
        for (Double distanta : distante) {
            if (distanta < minDistanta) {
                minDistanta = distanta;
            }
        }
        return minDistanta;
    }

    @Override
    public List<Joc> getClasament()
    {
        return jocRepository.getClasament();
    }

    @Override
    public List<Joc> getJocuriCuHit(String alias)
    {
        return jocRepository.getJocuriCuHit(alias);
    }

    @Override
    public void adaugaBarca(String pozitii)
    {
        if (!valideazaPozitii(pozitii)) {
            throw new IllegalArgumentException("Invalid boat positions");
        }
        Barca barca = new Barca(pozitii);
        barcaRepository.save(barca);
    }

    private boolean valideazaPozitii(String pozitii)
    {
        Integer[] p = getSirPozitii(pozitii);
        if (p.length != 6) return false;

        for (int i = 0; i < 6; i++) {
            if (p[i] < 0 || p[i] > 4) return false;
        }

        if (p[0].equals(p[2]) && p[2].equals(p[4])) {
            java.util.List<Integer> cols = java.util.Arrays.asList(p[1], p[3], p[5]);
            java.util.Collections.sort(cols);
            return (cols.get(1) == cols.get(0) + 1 && cols.get(2) == cols.get(1) + 1);
        }

        if (p[1].equals(p[3]) && p[3].equals(p[5])) {
            java.util.List<Integer> lines = java.util.Arrays.asList(p[0], p[2], p[4]);
            java.util.Collections.sort(lines);
            return (lines.get(1) == lines.get(0) + 1 && lines.get(2) == lines.get(1) + 1);
        }

        return false;
    }
}
