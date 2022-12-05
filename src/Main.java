import java.io.*;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {

		try (Scanner input1 = new Scanner(new BufferedReader(new FileReader(args[0])));
				Scanner input2 = new Scanner(new BufferedReader(new FileReader(args[1])));
				BufferedWriter output1 = new BufferedWriter(new FileWriter(args[2]));
				BufferedWriter output2 = new BufferedWriter(new FileWriter(args[3]));
				BufferedWriter output3 = new BufferedWriter(new FileWriter(args[4]));
				BufferedWriter output4 = new BufferedWriter(new FileWriter(args[5]));
				BufferedWriter output5 = new BufferedWriter(new FileWriter(args[6]))) {
			
			int numRows = 0;
			if (input1.hasNextInt())
				numRows = input1.nextInt();
			int numCols = 0;
			if (input1.hasNextInt())
				numCols = input1.nextInt();
			int minVal = 0;
			if (input1.hasNextInt())
				minVal = input1.nextInt();
			int maxVal = 0;
			if (input1.hasNextInt())
				maxVal = input1.nextInt();

			int structRows = 0;
			if (input2.hasNextInt())
				structRows = input2.nextInt();
			int structCols = 0;
			if (input2.hasNextInt())
				structCols = input2.nextInt();
			int structMin = 0;
			if (input2.hasNextInt())
				structMin = input2.nextInt();
			int structMax = 0;
			if (input2.hasNextInt())
				structMax = input2.nextInt();
			int rowOrigin = 0;
			if (input2.hasNextInt())
				rowOrigin = input2.nextInt();
			int colOrigin = 0;
			if (input2.hasNextInt())
				colOrigin = input2.nextInt();
			
			Morphology m = new Morphology(numRows, numCols, minVal, maxVal, structRows, structCols, structMin,
					structMax, rowOrigin, colOrigin);

			m.zero2DAry(m.zeroFramedAry, m.rowSize, m.colSize);
			m.loadImg(input1);
			output5.write("Input image:");
			output5.newLine();
			m.prettyPrint(m.zeroFramedAry, output5); 
			
			
			m.zero2DAry(m.structAry, m.numStructRows, m.numStructCols);
			m.loadstruct(input2);
			output5.write("Structuring Element:");
			output5.newLine();
			m.prettyPrint(m.structAry, output5);
			
			// Dilation
			m.zero2DAry(m.morphAry, m.rowSize, m.colSize);
			m.computeDilation(m.zeroFramedAry, m.morphAry);
			m.AryToFile(m.morphAry, output1);
			output5.write("Dilation on input image:");
			output5.newLine();
			m.prettyPrint(m.morphAry, output5);
			
			// Erosion
			m.zero2DAry(m.morphAry, m.rowSize, m.colSize);
			m.computeErosion(m.zeroFramedAry, m.morphAry);
			m.AryToFile(m.morphAry, output2);
			output5.write("Erosion on input image:");
			output5.newLine();
			m.prettyPrint(m.morphAry, output5);
			
			// Opening
			m.zero2DAry(m.morphAry, m.rowSize, m.colSize);
			m.computeOpening(m.zeroFramedAry, m.morphAry, m.tempAry);
			m.AryToFile(m.morphAry, output4);
			output5.write("Opening on input image:");
			output5.newLine();
			m.prettyPrint(m.morphAry, output5);

			// Closing
			m.zero2DAry(m.morphAry, m.rowSize, m.colSize);
			m.computeClosing(m.zeroFramedAry, m.morphAry, m.tempAry);
			m.AryToFile(m.morphAry, output3);
			output5.write("Closing on input image:");
			output5.newLine();
			m.prettyPrint(m.morphAry, output5);

		}
	}

}