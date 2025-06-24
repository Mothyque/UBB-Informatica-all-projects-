#include <iostream>
#include <vector>
#include <fstream>
#include <limits.h>

using namespace std;

ifstream fin("test.in");

void dijkstra(vector<vector<int>>& adj, int s, int f);
int main()
{
    int n, x, y, i, j, k;
    fin >> n;
    vector<vector<int>> adj(n, vector<int>(n, 0));
    while(fin >> i >> j >> k)
    {
        adj[i][j] = k;
    }
    cin >> x >> y;
    dijkstra(adj, x, y);
}

void dijkstra(vector<vector<int>>& adj, int s, int f)
{
    vector<int> dist(adj.size(), INT_MAX);
    vector<bool> visited(adj.size(), false);
    vector<int> parinte(adj.size(), -1);
    dist[s] = 0;

    for (int i = 0; i < adj.size(); i++)
    {
        int min_dist = INT_MAX, u = -1;
        for (int j = 0; j < adj.size(); j++)
        {
            if (!visited[j] && dist[j] < min_dist)
            {
                min_dist = dist[j];
                u = j;
            }
        }

        if (u == -1) break; 
        visited[u] = true;

        for (int v = 0; v < adj.size(); v++)
        {
            if (!visited[v] && adj[u][v] != 0 && dist[u] + adj[u][v] < dist[v])
            {
                dist[v] = dist[u] + adj[u][v];
                parinte[v] = u;
            }
        }
    }

    if (dist[f] == INT_MAX)
    {
        cout << "Nu exista drum de la " << s << " la " << f << endl;
        return;
    }

    vector<int> path;
    for (int i = f; i != -1; i = parinte[i])
    {
        path.push_back(i);
    }
    for(int i = path.size() - 1; i >= 0; i--)
    {
        cout << path[i] << " ";
    }
}