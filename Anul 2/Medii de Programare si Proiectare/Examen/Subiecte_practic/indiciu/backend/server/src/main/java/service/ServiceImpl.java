package service;

import domain.Configuratie;
import domain.Joc;
import domain.Jucator;
import domain.MeciActiv;
import observer.Observable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.ConfiguratieRepository;
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

    private final ConfiguratieRepository configuratieRepository;
    private final JocRepository jocRepository;
    private final JucatorRepository jucatorRepository;

    private final Map<Long, MeciActiv> meciuriActive = new HashMap<>();
    private AtomicLong idGenerator = new AtomicLong(1);

    public ServiceImpl(ConfiguratieRepository configuratieRepository, JocRepository jocRepository, JucatorRepository jucatorRepository)
    {
        this.configuratieRepository = configuratieRepository;
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
        Configuratie configuratie = genereazaConfiguratieRandom();
        MeciActiv meciActiv = new MeciActiv(idJoc, jucator.getAlias(), configuratie);
        meciuriActive.put(idJoc, meciActiv);
        return meciActiv;
        
    }

    private Configuratie genereazaConfiguratieRandom()
    {
        List<Configuratie> configuraties = configuratieRepository.findAll();
        Collections.shuffle(configuraties);
        return configuraties.get(0);
    }

    @Override
    public MeciActiv efectueazaAlegere(Long idJoc, Integer linie, Integer coloana)
    {
        if (linie == null || coloana == null || linie < 0 || coloana < 0 || linie > 3 || coloana > 3)
        {
            throw new RuntimeException();
        }
        MeciActiv meciActiv = meciuriActive.get(idJoc);
        meciActiv.incearca(linie, coloana);
        int linieIndiciu = meciActiv.getConfiguratie().getLinie();
        int coloanaIndiciu = meciActiv.getConfiguratie().getColoana();
        if (linie == linieIndiciu && coloana == coloanaIndiciu)
        {
            jocRepository.save(new Joc(meciActiv.getAlias(), meciActiv.getNrIncercari(), meciActiv.getPozitiiPropuse(), meciActiv.getConfiguratie().getText(), meciActiv.getDataOraInceput()));
            meciuriActive.remove(idJoc);
            this.notifyObservers();
            return meciActiv;
        }
        double distanta = Math.sqrt(Math.pow(linie - linieIndiciu, 2) + Math.pow(coloana - coloanaIndiciu, 2));
        meciActiv.actualizeazaTabla(linie, coloana, distanta);
        if (meciActiv.getNrIncercari() >= 4)
        {
            jocRepository.save(new Joc(meciActiv.getAlias(), 10, meciActiv.getPozitiiPropuse(), meciActiv.getConfiguratie().getText(), meciActiv.getDataOraInceput()));
            meciuriActive.remove(idJoc);
            this.notifyObservers();
        }
        return meciActiv;
    }

    @Override
    public List<Joc> getClasament()
    {
        return jocRepository.getClasament();
    }

    @Override
    public List<Joc> getJocuri(String alias)
    {
        return jocRepository.getJocuri(alias);
    }

    @Override
    public void adaugaConfiguratie(Integer linie, Integer coloana, String text)
    {
        Configuratie configuratie = new Configuratie(linie, coloana, text);
        configuratieRepository.save(configuratie);
    }
}
