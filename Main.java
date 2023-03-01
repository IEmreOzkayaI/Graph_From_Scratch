package Graph;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		FileManager fileManager = new FileManager();
		Scanner scan = new Scanner(System.in);
		fileManager.readFile("graph.txt");
		List<String> edges = fileManager.getFile();
		Graph<String> graph = new Graph<String>(fileManager.getVertexAmount());
		for (int i = 0; i < fileManager.getFileSize(); i+=3) {
				graph.addEdge(edges.get(i), edges.get(i+1), edges.get(i+2));
		}
		System.out.print("Please Enter Source : ");
		String source = scan.nextLine();
		System.out.print("Please Enter Destination : ");
		String destination = scan.nextLine();
		int task1 = graph.getBiggestCapacity(source.toUpperCase(Locale.ENGLISH),destination.toUpperCase(Locale.ENGLISH));
		if(task1 == 0){
            System.out.println("\nThese terminals dont exist !!!!!!!");
		}else{
			System.out.println("\nTASK 1");
			System.out.println("Max load in" +  " " + destination.toUpperCase(Locale.ENGLISH)+" " +  + task1 );
			System.out.println("\nTASK 2 - TASK 3");
			graph.findIncreasebleVertex(destination.toUpperCase(Locale.ENGLISH));
		}

		scan.close();
	}

}
