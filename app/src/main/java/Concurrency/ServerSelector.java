package Concurrency;

import java.net.*;
import java.net.http.*;
import java.util.*;
import java.util.concurrent.*;

public class ServerSelector {

    public static void main(String[] args) {
        findFastestServer("www.example.com", "www.example.org", "www.example.net");
    }

    /**
     * Select fastest Server.
     * 
     * @param servers Vararg/array of servers to select from.
     * @return server who responded first
     */
    public static String findFastestServer(String... servers) {
        // Name des Servers, der am schnellsten antwortet.
        String fastestServer = null;

        // todo: Erstellen Sie einen passenden ExecutorService [1P]
        ExecutorService executorService = Executors.newFixedThreadPool(servers.length);

        /*
         * todo: Liste mit allen Tasks. [2P] Für jeden Server aus der Liste "servers"
         * soll ein Task erstellt und der Liste taskList hinzugefügt werden. Der Task
         * soll mit Hilfe der statischen Methode checkServer (siehe unten) die
         * Verfügbarkeit des Servers überprüfen.
         */
        List<Callable<String>> taskList = new ArrayList<>();
        for (String server : servers) {
            try {
                checkServer(server);
            } catch (Exception e) {
                continue;
            }
            taskList.add(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    // compute stuff
                    return null;
                }
            });
        }

        long startTime = System.currentTimeMillis();
        try {
            /*
             * todo: Ausführen der Tasks und Entgegennahme des Resultats. [1.5P] Der Name
             * des Servers mit der schnellsten Rückmeldung soll der Variable fastestServer
             * zugewiesen werden.
             */
            fastestServer = executorService.invokeAny(taskList);
            System.out.println(fastestServer + " answered after " + (System.currentTimeMillis() - startTime) + "ms");
        } catch (Exception e) {
            System.out.println("Error selecting server: " + e.getMessage());
        }

        // todo: Beenden Sie den Executor-Service. [0.5P]
            executorService.shutdown();
        return fastestServer;
    }

    /**
     * Checks if server is responding
     * 
     * @param serverName to test if connection is working
     * @return serverName successfully connected to.
     * @throws ServerNotAvailableException if connection is failing or server is not
     *                                     ready.
     */
    private static String checkServer(String serverName) throws ServerNotAvailableException {
        try {
            URI serverUri = new URI("https", serverName, "/", null);
            HttpRequest request = HttpRequest.newBuilder(serverUri).GET().build();
            HttpResponse response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() != 200)
                throw new ServerNotAvailableException("Server not ready: " + serverName);
            return serverName;
        } catch (Exception e) {
            throw new ServerNotAvailableException("Failed to connect to server " + serverName);
        }
    }

    static class ServerNotAvailableException extends Exception {
        public ServerNotAvailableException(String message) {
            super(message);
        }
    }
}
