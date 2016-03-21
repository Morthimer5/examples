package server;



import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Created by Morthimer on 3/11/2016.
 */
public class MyServer  {

    private String contentDir = "D:\\content\\";

    public MyServer() { }

    public static void main(String[] args) {
        MyServer server = new MyServer();
        server.run();
    }

    public void run(){
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket socket;
            while(true) {
                socket = serverSocket.accept();
                String request = getRequestHeader(socket);
                System.out.println("Request: " + request);

                if(request != null) {
                    String arguments = request.split(" ")[1];
                    if (arguments.contains("file")) {
                        System.out.println("search for file ");
                        for (File f : new File(contentDir).listFiles()) {
                            if (arguments.equals("/file/" + f.getName())) {
                                sendResponse(f, socket);
                            }
                        }
                    } else if (arguments.contains("calculate?")) {
                        Calculator calc = new Calculator();
                        sendResponse(calc.parseRequestt(arguments), socket);
                    } else {
                        createMainPage(socket);
                    }
                }
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createMainPage(Socket socket) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Server</title>\n" +
                "    <link rel=stylesheet type=\"text/css\" href=http://localhost:8080/style.css> \r\n" +
                "</head>\n" +
                "<body>\n" +
                "<br>\n" +
                "<h1> Files are able to read: </h1>\r\n" +
                "<ul>\r\n";
        for (File f : new File(contentDir).listFiles()) {
            response += "<li><a href=\"http://localhost:8080/file/" + f.getName() +"\">" + f.getName() + "</a></li>\r\n";
        }
        socket.getOutputStream().write(response.getBytes());
        socket.getOutputStream().flush();
    }

    private String getRequestHeader(Socket socket) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String header = br.readLine();
        return header;
    }

    private void sendResponse(String s, Socket socket) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Server</title>\n" +
                "    <link rel=stylesheet type=\"text/css\" href=http://localhost:8080/style.css>" +
                "</head>\n" +
                "<body>\n" +
                "<br>\n";
        response += s;
        response += "\n<br><br><a href=\"http://localhost:8080/\"</a>Back to main page</body></html>";
        socket.getOutputStream().write(response.getBytes());
        socket.getOutputStream().flush();

    }

    private void sendResponse(File file, Socket socket) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder fileContent = new StringBuilder();
        while(br.ready()) {
            fileContent.append(br.readLine());
        }
        sendResponse(fileContent.toString(), socket);
    }


}
