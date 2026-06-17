package service;

import domain.Joc;
import domain.Jucator;
import domain.MeciActiv;
import domain.Status;
import repository.JocRepository;
import repository.JucatorRepository;
import utils.observer.Observable;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class JocServiceImpl extends Observable implements JocService
{
    private static final Logger logger = Logger.getLogger(JocServiceImpl.class.getName());

    private final JocRepository jocRepository;
    private final JucatorRepository jucatorRepository;

    private final Map<Long, MeciActiv> meciuriActive = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public JocServiceImpl(JocRepository jocRepository, JucatorRepository jucatorRepository)
    {
        this.jocRepository = jocRepository;
        this.jucatorRepository = jucatorRepository;
    }

    @Override
    public MeciActiv pornesteJoc(String alias)
    {
        logger.info("Service: Starting game for player with alias: " + alias);
        Jucator jucator = jucatorRepository.findByAlias(alias).orElseGet(() -> {
            Jucator nou = new Jucator(alias);
            jucatorRepository.save(nou);
            logger.info("New player created with alias: " + alias);
            return nou;
        });
        Long idJoc = idGenerator.getAndIncrement();
        MeciActiv meciActiv = new MeciActiv(idJoc, jucator);
        meciuriActive.put(idJoc, meciActiv);
        logger.info("Game started with id: " + idJoc + " for player: " + alias);
        return meciActiv;
    }

    @Override
    public MeciActiv efectueazaMutare(Long idJoc, int linie, int coloana)
    {
        MeciActiv meciActiv = meciuriActive.get(idJoc);
        if (meciActiv == null || !meciActiv.getStatus().equals(Status.IN_PROGRESS))
        {
            throw new RuntimeException("Game not found or not in progress with id: " + idJoc);
        }
        String[] tabla = meciActiv.getTabla();
        int pozitie = linie * 3 + coloana;
        if (!tabla[pozitie].equals(" "))
        {
            throw new RuntimeException("Cell is already occupied at position: (" + linie + ", " + coloana + ")");
        }
        tabla[pozitie] = "X";
        logger.info("Move made at position: (" + linie + ", " + coloana + ") for game id: " + idJoc);

        if (verificaCastigator(tabla, "X"))
        {
            finalizeazaMeci(meciActiv, Status.WON_PLAYER, 10);
            return meciActiv;
        }
        if (esteTablaPlina(tabla))
        {
            finalizeazaMeci(meciActiv, Status.DRAW, 5);
            return meciActiv;
        }

        faciMutareCalculator(tabla);
        logger.info("Move made by computer for game id: " + idJoc);

        if (verificaCastigator(tabla, "O"))
        {
            finalizeazaMeci(meciActiv, Status.WON_COMPUTER, 0);
            return meciActiv;
        }
        if (esteTablaPlina(tabla))
        {
            finalizeazaMeci(meciActiv, Status.DRAW, 5);
            return meciActiv;
        }
        return meciActiv;
    }

    private void faciMutareCalculator(String[] tabla)
    {
        List<Integer> mutariLibere = new ArrayList<>();
        for (int i = 0; i < 9; i++)
        {
            if (" ".equals(tabla[i]))
            {
                mutariLibere.add(i);
            }
        }
        if (!mutariLibere.isEmpty())
        {
            int alegere = mutariLibere.get(new Random().nextInt(mutariLibere.size()));
            tabla[alegere] = "O";
        }
    }

    private boolean verificaCastigator(String[] t, String c)
    {
        int[][] combinatiiCastigatoare = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] comb : combinatiiCastigatoare)
        {
            if (c.equals(t[comb[0]]) && c.equals(t[comb[1]]) && c.equals(t[comb[2]]))
            {
                return true;
            }
        }
        return false;
    }

    private boolean esteTablaPlina(String[] tabla)
    {
        for (int i = 0; i < 9; i++)
        {
            if(" ".equals(tabla[i]))
            {
                return false;
            }
        }
        return true;
    }

    private void finalizeazaMeci(MeciActiv meci, Status status, int puncte)
    {
        meci.setStatus(status);
        int durata = (int) ((System.currentTimeMillis() - meci.getStartTime()) / 1000);
        if (durata == 0)
        {
            durata = 1;
        }
        Joc joc = new Joc(meci.getJucator(), puncte, durata);
        jocRepository.save(joc);
        meciuriActive.remove(meci.getId());
        logger.info("Game with id: " + meci.getId() + " finalized with status: " + status + " and points: " + puncte);
        this.notifyObservers();
    }

    @Override
    public List<Joc> getClasament()
    {
        return jocRepository.getAllGamesSorted();
    }

    @Override
    public List<Joc> getJocuriCastigate(String alias)
    {
        return jocRepository.getWonGamesByPlayerAlias(alias);
    }
}
