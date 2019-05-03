package server; // 

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;


/**
 * Example of a LAN Recon tool, so that people stop using stupid ass tools to break into other peoples computers like little children.  */

public class DEZRecon {

    public static void main(String[] args) {
	// write your code here
        try {
            checkHosts("192.168.1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkHosts(String subnet) throws IOException {
        int timeout=2000;
        for (int i=1;i<255;i++){
            String host=subnet + "." + i;
            //System.out.println("Checking host: " + host + " ...");
            if (InetAddress.getByName(host).isReachable(timeout)){
                System.out.println(host + " is REACHABLE!");
                System.out.println("Scanning ports...");
                //testPorts(host, 65000);
            }
        }
    }

    static  void testPorts(String address, int range) {

        for (int i = 0; i < range; i ++) {
            if (isServerListening(address, i)) {
                //System.out.println("Server is listening on: " + i);
                //Socket sock = serverListening(address, i);
               // if (sock != null) {
                    System.out.println(address + " Is listening on port: " + i);
                   // interactSocket(sock, "".getBytes());
                //}
            }
        }
    }

    public static byte[] interactSocket(Socket socket, byte[] data) {
        try
        {
            System.out.println("Testing socket reply: " + socket.getInetAddress().toString());

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


            System.out.println("Writing data: " + new String(data));
            writer.write(new String(data));
            writer.flush();

            InputStream inputSream = socket.getInputStream();
            if (inputSream != null) {
                BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(inputSream));

                System.out.println("Reading reply...");

                Long line;
                while ((line = bufferedInputStream.lines().count()) > 0) {
                    System.out.println(socket.getInetAddress().toString() + " => Bytes recieved: " + line);
                }

                try {
                   // bufferedInputStream.close();
                } catch (Throwable t) {

                }
                System.out.println("Done, closing.");
            }
            //OutputStream outputStream = s.getOutputStream();


           // return true;
        }
        catch (Exception e)
        {
            //return new byte[0];
        }
        finally
        {
        }
        return new byte[0];


    }


    public static Socket serverListening(String host, int port)
    {
        Socket s = null;
        try
        {
            s = new Socket(   host, port);
            return s;
        }
        catch (Exception e)
        {
            return null;
        }
        finally
        {
            if(s != null)
                try {s.close();}
                catch(Exception e){}
            return null;
        }
    }

    public static boolean isServerListening(String host, int port)
    {
        Socket s = null;
        try
        {
            s = new Socket();
            s.setSoTimeout(1000);
            s.connect(new InetSocketAddress(host, port), 1000);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
        finally
        {
            if(s != null)
                try {s.close();}
                catch(Exception e){}
            return false;
        }
    }
}
