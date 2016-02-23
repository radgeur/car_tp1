import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class FtpRequest implements Runnable{
	
	//ATTRIBUTS
	private final static String USER = "toto";
	private final static String PASSWORD = "toto";
	
	private Socket s;
	//private PrintStream print;
	private OutputStream out;
	private BufferedReader br;
	private File currentRepository;
	private boolean isUserValid;
	private boolean isConnected;
	
	
	//METHODS
	/** Constructor
	 * @param s the socket to connect
	 * @param repo the repository that user could access
	 * @throws IOException
	 */
	public FtpRequest(Socket s, String repo) throws IOException{
		this.s=s;
		//print = new PrintStream(this.s.getOutputStream());
		out = s.getOutputStream();
		this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		currentRepository = new File(repo);
		isUserValid = false;
		isConnected = false;
	}

	/** send a message to the server
	 * @param string message to send at the server
	 * @throws IOException 
	 */
	public void write(String string) throws IOException {
		//print.println(string);
		string += "\n";
		out.write(string.getBytes());
		out.flush();
	}
	
	/** receiver a message from the server
	 * @return message send by the server
	 * @throws IOException
	 */
	public String receive() throws IOException {
		return br.readLine();
	}
	
	/** match string to know which method to execute
	 * @param string to match
	 * @throws IOException
	 */
	public void ProcessRequest(String string) throws IOException{
		System.out.println(string);
		String[] tab = string.split(" ");
		switch(tab[0].toLowerCase()) {
			case "user":
				processUSER(tab[1]);
				break;
			case "pass":
				processPASS(tab[1]);
				break;
			case "retr":
				if(userIsConnected())
					processRETR();
				break;
			case "store":
				if(userIsConnected())
					processSTOR();
				break;
			case "ls":
				if(userIsConnected())
					processLIST();
				break;
			case "quit":
				processQUIT();
				break;
			default:
				write("Commande invalide");
				return;
		}
	}
	
	/** return if the user is connected or not
	 * @return true if the user is connected else false
	 * @throws IOException 
	 */
	public boolean userIsConnected() throws IOException{
		if(!isConnected)
			write("Vous nêtes pas encore connecté pour pouvoir effectuer cette action");
		return isConnected;
	}
	
	/** process who the user enter his user name
	 * @param string the user name
	 * @throws IOException 
	 */
	public void processUSER(String string) throws IOException {
		if(string.equals(USER)){
			isUserValid = true;
			write("331 Nom d'utilisateur valide, en attente du mot de passe");
		} else 
			write("430 Nom d'utilisateur non valide");
	}
	
	/** process who the user enter his password
	 * @param string the user's password
	 * @throws IOException 
	 */
	public void processPASS(String string) throws IOException {
		if(!isUserValid){
			write("430 Vous n'avez pas encore saisi votre nom d'utilisateur ou il n'est pas valide");
			return;
		}
		
		if(string.equals(PASSWORD)){
			isConnected = true;
			write("230 Mot de Passe correct");
		} else 
			write("430 Mot de Passe incorrect");
	}
	
	/** move a file one the computer */
	public void processRETR() {
		
	}
	
	/** move a file on the server */
	public void processSTOR() {
		
	}
	
	/** display the list of the files who the user is actually 
	 * @throws IOException */
	public void processLIST() throws IOException {
		Set<File> files = new HashSet<File>(Arrays.asList(currentRepository.listFiles()));
		for (File f : files) {
			write(f.toString());
		}
	}
	
	/** process to disconnect from the server */
	public void processQUIT() throws IOException {
		s.close();
	}
	
	@Override
	public void run() {
		try {
			this.write("200 Veuillez vous identifiez");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String string = null;
		try {
			string = receive();
		} catch (IOException e1) {}
		while(string != "quit") {
			try {
				ProcessRequest(string);
			} catch (IOException e) {}
			try {
				string = receive();
			} catch (IOException e) {}
		}
		try {
			ProcessRequest(string);
		} catch (IOException e) {}
	}
}
