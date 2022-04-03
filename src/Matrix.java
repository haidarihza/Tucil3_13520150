import java.io.*;
import java.util.*;

public class Matrix {
	//Class untuk matrix puzzle yang akan dibuat
	private ArrayList<String> step;
	private int[][] matrix;
	private int depth;

	public Matrix() {
		//randomly generate a matrix
		step = new ArrayList<String>();
		this.matrix = new int[4][4];
		ArrayList<Integer> mylist = new ArrayList<Integer>();
        for (int i = 0; i < 16; i++) {
            mylist.add(i);
        } 
        Collections.shuffle(mylist);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				matrix[i][j] = mylist.get(i*4+j);
			}
		}
		this.depth = 0;
	}

	public Matrix(File file) throws FileNotFoundException {
		//read a matrix from a file
		step = new ArrayList<String>();
		this.depth = 0;
		this.matrix = new int[4][4];
		//read from txt file
		int row = 0;
		File myObj = file;
		Scanner myReader = new Scanner(myObj);
		while (myReader.hasNextLine()) {
			String data = myReader.nextLine();
			String[] data_split = data.split(" ");
			for (int i = 0; i < 4; i++) {
				matrix[row][i] = Integer.parseInt(data_split[i]);
			}
			row++;
		}
		myReader.close();
	}

	public Matrix(Matrix m) {
		//copy a matrix
		step = new ArrayList<String>();
		this.depth = m.depth;
		this.matrix = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				this.matrix[i][j] = m.matrix[i][j];
			}
		}
		for (int i = 0; i < m.step.size(); i++) {
			this.step.add(m.step.get(i));
		}
	}

	public void setElement(int i, int j, int value) {
		//mengubah nilai elemen pada matrix[i][j] menjadi value
		matrix[i][j] = value;
	}

	public void add_step(String step) {
		//menambahkan langkah yang telah dilakukan
		this.step.add(step);
	}

	public int get_element(int i, int j) {
		//Mengembalikan nilai elemen matrix[i][j]
		return this.matrix[i][j];
	}
	public Coordinate getEmptyPos() {
		//Mengembalikan koordinat kosong (dalam hal ini angka 0)
		Coordinate zero = new Coordinate();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (matrix[i][j] == 0) {
					zero.setX(i);
					zero.setY(j);
					return zero;
				}
			}
		}
		return zero;
	}

	public void printMatrix() {
		//Mencetak matrix ke layar
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void move(String move) {
		//mengubah posisi angka sesuai dengan perintah yang diberikan
		Coordinate emptyPos = this.getEmptyPos();
		if (move.equals("up")) {
			int temp = this.matrix[emptyPos.getX()][emptyPos.getY()];
			this.matrix[emptyPos.getX()][emptyPos.getY()] = this.matrix[emptyPos.getX() - 1][emptyPos.getY()];
			this.matrix[emptyPos.getX() - 1][emptyPos.getY()] = temp;
			this.step.add("up");
			this.inc_depth();
		}
		else if (move.equals("down")) {
			int temp = this.matrix[emptyPos.getX()][emptyPos.getY()];
			this.matrix[emptyPos.getX()][emptyPos.getY()] = this.matrix[emptyPos.getX() + 1][emptyPos.getY()];
			this.matrix[emptyPos.getX() + 1][emptyPos.getY()] = temp;
			this.step.add("down");
			this.inc_depth();
		}
		else if (move.equals("left")) {
			int temp = this.matrix[emptyPos.getX()][emptyPos.getY()];
			this.matrix[emptyPos.getX()][emptyPos.getY()] = this.matrix[emptyPos.getX()][emptyPos.getY() - 1];
			this.matrix[emptyPos.getX()][emptyPos.getY() - 1] = temp;
			this.step.add("left");
			this.inc_depth();
		}
		else if (move.equals("right")) {
			int temp = this.matrix[emptyPos.getX()][emptyPos.getY()];
			this.matrix[emptyPos.getX()][emptyPos.getY()] = this.matrix[emptyPos.getX()][emptyPos.getY() + 1];
			this.matrix[emptyPos.getX()][emptyPos.getY() + 1] = temp;
			this.step.add("right");
			this.inc_depth();
		}
	}

	public void inc_depth() {
		//menambah kedalaman simpul i
		this.depth++;
	}

	public int get_depth() {
		//mengembalikan nilai kedalaman simpul i
		return this.depth;
	}

	public int gp() {
		//ongkos mencapai simpul tujuan dari simpul i
		int count = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if ((this.matrix[i][j] != 0) && (this.matrix[i][j] != i*4+j+1)) {
					count++;
				}
			}
		}
		return count;
	}

	public int cost() {
		//Mengembalikan nilai cost dari suatu simpul
		return this.gp() + this.depth;
	}

	public boolean isFinish() {
		boolean isFinish = true;
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				list.add(this.matrix[i][j]);
			}
		}
		for (int i = 0; i < 15; i++) {
			if (list.get(i) != i+1) {
				isFinish = false;
			}
		}
		return isFinish;
	}

	public String step_before() {
		//mengembalikan perintah yang dilakukan sebelumnya
		String step = "";
		if (this.step.size() == 0) {
			step = "null";
		}
		else{
			step = this.step.get(this.step.size() - 1);
		}
		return step;
	}

	public ArrayList<String> get_step() {
		return this.step;
	}
}
