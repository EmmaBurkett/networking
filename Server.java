import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server implements Runnable {
    //initialize class variables
    ServerSocket ss = null;
    Socket s = null;
    String checkClosed = "";

    public static void main(String[] string) {
        Server myObj = new Server();
        try {
            myObj.peerTwo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void peerTwo() throws IOException {
        //open server
        ss = new ServerSocket(9191);
        //accept people to server
        s = ss.accept();
        //runs if not IOException
        System.out.println("client connected");

        //thread runs a while loop to check for incoming messages :)
        Thread thread = new Thread(this); //new thread //'this' is the function!
        thread.start(); 

        //prep to scan for user input
        Scanner scanner = new Scanner(System.in);
        
        //will store input to go to server
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(s.getOutputStream());
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter); 
         
        //while the user hasn't typed BYE continue to check for input
        while(checkClosed.equals("")){ 
            sendMessage(scanner, bufferedWriter);
        }

    }

    public void sendMessage(Scanner scanner, BufferedWriter bufferedWriter) throws IOException {
        String input = scanner.nextLine(); //scan next line for input
        if(input.equalsIgnoreCase("BYE")){ //if 'BYE' then close socket
            checkClosed = "BYE"; //change to BYE
            ss.close(); //close socket
        }else {
            bufferedWriter.write(input); //Send input
            bufferedWriter.newLine(); //adds a newline
            bufferedWriter.flush(); //flushes stream
        }
    }

    @Override
    public void run() {
        try {
            //initialize
            InputStreamReader inputStreamReader;
            String msgFromClient;

            //get's the server messages
            inputStreamReader = new InputStreamReader(s.getInputStream());

            //gets server message
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while (true) {
                msgFromClient = bufferedReader.readLine(); //stores server message
                if (!msgFromClient.equals("")) { //if server message isn't empty
                    System.out.println("Client -> server: " + msgFromClient);//then display to client
                }
            }
        } catch (IOException e) { //catch for connection errors!
            e.printStackTrace();
        }

    }
}








































//An Irritating attempt at threading :(
    //an actually working thingy-madoo! client to server and server to client 


/*public class Server extends Thread {
    public static int amount = 0;
  
    public static void main(String[] args) {
        for(int i = 0; i < 5; i++){
            Server thread = new Server();
            thread.start();
            System.out.println(amount);
            amount++;
            System.out.println(amount);
        }
    }
  
    public void run() {
      amount++;
    }
  }
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server implements Runnable {
    ServerSocket ss = null;
    Socket s = null;
    public static void main(String[] args) throws IOException {
        Server myObj = new Server();
        myObj.ss = new ServerSocket(9191);
        myObj.s = myObj.ss.accept();

        System.out.println("client connected");

        InputStreamReader in = new InputStreamReader(myObj.s.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        String str = bf.readLine();
        System.out.println("client : " + str);

        PrintWriter pr = new PrintWriter(myObj.s.getOutputStream());
        pr.println("hello");
        pr.flush();

        InputStreamReader inputStreamReader = new InputStreamReader(myObj.s.getInputStream());
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(myObj.s.getOutputStream());

        //BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        Scanner scanner = new Scanner(System.in);

        Thread thread = new Thread(myObj);
        thread.start();

        while (true) {
            String input = scanner.nextLine();
            bufferedWriter.write(input);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            //System.out.println(bufferedReader.readLine());
            if(input.equalsIgnoreCase("BYE")){
                break;
            }
        }

        

        myObj.ss.close();
        inputStreamReader.close();
        outputStreamWriter.close();
        scanner.close();
    }
    
    @Override
    public void run() {
        try {
            OutputStreamWriter outputStreamWriter;
            InputStreamReader inputStreamReader;
            String msgFromClient;
            //Server myObj = new Server();
            inputStreamReader = new InputStreamReader(s.getInputStream());
            outputStreamWriter = new OutputStreamWriter(s.getOutputStream());

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            Scanner scanner = new Scanner(System.in);
            while(true) {
                msgFromClient = bufferedReader.readLine();
                if (!msgFromClient.equals("")){
                    System.out.println("Client -> server: " + msgFromClient);
                }
                bufferedWriter.newLine();   
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}*/







//Receives messages from the client

/*import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(9191);
        Socket s = ss.accept();

        System.out.println("client connected");

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        String str = bf.readLine();
        System.out.println("client : " + str);

        PrintWriter pr = new PrintWriter(s.getOutputStream());
        pr.println("hello");
        pr.flush();

        InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(s.getOutputStream());

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String msgFromClient = bufferedReader.readLine();

            System.out.println("Client: " + msgFromClient);
            bufferedWriter.write("MSG Received.");
            bufferedWriter.newLine();
            bufferedWriter.flush();

            if (msgFromClient.equalsIgnoreCase("BYE")) {
                break;
            }
        }

        

        ss.close();
        inputStreamReader.close();
        outputStreamWriter.close();
        scanner.close();
    }
}



//Does not work

/*import java.io.*;
import java.net.*; //get connection stuffs

public class Server {
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    
    //constructor with port
    public Server(int port){
        //starts server and waits for a connnection
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client...");
            
            socket = server.accept();
            System.out.println("Client accepted");

            //takes input from the client socket
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            /*
             * InputStreffam java.net.Socket.getInputStream() throws IOException
             * Returns an input stream for this socket.
             */
            /*String line = "";

            //reads message from client until "Over" is received
            while (!line.equals("Over")){
                try{
                    line = in.readUTF();
                    System.out.println(line);
                }
                catch(IOException i){
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            //close connection 
            socket.close();
            in.close();
        }
        catch (IOException i){
            System.out.println(i);
        }
    }

    public static void main(String args[]){
        Server server = new Server(9191); //phone number
    }
}
*/