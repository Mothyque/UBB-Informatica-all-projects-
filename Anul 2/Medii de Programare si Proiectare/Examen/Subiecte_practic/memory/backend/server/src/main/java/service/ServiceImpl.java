package service;


import domain.*;
import observer.Observable;
import repository.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class ServiceImpl extends Observable implements Service
{
    private static final Logger logger = Logger.getLogger(ServiceImpl.class.getName());

    private final ConfiguratieRepository configuratieRepository;
    private final CuvantRepository cuvantRepository;
    private final JocRepository jocRepository;
    private final JucatorRepository jucatorRepository;

    private final Map<Long, MeciActiv> meciuriActive = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ServiceImpl(ConfiguratieRepository configuratieRepository, CuvantRepository cuvantRepository, JocRepository jocRepository, JucatorRepository jucatorRepository)
    {
        this.configuratieRepository = configuratieRepository;
        this.cuvantRepository = cuvantRepository;
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
        List<Cuvant> cuvinte = cuvantRepository.findAll();
        String configuratie = generateRandomConfiguration(cuvinte);
        if (!configuratieRepository.exists(configuratie))
        {
            configuratieRepository.save(new Configuratie(configuratie));
        }
        String[] configuratieArray = configuratie.split(",");
        MeciActiv meciActiv = new MeciActiv(idJoc, jucator.getAlias(), configuratieArray);
        meciuriActive.put(idJoc, meciActiv);
        logger.info("Game started with id: " + idJoc + " for player: " + alias);
        return meciActiv;
    }

    private String generateRandomConfiguration(List<Cuvant> cuvinte)
    {
        if (cuvinte.size() < 5)
        {
            throw new RuntimeException("Not enough words in the repository to generate a configuration.");
        }
        List<Cuvant> copie = new ArrayList<>(cuvinte);
        Collections.shuffle(copie);
        List<String> listaDeZece = new ArrayList<>();
        for (int i = 0; i < 5; i++)
        {
            String cuvant = copie.get(i).getText();
            listaDeZece.add(cuvant);
            listaDeZece.add(cuvant);
        }
        Collections.shuffle(listaDeZece);
        return String.join(",", listaDeZece);
    }

    @Override
    public MeciActiv efectueazaMutare(Long idJoc, int pozitie1, int pozitie2)
    {
        MeciActiv meciActiv = meciuriActive.get(idJoc);
        if (meciActiv == null)
        {
            throw new RuntimeException("Game not found or not in progress with id: " + idJoc);
        }
        if (pozitie1 < 0 || pozitie1 > 9 || pozitie2 < 0 || pozitie2 > 9 || pozitie1 == pozitie2)
        {
            throw new RuntimeException("Invalid positions: " + pozitie1 + ", " + pozitie2);
        }
        String[] configuratie = meciActiv.getConfiguratie();
        String[] descoperite = meciActiv.getDescoperite();
        if (descoperite[pozitie1].equals("yes") || descoperite[pozitie2].equals("yes"))
        {
            throw new RuntimeException("One or both positions are already discovered: " + pozitie1 + ", " + pozitie2);
        }
        meciActiv.adaugaIncercare(String.valueOf(pozitie1), String.valueOf(pozitie2));
        if (configuratie[pozitie1].equals(configuratie[pozitie2]))
        {
            descoperite[pozitie1] = "yes";
            descoperite[pozitie2] = "yes";
            meciActiv.adaugaPuncte(-2);
            logger.info("Successful move at positions: " + pozitie1 + ", " + pozitie2 + " for game id: " + idJoc);
        }
        else
        {
            meciActiv.adaugaPuncte(3);
            logger.info("Unsuccessful move at positions: " + pozitie1 + ", " + pozitie2 + " for game id: " + idJoc);
        }
        if (meciActiv.getIncercari() == 10 || allDiscovered(descoperite))
        {
            String configuratieStr = String.join(",", configuratie);
            Joc joc = new Joc(meciActiv.getAlias(), meciActiv.getPuncte(), (System.currentTimeMillis() - meciActiv.getStartTime()) / 1000, configuratieStr , meciActiv.getPozitii());
            jocRepository.save(joc);
            this.notifyObservers();
            meciuriActive.remove(idJoc);
            logger.info("Game with id: " + idJoc + " ended. Final score: " + meciActiv.getPuncte() + " points, " + meciActiv.getIncercari() + " attempts.");
            return meciActiv;
        }
        return meciActiv;
    }

    private boolean allDiscovered(String[] descoperite)
    {
        for (String s : descoperite)
        {
            if (s.equals("no"))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Joc> getClasament()
    {
        return jocRepository.getClasament();
    }

    @Override
    public Joc getJoc(Long idJoc)
    {
        Joc joc = jocRepository.getJoc(idJoc);
        if (joc == null)
        {
            throw new RuntimeException("Game not found with id: " + idJoc);
        }
        return joc;
    }

    @Override
    public void modificaConfiguratie(Long idConfiguratie, List<String> elementeNoi)
    {
        if (elementeNoi.size() != 10)
        {
            throw new RuntimeException("Configuration must have exactly 10 elements.");
        }
        Map<String, Integer> frecventa = new HashMap<>();
        for (String element : elementeNoi)
        {
            frecventa.put(element, frecventa.getOrDefault(element, 0) + 1);
        }

        if (frecventa.size() != 5 || frecventa.values().stream().anyMatch(count -> count != 2))
        {
            throw new RuntimeException("Configuration must contain exactly 5 unique elements, each appearing exactly twice.");
        }
        String configuratieNoua = String.join(",", elementeNoi);
        Configuratie configuratie = configuratieRepository.find(idConfiguratie).orElseThrow(() -> new RuntimeException("Configuration not found with id: " + idConfiguratie));
        configuratie.setText(configuratieNoua);
        configuratieRepository.update(configuratie);
        logger.info("Configuration with id: " + idConfiguratie + " updated successfully.");
    }
}
