import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {

	private ServerSocket port;
	private OutputStream output;
	private BufferedReader br;
	
	public Serveur(){
		try {
			port = new ServerSocket(1028);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//this.output = this.port.;
		//this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
	}
	
	public void attendreCx() throws IOException{
		while(true){
			Socket s=port.accept();
			Thread t = new Thread(new FtpRequest(s));
			System.out.println("coucou");
			t.start();
		}
	}
	
	public void recevoir() throws IOException{
		this.br.readLine();
		
	}
	
	public static void main(String[] args) throws IOException {
		Serveur s = new Serveur();
		System.out.println("création du client");
		Socket client = new Socket(InetAddress.getLocalHost(), 1028);
		BufferedReader br_client = new BufferedReader(new InputStreamReader(client.getInputStream()));
		System.out.println("Attente du message");
		s.attendreCx();
		System.out.println("tentative de reception de message");
		br_client.readLine();
		System.out.println("message reçu");
	}
}
