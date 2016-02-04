import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class FtpRequest implements Runnable{
	
	private Socket s;
	private PrintStream print;
	private BufferedReader br;
	private String currentRepository;
	
	public FtpRequest(Socket s, String repo) throws IOException{
		this.s=s;
		print = new PrintStream(this.s.getOutputStream());
		this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		currentRepository = repo;
	}

	public void write(String string) {
		print.println(string);
	}
	
	public String receive() throws IOException {
		return br.readLine();
	}
	
	public void ProcessRequest(String string){
		switch(string) {
			case "user":
				;
			case "pass":
				;
			case "retr":
				;
			case "store":
				;
			case "quit":
				;
			default:
				return;
		}
	}
	
	public void processUSER() {
		
	}
	
	public void processPASS() {
		
	}
	
	public void processRETR() {
		
	}
	
	public void processSTOR() {
		
	}
	
	public void processLIST() {
		
	}
	
	public void processQUIT() throws IOException {
		s.close();
	}
	
	@Override
	public void run() {
		this.write("200 Service OK");
		while(true) {
			String string = null;
			try {
				string = receive();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ProcessRequest(string);
		}
	}
}
