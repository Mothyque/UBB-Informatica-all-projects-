#include <algorithm>
#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

#define edge pair<int, int>

ifstream fin("8-in.txt");
ofstream fout("8-out.txt");

class Graph{
    vector<pair<int, edge>> G;
    vector<pair<int, edge>> MST;
    int* parent;
    int wt;
    int V;
public:
    Graph(int V);
    ~Graph();
    void addEdge(int u, int v, int w);
    int findParent(int i);
    void unionSet(int u, int v);
    void kruskal();
    void printMST();
};

Graph::Graph(int V) {
    this->V = V;
    parent = new int[V + 1];
    for (int i = 0; i < V + 1; i++)
        parent[i] = i;
    G.clear();
    MST.clear();
}

void Graph::addEdge(int u, int v, int w) {
    G.push_back(make_pair(w, edge(u, v)));
}

int Graph::findParent(int i) {
    while (parent[i] != i)  
        i = parent[i];
    return i;
}

void Graph::unionSet(int u, int v) {
    parent[u] = v;
}

void Graph::kruskal(){
    int mst_wt = 0;
    sort(G.begin(), G.end());
    for(int i = 0; i < G.size(); i++){
        int u = findParent(G[i].second.first);
        int v = findParent(G[i].second.second);
        if(u != v){
            mst_wt += G[i].first;
            MST.push_back(G[i]);
            unionSet(u, v);
        }
    }
    wt = mst_wt;
}

void Graph::printMST() {
    fout << wt << endl;
    fout << MST.size() << endl;
    sort(MST.begin(), MST.end(), [](pair<int, edge> a, pair<int, edge> b) {
        if (a.second.first == b.second.first) {
            return a.second.second < b.second.second;
        }
        return a.second.first < b.second.first;
    });
    for (int i = 0; i < MST.size(); i++) {
        fout << MST[i].second.first << " " << MST[i].second.second << endl;
    }
}

Graph::~Graph() {
    delete[] parent;
}

int main() {
    int V, E;
    fin >> V >> E;
    Graph g(V);
    for (int i = 0; i < E; i++) {
        int u, v, w;
        fin >> u >> v >> w;
        g.addEdge(u, v, w);
    }
    g.kruskal();
    g.printMST();
    return 0;
}