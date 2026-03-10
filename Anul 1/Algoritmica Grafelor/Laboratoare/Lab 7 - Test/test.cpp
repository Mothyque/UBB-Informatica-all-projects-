#include <iostream>
#include <vector>
#include <fstream>
#include <algorithm>

using namespace std;

ifstream fin("1.in");
ofstream fout("1.out");

#define edge pair<int, int>



int main()
{
    int n, m;
    fin >> n >> m;
    vector<pair<int, edge>> graph;
    for (int i = 0; i < m; i++)
    {
        int u, v, w;
        fin >> u >> v >> w;
        graph.push_back(make_pair(w, edge(u, v)));
    }
    vector<int> parent(n, -1);
    vector<int> key(n,INT_MAX);
    vector<bool> visited(n, false);
    key[0] = 0;
    parent[0] = -1;
    int totalWeight = 0;
    for(int i = 0; i < n; i++)
    {
        int minKey = INT_MAX, minIndex = -1;
        for(int j = 0; j < n; j++)
        {
            if (!visited[j] && key[j] < minKey)
            {
                minKey = key[j];
                minIndex = j;
            }
        }
        visited[minIndex] = true;
        for(const auto& muchie : graph)
        {
            int u = muchie.second.first;
            int v = muchie.second.second;
            int w = muchie.first;
            if (u == minIndex && !visited[v] && w < key[v])
            {
                key[v] = w;
                parent[v] = minIndex;
            }
            else if (v == minIndex && !visited[u] && w < key[u])
            {
                key[u] = w;
                parent[u] = minIndex;
            }
        }
    }
    for(int i = 0; i < n; i++)
    {
        totalWeight += key[i];
    }
    fout << totalWeight << endl;
    fout << n - 1 << endl;
    for(int i = 1; i < n; i++)
    {
        fout << parent[i] << " " << i << endl;
    }
}