
import java.net.*;
import java.io.*;
import java.util.Scanner;



public class Client implements Runnable {
    //initialize class variables
    Socket s = null;
    String checkClosed = "";

    public static void main(String[] string) {
        Client myObj = new Client();
        try {
            myObj.peerOne();
        } catch (IOException e) { //catch for exceptions
            e.printStackTrace();
        }
    }

    public void peerOne() throws IOException{
        s = new Socket("127.0.0.1", 9191); //connect to this computer on port 9191
        System.out.println("Server connected"); //gets run if not IOException

        Thread thread = new Thread(this); //new thread //'this' is the function!
        thread.start(); //run a while loop to check for incoming messages :)
        Scanner scanner = new Scanner(System.in); //helps get user input
        
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
            s.close(); //close socket
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
            String msgFromServer;

            //get's the server messages
            inputStreamReader = new InputStreamReader(s.getInputStream());

            //gets server message
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while (true) {
                msgFromServer = bufferedReader.readLine(); //stores server message
                if (!msgFromServer.equals("")) { //if server message isn't empty
                    System.out.println("Server -> client: " + msgFromServer); //then display to client
                }
            }
        } catch (IOException e) { //catch for connection errors!
            e.printStackTrace();
        }
    }

}
































//An Irritating attempt at threading :(
    //an actually working thingy-madoo! client to server and server to client 

/*import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client implements Runnable {
    Socket s = null;
    public static void main(String[] args) throws IOException {
        Client myObj = new Client();
        myObj.client();
    }

    public void client() throws IOException{
        s = new Socket("127.0.0.1", 9191);
        PrintWriter pr = new PrintWriter(s.getOutputStream());
        pr.println("hello");
        pr.flush();

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        String str = bf.readLine();
        System.out.println("client : " + str);
        

        InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(s.getOutputStream());

        //BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        
        Scanner scanner = new Scanner(System.in);
    
        Thread thread = new Thread(this);
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

        scanner.close();

        s.close();
    }

    @Override
    public void run() {
        try {
            OutputStreamWriter outputStreamWriter;
            InputStreamReader inputStreamReader;
            String msgFromClient;
            inputStreamReader = new InputStreamReader(s.getInputStream());
            outputStreamWriter = new OutputStreamWriter(s.getOutputStream());

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            Scanner scanner = new Scanner(System.in);
            while(true) {
                msgFromClient = bufferedReader.readLine();
                if (!msgFromClient.equals("")){
                    System.out.println("Server -> client: " + msgFromClient);
                }
                bufferedWriter.newLine();   
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}






// Marki










//Sends messages to the server
/*
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client{
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1", 9191);

        PrintWriter pr = new PrintWriter(s.getOutputStream());
        pr.println("hello");
        pr.flush();

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        String str = bf.readLine();
        System.out.println("client : " + str);
        

        InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(s.getOutputStream());

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        
        Scanner scanner = new Scanner(System.in);
    
        while (true) {
            String input = scanner.nextLine();
            bufferedWriter.write(input);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            System.out.println("Sever: " + bufferedReader.readLine());
            if(input.equalsIgnoreCase("BYE")){
                break;
            }
        }

        scanner.close();

        s.close();
    }
}
*/


//Does not work

/*import java.net.*; //get connetion
import java.io.*; //get input outputs
//import java.util.Scanner;


public class Client
{
    //initialize socket and input output streams
    private Socket socket = null;
    private BufferedReader input = null;
    private DataOutputStream out = null;

    public Client(String address, int port){
        //establish a connection
        address = "205.185.96.163";
        port = 9191;
        try {
            socket = new Socket(address, port); //java.net.Socket.Socket(String host, int port) throws UnknownHostException, IOException
                                                //Creates a stream socket and connects it to the specified port number on the named host.
            System.out.println("Connected");

            //takes input from terminal
            //Enter data using BufferReader
            input = new BufferedReader(new InputStreamReader(System.in));  
            /*
            * java.io.BufferedReader.BufferedReader(Reader in)
            * Creates a buffering character-input stream that uses a default-sized input buffer.
            * Parameters: 'in' A Reader
            *
            *java.io.InputStreamReader.InputStreamReader(InputStream in)
            * Creates an InputStreamReader that uses the default charset.
            * Parameters: 'in' An InputStream
            */
            /*out = new DataOutputStream(socket.getOutputStream()); 
        
        }
        catch(UnknownHostException u){
            System.out.println(u);
        }
        catch(IOException i){
            System.out.println(i);
        }

        //String to read message from input tab
        String line = "";

        //keep reading until "Over" is displayed on the screen
        while (!(line.equals("Over")))
        {
            try {
                //input = buffer-thing
                line = input.readLine();
                // print it! :)
                System.out.println(line);
            }
            catch (IOException i){
                System.out.println(i);
            }
        }

        //cloase the connection
        try {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i){
            System.out.println(i);
        }
    }

    public static void main(String args[]){
        Client client = new Client("205.185.96.163", 9191); //ip and phone #
    }

}*/