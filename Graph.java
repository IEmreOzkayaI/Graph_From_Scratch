package Graph;

import java.util.ArrayList;
import java.util.Stack;

public class Graph<T> implements GraphInterface<T> {

    private int[][] adjacencyMatrice;
    private ArrayList<T> allVertices;

    ArrayList<Vertex<T>> bfsQeueue = new ArrayList<Vertex<T>>();
    ArrayList<Vertex<T>> vertices = new ArrayList<Vertex<T>>();
    ArrayList<T> visitedQueue = new ArrayList<T>();
    ArrayList<Vertex<T>> remainingLoadqueue;

    public Graph(int size) {
        allVertices = new ArrayList<T>();
        remainingLoadqueue = new ArrayList<Vertex<T>>();
        adjacencyMatrice = new int[size][size];
    }

    public void addEdge(T begin, T end, String edgeWeight) {
        if (!allVertices.contains(begin))
            allVertices.add(begin);
        if (!allVertices.contains(end))
            allVertices.add(end);

        int source_index = allVertices.indexOf(begin);
        int destination_index = allVertices.indexOf(end);
        adjacencyMatrice[source_index][destination_index] = Integer.parseInt(edgeWeight);
    }

    public int getBiggestCapacity(T begin, T end) {
        if(allVertices.indexOf(begin) == -1 || allVertices.indexOf(end) == -1){
            return 0;
        }
        distribuiteFromSource(begin);
        // for bfs qeueu
        while (!bfsQeueue.isEmpty()) {
            int removed_location_vert = -1;
            int removed_location_bfs = -1;
            Vertex<T> frontVertex = bfsQeueue.remove(0);

            if (bfsQeueue.isEmpty()) {
                for (int index = 0; index < vertices.size(); index++) {
                    if (vertices.get(index).getLabel().equals(end)) {
                        return vertices.get(index).getLoad();
                    }
                }
            }

            frontVertex.setVisited(true);
            int source_index = allVertices.indexOf(frontVertex.getLabel());
            ArrayList<Vertex<T>> mostCapacitiveNeighbor = findMostCapacitiveVertex(source_index, end);
            int i = 0;
            while ((frontVertex.getRemainedPower() > 0)
                    && (frontVertex.getDistribuited() != frontVertex.getCapacity())
                    && !frontVertex.getLabel().equals(end) && i <= mostCapacitiveNeighbor.size() - 1) {

                Vertex<T> vertex = mostCapacitiveNeighbor.get(i);
                Vertex<T> isExist = IsExist(vertex);
                if (isExist != null) {
                    for (int index = 0; index < bfsQeueue.size(); index++) {
                        if (bfsQeueue.get(index).getLabel().equals(vertex.getLabel())) {
                            removed_location_bfs = index;
                            bfsQeueue.remove(removed_location_bfs);
                        }
                    }
                    for (int index = 0; index < vertices.size(); index++) {
                        if (vertices.get(index).getLabel().equals(vertex.getLabel())) {
                            removed_location_vert = index;
                            vertices.remove(removed_location_vert);
                        }
                    }
                    vertex.setLoad(isExist.getLoad());
                    vertex.setRemainedPower(isExist.getRemainedPower());
                }
                int index = allVertices.indexOf(vertex.getLabel());
                int edgeWeight = adjacencyMatrice[source_index][index];
                if (edgeWeight >= frontVertex.getRemainedPower()) {
                    vertex.setLoad(frontVertex.getRemainedPower() + vertex.getLoad());
                    vertex.setRemainedPower(vertex.getLoad());
                    frontVertex.setRemainedPower(frontVertex.getRemainedPower() - frontVertex.getRemainedPower());
                    frontVertex.setDistribuited(frontVertex.getDistribuited() + frontVertex.getRemainedPower());

                } else {
                    vertex.setLoad(edgeWeight + vertex.getLoad());
                    vertex.setRemainedPower(vertex.getLoad());
                    frontVertex.setRemainedPower(frontVertex.getRemainedPower() - edgeWeight);
                    frontVertex.setDistribuited(frontVertex.getDistribuited() + edgeWeight);
                }
                if (removed_location_vert != -1) {
                    vertices.add(removed_location_vert, vertex);
                    bfsQeueue.add(removed_location_bfs, vertex);
                    removed_location_vert = -1;
                    removed_location_bfs = -1;
                } else {
                    vertices.add(vertex);
                    bfsQeueue.add(vertex);
                }
                i++;
            }
            if (!frontVertex.getLabel().equals(end)) {

                visitedQueue.add(frontVertex.getLabel());

                if (frontVertex.getRemainedPower() > 0) {
                    remainingLoadqueue.add(frontVertex);
                }
            }
        }

        return 0;

    }

    private Vertex<T> IsExist(Vertex<T> vertex) {
        for (int index = 0; index < bfsQeueue.size(); index++) {
            if (bfsQeueue.get(index).getLabel().equals(vertex.getLabel())) {
                return bfsQeueue.get(index);
            }
        }
        return null;
    }

    private ArrayList<Vertex<T>> findMostCapacitiveVertex(int source_index, T end) {
        ArrayList<Vertex<T>> biggest = new ArrayList<>();
        for (int neighborTraveller = 0; neighborTraveller < adjacencyMatrice[1].length; neighborTraveller++) {
            if (adjacencyMatrice[source_index][neighborTraveller] != 0) {
                T nameOfVertex = allVertices.get(neighborTraveller);
                if (!visitedQueue.contains((allVertices.get(neighborTraveller)))) {
                    Vertex<T> vertex = new Vertex<T>(nameOfVertex);
                    int total = 0;
                    if (vertex.getLabel().equals(end)) {
                        total = Integer.MAX_VALUE;
                    } else {
                        for (int neighborTraveller2 = 0; neighborTraveller2 < adjacencyMatrice[1].length; neighborTraveller2++) {
                            if (visitedQueue.contains((allVertices.get(neighborTraveller2)))) {
                                adjacencyMatrice[neighborTraveller][neighborTraveller2] = 0;
                            } else {
                                total = total + adjacencyMatrice[neighborTraveller][neighborTraveller2];
                            }
                        }
                    }
                    vertex.setCapacity(total);
                    biggest.add(vertex);
                }

            }
        }
        biggest = sort(biggest);
        return biggest;
    }

    public ArrayList<Vertex<T>> sort(ArrayList<Vertex<T>> arr) {
        int n = arr.size();
        for (int i = 1; i < n; ++i) {
            Vertex<T> key = arr.get(i);
            int j = i - 1;

            while (j >= 0 && arr.get(j).getCapacity() < key.getCapacity()) {
                arr.set(j + 1, arr.get(j));
                j = j - 1;
            }
            arr.set(j + 1, key);
        }
        return arr;
    }

    private void distribuiteFromSource(T begin) {
        int source_index = allVertices.indexOf(begin);

        visitedQueue.add(begin);
        for (int neighborTraveller = 0; neighborTraveller < adjacencyMatrice[1].length; neighborTraveller++) {
            int edgeWeight = adjacencyMatrice[source_index][neighborTraveller];
            if (edgeWeight != 0) {
                T nameOfVertex = allVertices.get(neighborTraveller);
                Vertex<T> neighborVertex = new Vertex<T>(nameOfVertex);
                neighborVertex.setLoad(edgeWeight + neighborVertex.getLoad());
                neighborVertex.setRemainedPower(neighborVertex.getLoad());
                int total = 0;
                for (int neighborTraveller2 = 0; neighborTraveller2 < adjacencyMatrice[1].length; neighborTraveller2++) {
                    total = total + adjacencyMatrice[neighborTraveller][neighborTraveller2];
                }
                neighborVertex.setCapacity(total);
                bfsQeueue.add(neighborVertex);
                vertices.add(neighborVertex);
            }
        }
    }

    public void findIncreasebleVertex(T end) {
        if (remainingLoadqueue.size() != 0) {
            for (int index = 0; index < remainingLoadqueue.size(); index++) {
                findShortestPath(remainingLoadqueue.get(index).getLabel(), end);
            }
        } else {
            System.out.println("No Need To This");
        }

    }

    private void findShortestPath(T begin, T end) {
        Stack<T> stack = new Stack<>();

        visitedQueue = null;
        for (int index = 0; index < vertices.size(); index++) {
            vertices.get(index).setVisited(false);
            vertices.get(index).setPath(0);
            vertices.get(index).setPredecessor(null);
        }
        if (allVertices.indexOf(begin) - 1 >= 0 && allVertices.indexOf(begin) - 1 <= vertices.size()) {
            vertices.get(allVertices.indexOf(begin) - 1).setVisited(true);
            boolean done = false;
            ArrayList<T> vertexList = new ArrayList<>();
            vertexList.add(begin);
            while (!done && !vertexList.isEmpty()) {
                T frontVertex = vertexList.remove(0);
                int source = allVertices.indexOf(frontVertex);
                for (int index = 0; index < adjacencyMatrice[1].length; index++) {
                    if (adjacencyMatrice[source][index] != 0 && !done) {
                        T nextVertex = allVertices.get(index);
                        if (allVertices.indexOf(nextVertex) - 1 >= 0 && vertices.size() >= allVertices.indexOf(nextVertex)
                                && !vertices.get(allVertices.indexOf(nextVertex) - 1).isVisited()) {
                            vertices.get(allVertices.indexOf(nextVertex) - 1).setVisited(true);
                            vertices.get(allVertices.indexOf(nextVertex) - 1)
                                    .setPath(vertices.get(allVertices.indexOf(frontVertex) - 1).getPath() + 1);
                            vertices.get(allVertices.indexOf(nextVertex) - 1).setPredecessor(frontVertex);
                            vertexList.add(nextVertex);
                        }
                        if (nextVertex.equals(end)) {
                            done = true;
                        }

                    }
                }
            }
            if (vertexList.size() != 0) {
                T vertex = vertexList.get(vertexList.size() - 1);
                stack.push(vertex);
                while (vertices.get(allVertices.indexOf(vertex) - 1).getPredecessor() != null) {
                    vertex = vertices.get(allVertices.indexOf(vertex) - 1).getPredecessor();
                    stack.push(vertex);
                }

                findMaximizeAmount(stack);
                System.out.println();
            }else{
                if(stack.size() ==0){
                    System.out.println("There is no alternative");
                }
            }
        }

    }

    public void findMaximizeAmount(Stack<T> stack) {
        for (int index = 0; index < stack.size(); index++) {
            T first = stack.pop();
            T second = stack.pop();

            int newedge = adjacencyMatrice[allVertices.indexOf(first)][allVertices.indexOf(second)];
            newedge += vertices.get(allVertices.indexOf(first) - 1).getRemainedPower();
            if (vertices.get(allVertices.indexOf(second) - 1).getRemainedPower() == 0) {
                vertices.get(allVertices.indexOf(second) - 1)
                        .setRemainedPower(vertices.get(allVertices.indexOf(first) - 1).getRemainedPower()
                                + vertices.get(allVertices.indexOf(second) - 1).getRemainedPower());
                System.out.print(first + " -" + newedge + "-> " + second + "   ");
            } else {
                if (vertices.get(allVertices.indexOf(first) - 1).getRemainedPower() + vertices
                        .get(allVertices.indexOf(first) - 1)
                        .getLoad() <= adjacencyMatrice[allVertices.indexOf(first)][allVertices.indexOf(second)]) {
                    newedge = adjacencyMatrice[allVertices.indexOf(first)][allVertices.indexOf(second)];
                } else {
                    System.out.print(first + " -" + newedge + "-> " + second + "   ");

                }
            }

            stack.push(second);
        }

    }

}
