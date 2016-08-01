import java.io.*;
import java.util.*;

public class DeepestPath {

	private static int rows = 0;
	private static int cols = 0;

	public static void main(String[] args) throws IOException {

		int[][] ar = constructArrayFromFile();
		/*int[][] ar = {
						{4,8,7,3},
						{2,5,9,3},
						{6,3,2,5},
						{4,4,1,6},
					 };*/
		int[][] tmp = new int[rows][cols];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				tmp[i][j] = -1;
			}
		}
		int result = maxLengthPath(ar, tmp, rows, cols);
		System.out.println("length: " + result);
	}

	private static int[][] constructArrayFromFile() throws IOException{	
		int[][] ar;
		BufferedReader br = new BufferedReader(new FileReader("map.txt"));
		try {
			String line = br.readLine();
			if(null != line)
			{
				String[] size = line.split("\\s+");
				rows = Integer.parseInt(size[0]);
				cols = Integer.parseInt(size[1]);
			}
			ar = new int[rows][cols];
			String input = br.readLine();
			int i = 0;
			while(null != input)
			{
				String[] num = input.split("\\s+");
				for(int j = 0; j < cols; j++) {
					ar[i][j] = Integer.parseInt(num[j]);
				}
				i++;
				input = br.readLine();
			}
		}
		finally {
			br.close();
		}
		return ar;
	}

	private static int maxLengthPath(int[][] ar, int[][] tmp, int rows, int cols) {
		int result = 1;
		int maximumValue = Integer.MIN_VALUE;

		for(int i = 0; i < rows; i++) 
		{
			for(int j = 0; j < cols; j++) 
			{
				if(tmp[i][j] == -1) 
				{
					tmp[i][j] = findPathFromACell(i, j, ar, tmp);
				}
				if(tmp[i][j] >= result) {
					result = tmp[i][j];
					if(ar[i][j] > maximumValue)
						maximumValue = ar[i][j];
				}
			}
		}
		System.out.println("Drop: " + (maximumValue - 1));
		return result;
	}

	private static int findPathFromACell(int i, int j, int[][] ar, int[][] tmp) {
		int rows = ar.length;
		if(tmp[i][j] != -1)
			return tmp[i][j];

		if(j < rows-1 && (ar[i][j] > ar[i][j+1])){
			tmp[i][j] = 1 + findPathFromACell(i, j+1, ar, tmp);
		}

		if(j > 0 && (ar[i][j] > ar[i][j-1])){
			tmp[i][j] = Math.max(tmp[i][j], (1 + findPathFromACell(i, j-1, ar, tmp)));
		}

		if(i > 0 && (ar[i][j] > ar[i-1][j])){
			tmp[i][j] = Math.max(tmp[i][j], (1+findPathFromACell(i-1, j, ar, tmp)));
		}

		if(i < rows-1 && (ar[i][j] > ar[i+1][j])){
			tmp[i][j] = Math.max(tmp[i][j], (1+findPathFromACell(i+1, j, ar, tmp)));
		}

		if(tmp[i][j] == -1)
			tmp[i][j] = 1;

		return tmp[i][j];
	}
}
