import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.util.*;
import java.lang.Character;
import java.text.*;
import java.awt.font.*;
import java.awt.event.*;

public class Song extends JComponent{
	private int numStrings;
	private float tempo;
	private int sync;
	private Vector<Character> tempVector;
	private GuitarString[] guitarStrings;
	private JLabel[] track;
	private int length;
	private int beat;


	public Song () {
		beat = 0;

	}

	public void loadSong (String fileName){
		tempVector = new Vector<Character>();
		try {
			System.out.println(fileName);
			FileInputStream fstream = new FileInputStream(fileName);

			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int i=0;
			while ((strLine = br.readLine()) != null)   {
				if (i==0){
					numStrings = Integer.parseInt(strLine);
					guitarStrings = new GuitarString[numStrings];
				}
				else if (i<=numStrings){
					tempVector = new Vector<Character>();
					for (int j=4; j<strLine.length(); j+=2){
						tempVector.add(strLine.charAt(j));
					}
					guitarStrings[i-1] = new GuitarString(i, strLine.charAt(0), tempVector, numStrings);
				}
				else if (i==numStrings+1) {
					tempo = Float.parseFloat(strLine);
				}
				else{
					sync = Integer.parseInt(strLine);
				}
				i++;
			}
			in.close();
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
		track = new JLabel[numStrings];
		buildTrack(0);
		length = guitarStrings[0].length();
	}

	public void saveSong (String fileName){
		tempVector = new Vector<Character>();
		try {
			System.out.println(fileName);

			FileWriter fstream = new FileWriter(fileName);

			BufferedWriter bw = new BufferedWriter(fstream);
			bw.write(Integer.toString(numStrings));
			bw.newLine();
			for (int i = 0; i<numStrings; i++){
				bw.write(guitarStrings[i].tuning() + " | ");
				bw.write(guitarStrings[i].printNotes(0));
				bw.newLine();
			}
			bw.write(Float.toString(tempo));
			bw.newLine();
			bw.write(Integer.toString(sync));
			bw.close();
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}

	public int length (){
		return length;
	}
	public int sync (){
		return sync;
	}
	public int numStrings (){
		return numStrings;
	}
	public float tempo (){
		return tempo;
	}
	public void setTempo (float tempo){
		this.tempo = tempo;
	}
	public void setSync (int sync){
		this.sync = sync;
	}

	public void paintComponent (Graphics g){
		super.paintComponent(g);
		for (int i=0; i<numStrings; i++){
			guitarStrings[i].draw(g);
		}
	}


	public JLabel[] buildTrack(int beat){
		for (int i=0; i<numStrings; i++){
			track[i] = new JLabel(guitarStrings[i].printNotes(beat));
		}
		return track;
	}

	public void setBeat (int beat){
		this.beat = beat;
		for (int i=0; i<numStrings; i++){
			guitarStrings[i].setBeat(beat);
		}
	}

	public void printSong (int beat){
		System.out.println ("Strings: " + numStrings);
		System.out.println ("Tempo: " + tempo);
		System.out.println ("Sync: " + sync);
		for (int i=0;i<numStrings;i++){
			System.out.println ("Notes: " + guitarStrings[i].printNotes(beat));
		}
	}

}

