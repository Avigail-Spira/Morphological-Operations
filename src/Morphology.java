import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class Morphology {
	int numImgRows, numImgCols;
	int imgMin, imgMax;
	int numStructRows, numStructCols;
	int structMin, structMax;
	int rowOrigin, colOrigin;
	int rowFrameSize;
	int colFrameSize;
	int extraRows;
	int extraCols;
	int rowSize;
	int colSize;
	int[][] zeroFramedAry; 
	int[][] morphAry;
	int[][] tempAry;
	int[][] structAry;
	
	
	Morphology(int numImgRows, int numImgCols, int imgMin, int imgMax, int numStructRows, int numStructCols, int structMin, int structMax, int rowOrigin, int colOrigin) {
		this.numImgRows = numImgRows;
		this.numImgCols = numImgCols;
		this.imgMin = imgMin;
		this.imgMax = imgMax;
		this.numStructRows = numStructRows;
		this.numStructCols = numStructCols;
		this.structMin = structMin;
		this.structMax = structMax;
		this.rowOrigin = rowOrigin;
		this.colOrigin = colOrigin;
		
		this.rowFrameSize = numStructRows/2;
		this.colFrameSize = numStructCols/2;
		this.extraRows = rowFrameSize * 2;
		this.extraCols = colFrameSize * 2;
		this.rowSize = numImgRows + extraRows;
		this.colSize = numImgCols + extraCols;
		
		this.zeroFramedAry = new int[this.rowSize][this.colSize];
		this.morphAry = new int[this.rowSize][this.colSize];
		this.tempAry =  new int[this.rowSize][this.colSize];
		this.structAry = new int[this.numStructRows][this.numStructCols];
		
	}
	
	void zero2DAry(int[][] Ary, int nRows, int nCols) {
		for (int i = 0; i < nRows; i++) {
		    for (int j = 0; j < nCols; j++) {
		        Ary[i][j] = 0;
		    } 
		}

	}
	
	void loadImg(Scanner s) {
		for (int i=rowFrameSize; i<rowSize-rowFrameSize; i++ ) {
			for (int j=colFrameSize; j<colSize-colFrameSize; j++ ) {
				if (s.hasNextInt())
					this.zeroFramedAry[i][j] = s.nextInt();
			}
		}
	} 
	
	void loadstruct(Scanner s) {
		for (int i=0; i<numStructRows; i++) {
			for (int j=0; j<numStructCols; j++) {
				if (s.hasNextInt())
					this.structAry[i][j] = s.nextInt();
			}
		}
	}
	
	void computeDilation(int[][] inAry, int[][] outAry) {
		int i = rowFrameSize;
		
		while (i < rowSize-rowFrameSize) {
			int j = colFrameSize;
			
			while (j < colSize-colFrameSize) {
				if (inAry[i][j] > 0)
					onePixelDilation(i, j, inAry, outAry);
				j++;
			}
			i++;
		}
		
	}
	
	
	void computeErosion(int[][] inAry, int[][] outAry) {
		int i = rowFrameSize;
		
		while (i < rowSize-rowFrameSize) {
			int j = colFrameSize;
			
			while(j < colSize-colFrameSize) {
				if (inAry[i][j] > 0)
					onePixelErosion(i,j,inAry,outAry);
				j++;
			}
			i++;
		}
		
	}
	
	void computeOpening(int[][] inAry, int[][] outAry, int[][] temp) {
		computeErosion(inAry, temp);
		computeDilation(temp, outAry);
	}
	
	
	void computeClosing(int[][] inAry, int[][] outAry, int[][] temp) {
		computeDilation(inAry, temp);
		computeErosion(temp, outAry);
	}
	
	void onePixelDilation(int i, int j, int[][] inAry, int[][] outAry) {		
		int iOffset = i - rowOrigin;
		int jOffset = j - colOrigin;
		
		int rIndex = 0;
		int cIndex = 0;
		
		while (rIndex<numStructRows) {
			cIndex = 0;
			while (cIndex<numStructCols) {
				if (structAry[rIndex][cIndex] > 0) {
					outAry[i][j] = 1;
					outAry[iOffset+rIndex][jOffset+cIndex] = 1;
				}
				cIndex++;
			}
			rIndex++;
		}
	}
	
	
	void onePixelErosion(int i, int j, int[][] inAry, int[][] outAry) {
		int iOffset = i - rowOrigin;
		int jOffset = j - colOrigin;
		boolean matchFlag = true;
		
		int rIndex = 0;
		int cIndex = 0;
		
		while (matchFlag==true && rIndex<numStructRows) {
			cIndex = 0;
			while (matchFlag==true && cIndex<numStructCols) {
				if ((structAry[rIndex][cIndex] > 0) && (inAry[iOffset+rIndex][jOffset+cIndex] <= 0))
					matchFlag = false;
				cIndex++;
			}
			rIndex++;
		}
		
		if (matchFlag == true)
			outAry[i][j] = 1;
		else
			outAry[i][j] = 0;

	}
	
	void AryToFile(int[][] Ary, BufferedWriter outFile) throws IOException {
		outFile.write(numImgRows + " " + numImgCols + " " + imgMin + " " + imgMax + " ");
		outFile.newLine();
		for (int i=rowOrigin; i<rowSize-rowOrigin; i++ ) {
			for (int j=colOrigin; j<colSize-colOrigin; j++ ) {
				outFile.write(Ary[i][j] + " ");
			}
			outFile.newLine();
		}
	}
	
	void prettyPrint(int[][] Ary, BufferedWriter outFile) throws IOException {
		for (int i=0; i<Ary.length; i++) {
			for (int j=0; j<Ary[i].length; j++) {
				if (Ary[i][j] == 0)
					outFile.write(". ");
				else
					outFile.write(Ary[i][j] + " ");
			}
			outFile.newLine();
		}
		outFile.newLine();
	}
	
	
}