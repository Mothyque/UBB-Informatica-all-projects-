package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import domain.Joc;
import domain.MeciActiv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.JocService;
import utils.observer.Observer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


public class RestHandler implements HttpHandler, Observer
{
    private static final Logger logger = LogManager.getLogger(RestHandler.class);
    private final JocService jocService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<HttpExchange> clientiConectati = new CopyOnWriteArrayList<>();
    public RestHandler(JocService jocService)
    {
        this.jocService = jocService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod()))
        {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        logger.info("REST request received: {} {}", method, path);
        try
        {
            if ("GET".equalsIgnoreCase(method) && "/api/clasament/stream".equals(path))
            {
                exchange.getResponseHeaders().set("Content-Type", "text/event-stream");
                exchange.getResponseHeaders().set("Cache-Control", "no-cache");
                exchange.getResponseHeaders().set("Connection", "keep-alive");
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                exchange.sendResponseHeaders(200, 0);
                clientiConectati.add(exchange);
                logger.info("Client connected for streaming updates");
                return;
            }
            if ("POST".equalsIgnoreCase(method) && "/api/jocuri/start".equals(path))
            {
                Map<String, String> body = objectMapper.readValue(exchange.getRequestBody(), Map.class);
                String alias = body.get("alias");
                MeciActiv meciNou = jocService.pornesteJoc(alias);
                trimiteRaspuns(exchange, 200, meciNou);
                return;
            }

            if("POST".equalsIgnoreCase(method) && path.matches("/api/jocuri/\\d+/mutare"))
            {
                String[] parts = path.split("/");
                Long idMeci = Long.parseLong(parts[3]);
                Map<String, Object> body = objectMapper.readValue(exchange.getRequestBody(), Map.class);
                int linie = Integer.parseInt(body.get("linie").toString());
                int coloana = Integer.parseInt(body.get("coloana").toString());
                MeciActiv meciActualizat = jocService.efectueazaMutare(idMeci, linie, coloana);
                trimiteRaspuns(exchange, 200, meciActualizat);
                return;
            }

            if ("GET".equalsIgnoreCase(method) && path.matches("/api/jocuri/[a-zA-Z0-9]+/castigate"))
            {
                String[] parts = path.split("/");
                String alias = parts[3];
                List<Joc> jocuriCastigate = jocService.getJocuriCastigate(alias);
                trimiteRaspuns(exchange, 200, jocuriCastigate);
                return;
            }

            if ("GET".equalsIgnoreCase(method) && "/api/clasament".equals(path))
            {
                trimiteRaspuns(exchange, 200, jocService.getClasament());
                return;
            }

            trimiteRaspuns(exchange, 404, Map.of("error", "Endpoint not found"));
        }
        catch (IllegalArgumentException e)
        {
            logger.warn("Invalid request: {}", e.getMessage());
            trimiteRaspuns(exchange, 400, Map.of("error", e.getMessage()));
        }
        catch (Exception e)
        {
            logger.error("Error processing request", e);
            trimiteRaspuns(exchange, 500, Map.of("error", "Internal server error"));
        }
    }

    private void trimiteRaspuns(HttpExchange exchange, int statusCode, Object responseObj) throws IOException
    {
        byte[] responseBytes = objectMapper.writeValueAsBytes(responseObj);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody())
        {
            os.write(responseBytes);
        }
    }

    @Override
    public void update()
    {
        logger.info("Received update notification from observable");
        var clasament = jocService.getClasament();
        List<HttpExchange> conexiuniMoarte = new CopyOnWriteArrayList<>();
        for (HttpExchange exchange : clientiConectati)
        {
            try
            {
                byte[] jsonBytes = objectMapper.writeValueAsBytes(clasament);
                var os = exchange.getResponseBody();
                os.write("data: ".getBytes());
                os.write(jsonBytes);
                os.write("\n\n".getBytes());
                os.flush();
            }
            catch (IOException e)
            {
                logger.warn("Failed to send update to a client, removing from list", e);
                conexiuniMoarte.add(exchange);
            }
            clientiConectati.removeAll(conexiuniMoarte);
        }
    }
}
