import java.awt.*;
import java.text.*;
import java.awt.font.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.util.*;
import java.lang.Character;

public class GuitarString extends JComponent{
	private int stringNumber;
	private char tuning;
	private Vector<Character> noteVector;
	private int length;
	private int x;
	private int y;
	private int beat;
	private int numStrings;

	public GuitarString(int stringNumber, char tuning, Vector<Character> noteVector, int numStrings) {
		this.stringNumber = stringNumber;
		this.tuning = tuning;
		this.noteVector = noteVector;
		length = noteVector.size();
		beat = 0;
		this.numStrings = numStrings;
	}
	public void draw (Graphics g){
		super.paintComponent(g);

		if (beat < 0){
			beat=0;
		}
		if (beat < noteVector.size()){
			x = 95 + (100*(numStrings-stringNumber)); 
			y = 500;

			g.setColor(Color.red);
			g.fillRect (x,0,35,550);
			g.setColor(Color.yellow);
			if (stringNumber == 1)
				g.fillRect (x,415,35,50);
			else 
				g.fillRect (x,415,100,50);

			g.setColor(Color.black);
				Font font = new Font("Arial", Font.PLAIN, 40);
				AttributedString scaledText = new AttributedString(tuning + "");
				scaledText.addAttribute(TextAttribute.FONT, font);
				g.drawString(scaledText.getIterator(), x+4, 520);
				font = new Font("Arial", Font.PLAIN, 60);
				if (noteVector.get(beat) == '.')
					scaledText = new AttributedString("  ");
				else
					scaledText = new AttributedString(noteVector.get(beat) + " ");
				scaledText.addAttribute(TextAttribute.FONT, font);
				g.drawString(scaledText.getIterator(), x, y-=40);
				x+=5;
				y-=40;
				for (int i=beat+1; i<noteVector.size(); i++){
					font = new Font("Arial", Font.PLAIN, 18);

					if (noteVector.get(i) == '.')
						scaledText = new AttributedString("  ");
					else
						scaledText = new AttributedString(noteVector.get(i) + " ");
					scaledText.addAttribute(TextAttribute.FONT, font);
					g.drawString(scaledText.getIterator(), x+8, y-=40);
			}
		}
		else{	
				Font font = new Font("Arial", Font.PLAIN, 50);
				AttributedString scaledText = new AttributedString("Song Over");
				scaledText.addAttribute(TextAttribute.FONT, font);
				g.drawString(scaledText.getIterator(), 300, 300);
		
		}
	}
		//g.drawString (printNotes (beat), x, y);
	public int length (){
		return length;
	}
	public char tuning (){
		return tuning;
	}
	public String printNotes(int beat){
		String noteString = new String("");
		for (int i=beat; i<noteVector.size(); i++){
			noteString = noteString.concat(noteVector.get(i) + " ");
		}
		return noteString;
	}
	public void printTuning(){
		System.out.println ("Notes: " + tuning);
	}

	public void setBeat (int beat){
		this.beat = beat;
	}

	

}