import java.io.File;
import java.io.FileNotFoundException;

public class Process {
	//Class untuk memproses Matrix/Puzzle
	private Matrix mat_start;
	private Matrix mat_end;
	private double time_process;
	private int simpul;
	
	public Process(File file) throws FileNotFoundException {
		if (file == null) {
			this.mat_start = new Matrix();			
		}
		else {
			this.mat_start = new Matrix(file);
		}
	}
	
	public int[] get_arr_mat_start() {
		//Mengembalikan susunan angka dalam array satu dimensi
		int[] arr = new int[16];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				arr[i*4+j] = this.mat_start.get_element(i, j);
			}
		}
		return arr;
	}
	
	public int[] get_arr_mat_end() {
		//Mengembalikan susunan angka dalam array satu dimensi
		int[] arr = new int[16];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				arr[i*4+j] = this.mat_end.get_element(i, j);
			}
		}
		return arr;
	}

	public boolean isSolvable() {
		//Mengembalikan boolean apakah matrix dapat diselesaikan atau tidak
        if ((get_kurangX()%2) != 0) {
        	return false;
        }
        else {
        	return true;
        }
	}
	
	public int get_kurangX() {
		//Mengembalikan nilai Sigma Kurang + X
		int[] kurang = this.get_kurang();
		int total = 0;
		for (int i = 0; i < 16; i++) {
			total += kurang[i];
		}
		Coordinate zero = this.mat_start.getEmptyPos();
		if ((zero.getX() == 0) || (zero.getX() == 2)) {
			if (zero.getY() == 1){
				total++;
			}
			if (zero.getY() == 3){
				total++;
			}
		}
		if ((zero.getX() == 1) || (zero.getX() == 3)){
			if (zero.getY() == 0){
				total++;
			}
			if (zero.getY() == 2){
				total++;
			}
		}
		return total;
	}
	
	public int[] get_kurang() {
		//Mengembalikan nilai kurang(i)
		int[] kurang = new int[16];
		int[] list = new int[16];
		int count;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (this.mat_start.get_element(i, j) == 0) {
					list[i*4+j] = 16;
				}
				else {
					list[i*4+j] = this.mat_start.get_element(i,j);					
				}
			}
		}
		for (int i = 0; i < 16; i++) {
			count = 0;
			if (list[i] != 0){
				for (int j = i+1; j < 16; j++) {
					if ((list[j] < list[i]) && (list[j] != 0)){
						count++;
					}
				}
			}
			kurang[list[i]-1] = count;
		}
		return kurang;
	}
	
	public Matrix get_mat_start() {
		return this.mat_start;
	}
	
	public Matrix get_mat_end() {
		return this.mat_end;
	}
	
	public double get_time_process() {
		return this.time_process;
	}
	
	public int get_total_simpul() {
		return this.simpul;
	}
	
	public void process() {
		int count = 0;
		long start_time = System.nanoTime();
		if (!this.isSolvable()) {
			this.mat_end = new Matrix(this.mat_start);
		}
		else {
	        MyPriorityQueue pq = new MyPriorityQueue();
	        Matrix curr_mat = new Matrix(this.mat_start);
	        count++;
	        while (!curr_mat.isFinish()) {
	            Coordinate curr_zero = curr_mat.getEmptyPos();
	            if (curr_zero.getX() == 0) {
	                if (curr_zero.getY() == 0) {
	                    if (curr_mat.step_before().equals("left")) {
	                        Matrix next_mat = new Matrix(curr_mat);
	                        next_mat.move("down");
	                        pq.enqueue(next_mat);
	                        count++;
	                    }
	                    else if (curr_mat.step_before().equals("up")) {
	                        Matrix next_mat = new Matrix(curr_mat);
	                        next_mat.move("right");
	                        pq.enqueue(next_mat);
	                        count++;
	                    }
	                    else{
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("down");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("right");
	                        pq.enqueue(next_mat2);
	                        count++;
	                    }
	                }
	                else if (curr_zero.getY() == 3) {
	                    if (curr_mat.step_before().equals("right")) {
	                        Matrix next_mat = new Matrix(curr_mat);
	                        next_mat.move("down");
	                        pq.enqueue(next_mat);
	                        count++;
	                    }
	                    else if (curr_mat.step_before().equals("up")) {
	                        Matrix next_mat = new Matrix(curr_mat);
	                        next_mat.move("left");
	                        pq.enqueue(next_mat);
	                        count++;
	                    }
	                    else{
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("down");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("left");
	                        pq.enqueue(next_mat2);
	                        count++;
	                    }
	                }
	                else {
	                    if (curr_mat.step_before().equals("left")) {
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("down");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("left");
	                        pq.enqueue(next_mat2);
	                        count++;
	                    }
	                    else if (curr_mat.step_before().equals("right")) {
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("down");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("right");
	                        pq.enqueue(next_mat2);
	                        count++;
	                    }
	                    else if (curr_mat.step_before().equals("up")) {
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("right");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("left");
	                        pq.enqueue(next_mat2);
	                        count++;
	                    }
	                    else{
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("right");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("down");
	                        pq.enqueue(next_mat2);
	                        count++;
	                        Matrix next_mat3 = new Matrix(curr_mat);
	                        next_mat3.move("left");
	                        pq.enqueue(next_mat3);
	                        count++;
	                    }
	                }
	            }
	            else if (curr_zero.getX() == 3) {
	                if (curr_zero.getY() == 0) {
	                    if (curr_mat.step_before().equals("left")) {
	                        Matrix next_mat = new Matrix(curr_mat);
	                        next_mat.move("up");
	                        pq.enqueue(next_mat);
	                        count++;
	                    }
	                    else if (curr_mat.step_before().equals("down")) {
	                        Matrix next_mat = new Matrix(curr_mat);
	                        next_mat.move("right");
	                        pq.enqueue(next_mat);
	                        count++;
	                    }
	                    else{
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("up");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("right");
	                        pq.enqueue(next_mat2);
	                        count++;
	                    }
	                }
	                else if (curr_zero.getY() == 3) {
	                    if (curr_mat.step_before().equals("right")) {
	                        Matrix next_mat = new Matrix(curr_mat);
	                        next_mat.move("up");
	                        pq.enqueue(next_mat);
	                        count++;
	                    }
	                    else if (curr_mat.step_before().equals("down")) {
	                        Matrix next_mat = new Matrix(curr_mat);
	                        next_mat.move("left");
	                        pq.enqueue(next_mat);
	                        count++;
	                    }
	                    else{
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("up");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("left");
	                        pq.enqueue(next_mat2);
	                        count++;
	                    }
	                }
	                else {
	                    if (curr_mat.step_before().equals("left")) {
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("up");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("left");
	                        pq.enqueue(next_mat2);
	                        count++;
	                    }
	                    else if (curr_mat.step_before().equals("right")) {
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("up");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("right");
	                        pq.enqueue(next_mat2);
	                        count++;
	                    }
	                    else if (curr_mat.step_before().equals("down")) {
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("right");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("left");
	                        pq.enqueue(next_mat2);
	                        count++;
	                    }
	                    else{
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("right");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("up");
	                        pq.enqueue(next_mat2);
	                        count++;
	                        Matrix next_mat3 = new Matrix(curr_mat);
	                        next_mat3.move("left");
	                        pq.enqueue(next_mat3);
	                        count++;
	                    }
	                }
	            }
	            else{
	                //di tengah
	                if (curr_zero.getY() == 0) {
	                    if (curr_mat.step_before().equals("left")) {
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("right");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("down");
	                        pq.enqueue(next_mat2);
	                        count++;
	                    }
	                    else if (curr_mat.step_before().equals("down")) {
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("down");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("right");
	                        pq.enqueue(next_mat2);
	                        count++;
	                    }
	                    else if (curr_mat.step_before().equals("up")) {
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("up");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("right");
	                        pq.enqueue(next_mat2);
	                        count++;
	                    }
	                    else{
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("up");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("right");
	                        pq.enqueue(next_mat2);
	                        count++;
	                        Matrix next_mat3 = new Matrix(curr_mat);
	                        next_mat3.move("down");
	                        pq.enqueue(next_mat3);
	                        count++;
	                    }
	                }
	                else if (curr_zero.getY() == 3) {
	                    if (curr_mat.step_before().equals("right")) {
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("up");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("down");
	                        pq.enqueue(next_mat2);
	                        count++;
	                    }
	                    else if (curr_mat.step_before().equals("up")) {
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("up");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("left");
	                        pq.enqueue(next_mat2);
	                        count++;
	                    }
	                    else if (curr_mat.step_before().equals("down")) {
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("down");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("left");
	                        pq.enqueue(next_mat2);
	                        count++;
	                    }
	                    else{
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("down");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("left");
	                        pq.enqueue(next_mat2);
	                        count++;
	                        Matrix next_mat3 = new Matrix(curr_mat);
	                        next_mat3.move("up");
	                        pq.enqueue(next_mat3);
	                        count++;
	                    }
	                }
	                else {
	                    if (curr_mat.step_before().equals("left")) {
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("left");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("up");
	                        pq.enqueue(next_mat2);
	                        count++;
	                        Matrix next_mat3 = new Matrix(curr_mat);
	                        next_mat3.move("down");
	                        pq.enqueue(next_mat3);
	                        count++;
	                    }
	                    else if (curr_mat.step_before().equals("right")) {
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("right");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("up");
	                        pq.enqueue(next_mat2);
	                        count++;
	                        Matrix next_mat3 = new Matrix(curr_mat);
	                        next_mat3.move("down");
	                        pq.enqueue(next_mat3);
	                        count++;
	                    }
	                    else if (curr_mat.step_before().equals("up")) {
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("up");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("left");
	                        pq.enqueue(next_mat2);
	                        count++;
	                        Matrix next_mat3 = new Matrix(curr_mat);
	                        next_mat3.move("right");
	                        pq.enqueue(next_mat3);
	                        count++;
	                    }
	                    else if (curr_mat.step_before().equals("down")) {
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("down");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("left");
	                        pq.enqueue(next_mat2);
	                        count++;
	                        Matrix next_mat3 = new Matrix(curr_mat);
	                        next_mat3.move("right");
	                        pq.enqueue(next_mat3);
	                        count++;
	                    }
	                    else{
	                        Matrix next_mat1 = new Matrix(curr_mat);
	                        next_mat1.move("up");
	                        pq.enqueue(next_mat1);
	                        count++;
	                        Matrix next_mat2 = new Matrix(curr_mat);
	                        next_mat2.move("left");
	                        pq.enqueue(next_mat2);
	                        count++;
	                        Matrix next_mat3 = new Matrix(curr_mat);
	                        next_mat3.move("right");
	                        pq.enqueue(next_mat3);
	                        count++;
	                        Matrix next_mat4 = new Matrix(curr_mat);
	                        next_mat4.move("down");
	                        pq.enqueue(next_mat4);
	                        count++;
	                    }
	                }
	            }
	            curr_mat = pq.dequeue();
	        }
	        //get curr_mat as result
	        this.mat_end = new Matrix(curr_mat);
		}
	    long end_time = System.nanoTime();
		//in milisecond
		this.time_process = (end_time-start_time) / 1000000;
		this.simpul = count;
	}
}
