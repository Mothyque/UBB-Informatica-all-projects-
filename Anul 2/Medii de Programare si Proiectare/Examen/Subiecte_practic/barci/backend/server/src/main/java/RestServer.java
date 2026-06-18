import com.sun.net.httpserver.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RestServer
{
    private final Logger logger = LogManager.getLogger(RestServer.class);
    private final HttpServer server;

    public RestServer(int port, RestHandler restHandler) throws Exception
    {
        this.server = HttpServer.create(new java.net.InetSocketAddress(port), 0);
        this.server.createContext("/api", restHandler);
        this.server.setExecutor(java.util.concurrent.Executors.newFixedThreadPool(10));
    }

    public void start()
    {
        server.start();
        logger.info("The REST Server has started on port {}", server.getAddress().getPort());
    }

    public void stop()
    {
        server.stop(0);
        logger.info("The Server has been stopped");
    }

}
