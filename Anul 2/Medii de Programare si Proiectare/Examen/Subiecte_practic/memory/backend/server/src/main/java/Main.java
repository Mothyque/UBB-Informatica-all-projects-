import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import service.ServiceImpl;
import utils.HibernateUtils;
import repository.*;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

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
            ConfiguratieRepository configuratieRepository = new ConfiguratieRepositoryImpl();
            CuvantRepository cuvantRepository = new CuvantRepositoryImpl();
            ServiceImpl service = new ServiceImpl(configuratieRepository, cuvantRepository, jocRepository, jucatorRepository);

            RestHandler restHandler = new RestHandler(service);
            service.addObserver(restHandler);

            RestServer server = new RestServer(getPort(), restHandler);
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

    private static int getPort()
    {
        try
        {
            InputStream is = Main.class.getClassLoader().getResourceAsStream("hibernate.cfg.xml");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);

            NodeList list = doc.getElementsByTagName("property");
            for (int i = 0; i < list.getLength(); i++)
            {
                Element element = (Element) list.item(i);
                if ("server.port".equals(element.getAttribute("name")))
                {
                    return Integer.parseInt(element.getTextContent().trim());
                }
            }
        }
        catch (Exception e)
        {
            logger.error("Error reading port from configuration: {}", e.getMessage(), e);
        }
        return 8080;

    }
}
