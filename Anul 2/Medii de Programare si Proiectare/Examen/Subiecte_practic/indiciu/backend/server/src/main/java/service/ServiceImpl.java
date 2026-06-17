package service;

import domain.Joc;
import domain.Jucator;
import domain.MeciActiv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.ConfiguratieRepository;
import repository.JocRepository;
import repository.JucatorRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceImpl implements Service
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
        String configuratie = genereazaConfiguratieRandom();
        
    }

    @Override
    public MeciActiv efectueazaAlegere(Long idJoc, Integer linie, Integer coloana) {
        return null;
    }

    @Override
    public List<Joc> getClasament() {
        return List.of();
    }

    @Override
    public Joc getJoc(String alias) {
        return null;
    }

    @Override
    public void modificaConfiguratie(Long idConfiguratie, Integer linie, Integer coloana, String text) {

    }
}
