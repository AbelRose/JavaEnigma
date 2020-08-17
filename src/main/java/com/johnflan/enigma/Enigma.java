package com.johnflan.enigma;

import com.johnflan.enigma.EnigmaMachine;
import com.johnflan.enigma.EnigmaMachineBuilder;
import com.johnflan.enigma.scrambler.plugboard.Plugboard;
import com.johnflan.enigma.scrambler.plugboard.PlugboardImpl;
import com.johnflan.enigma.scrambler.reflector.ReflectorType;
import com.johnflan.enigma.scrambler.rotor.RotorType;

import java.io.*;

public class Enigma {


	public static String readFileByChars(String filePath) throws Exception{
		File file = new File(filePath);
		if(!file.exists() || !file.isFile()){
			return null;
		}

		StringBuffer content = new StringBuffer();
		try {
			char[] temp = new char[1024];
			FileInputStream fileInputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			while(inputStreamReader.read(temp) != -1){
				content.append(new String(temp));
				temp = new char[1024];
			}

			fileInputStream.close();
			inputStreamReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content.toString();
	}



	public static void main(String[] args) throws Exception {
		
		Plugboard plugboard = new PlugboardImpl();
		plugboard.addCable('A', 'H').addCable('Q', 'C').addCable('P', 'Z');
		
		EnigmaMachine enigmaMachine = EnigmaMachineBuilder
				.addRotor1(RotorType.I, 'H')
				.addRotor2(RotorType.II, 'A')
				.addRotor3(RotorType.III, 'A')
				.addReflector(ReflectorType.B)
				.addPlugBoard(plugboard)
				.build();

		String filepath = "D:\\MaTrix\\JavaEnigma\\src\\main\\java\\com\\johnflan\\enigma\\Cipher.txt";

		String cipher = readFileByChars(filepath);

		System.out.println(cipher);


		String pt = "BLBDH";
		String ct = enigmaMachine.encrypt(pt);
		
		System.out.println(pt);
		System.out.println(ct);
	}
}
