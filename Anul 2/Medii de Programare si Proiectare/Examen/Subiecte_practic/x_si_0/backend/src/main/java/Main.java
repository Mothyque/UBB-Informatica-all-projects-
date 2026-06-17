import com.sun.net.httpserver.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.JocRepository;
import repository.JocRepositoryImpl;
import repository.JucatorRepository;
import repository.JucatorRepositoryImpl;
import server.RestHandler;
import server.RestServer;
import service.JocService;
import service.JocServiceImpl;
import utils.HibernateUtils;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main
{
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args)
    {
        logger.info("Starting the application...");

        try
        {
            JucatorRepository jucatorRepository = new JucatorRepositoryImpl();
            JocRepository jocRepository = new JocRepositoryImpl();
            JocServiceImpl jocService = new JocServiceImpl(jocRepository, jucatorRepository);

            RestHandler restHandler = new RestHandler(jocService);
            jocService.addObserver(restHandler);

            RestServer server = new RestServer(8080, restHandler);
            server.start();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("Shutting down the application...");
                server.stop();
                HibernateUtils.shutdown();
            }));
        }
        catch (Exception e)
        {
            logger.error("An error occurred while starting the application: {}", e.getMessage(), e);
            System.exit(1);
        }
    }
}
