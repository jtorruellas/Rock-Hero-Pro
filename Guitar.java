import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;
import java.util.*;
import java.lang.Character;

public class Guitar extends JFrame  {
	private static final long serialVersionUID = 123456789;
	private int beat;
	private boolean songOver;
	private Song newSong;
	private javax.swing.Timer songTimer;
	private Player player;
	private boolean mp3Loaded = false;


    public static void main (String [] args) throws IOException {
	new Guitar();
    }

    public Guitar () {

	setLocation (100, 100);
	setSize (800, 800);
	setDefaultCloseOperation (EXIT_ON_CLOSE);
	final Container content = getContentPane();
	content.setLayout (new BorderLayout());
	newSong = new Song();
	content.add(newSong, BorderLayout.CENTER);

	JPanel controls = new JPanel ();
	controls.setBorder (new LineBorder(Color.blue, 2));
	controls.setLayout (new FlowLayout());
	content.add (controls, BorderLayout.SOUTH);
	JPanel settings = new JPanel ();
	settings.setBorder (new LineBorder(Color.blue, 2));
	settings.setLayout (new FlowLayout());
	content.add (settings, BorderLayout.NORTH);
   	final JTextField fileName = new JTextField (15);
	settings.add (fileName);
   	JButton loadButton = new JButton ("Load");
	settings.add (loadButton);
	JButton saveButton = new JButton ("Save");
	settings.add (saveButton);
	final JTextField mp3File = new JTextField (15);
	settings.add (mp3File);
   	JButton mp3LoadButton = new JButton ("Load");
	settings.add (mp3LoadButton);
	JButton goButton = new JButton ("Go");
	controls.add (goButton);	
	JButton pauseButton = new JButton ("Pause");
	controls.add (pauseButton);	
	JButton stopButton = new JButton ("Stop");
	controls.add (stopButton);	
	final JTextField tempoField = new JTextField (10);
	controls.add (tempoField);	
	final JTextField syncField = new JTextField (10);
	controls.add (syncField);	


	
	
	loadButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent addClick){
			
			String fn = fileName.getText();
			newSong.loadSong(fn);
			newSong.printSong(0);
			tempoField.setText(Float.toString(newSong.tempo()));
			syncField.setText(Integer.toString(newSong.sync()));
			beat = newSong.sync();
			newSong.repaint();
		}	
	});
	saveButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent addClick){
			newSong.setTempo(Float.parseFloat(tempoField.getText()));
			newSong.setSync(Integer.parseInt(syncField.getText()));
			String fn = fileName.getText();
			newSong.saveSong(fn);
		}	
	});
	mp3LoadButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent addClick){
			
			String fn = mp3File.getText();
			loadMP3(fn);

		}	
	});

		
	final ActionListener playSong = new ActionListener(){
		public void actionPerformed(ActionEvent evt){
			if (mp3Loaded)
				player.start();
			
			if (beat < newSong.length()){
				System.out.println("Step ");
				//newSong.printSong(beat);
				newSong.setBeat(beat);
				newSong.repaint();
				beat++;
			}
			else {
					System.out.println ("Song Over");
					if (mp3Loaded)
						player.stop();
					songTimer.stop();
			}
			
	}};
	

	goButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent addClick){
			songTimer = new javax.swing.Timer((int)(newSong.tempo()*(100/6)), playSong);
			songTimer.start();
			
		}});	
	pauseButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent addClick){
			if (mp3Loaded)
				player.stop();
			songTimer.stop();
			
		}});	

	stopButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent addClick){
			songTimer.stop();
			if (mp3Loaded){
				player.stop();
				String fn = mp3File.getText();
				loadMP3(fn);
			}
			beat = newSong.sync();	
			newSong.setBeat(beat);
			newSong.repaint();		
		}});

	setVisible (true);
    }


//Tapan Desai - StackOverflow (http://stackoverflow.com/questions/12293071/how-to-play-an-mp3-file-using-java)
	public void loadMP3(String mp3File) {

		Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
		Format input2 = new AudioFormat(AudioFormat.MPEG);
		Format output = new AudioFormat(AudioFormat.LINEAR);
		PlugInManager.addPlugIn(
			"com.sun.media.codec.audio.mp3.JavaDecoder",
			new Format[]{input1, input2},
			new Format[]{output},
			PlugInManager.CODEC
		);
		try{
			player = Manager.createPlayer(new MediaLocator(new File(mp3File).toURI().toURL()));
			System.out.println("Song " + mp3File + " loaded");
			mp3Loaded = true;
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	



}












	