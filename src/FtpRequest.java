import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


public class FtpRequest implements Runnable{
	
	private Socket s;
	private OutputStream output;
	private BufferedReader br;
	
	public FtpRequest(Socket s) throws IOException{
		System.out.println("création de la socket");
		this.s=s;
		System.out.println("Création de la sortie");
		this.output = this.s.getOutputStream();
		System.out.println("création de la lecture");
		this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		System.out.println("lecture");
		//br.readLine(); Récupération d'un message
		System.out.println("lu");
	}
	
	public void ProcessRequest(){
		
	}

	@Override
	public void run() {
		System.out.println("it works");
		try {
			output.write("200 Service OK".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
