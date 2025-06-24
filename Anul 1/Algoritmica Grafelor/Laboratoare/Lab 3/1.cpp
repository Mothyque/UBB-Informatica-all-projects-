#include <iostream>
#include <fstream>
#include <vector>
#include <limits.h>

using namespace std;

ifstream fin("5-in.txt");
ofstream fout("1.out");

vector<int> dijkstra(vector<vector<pair<int, int>>> adj, int n, int s);
void afisare_dijkstra(vector<int> dist, int n);

int main()
{
    vector<vector<pair<int, int>>> adj;
    int n, m, s;
    int a, b, c;
    fin >> n >> m >> s;
    adj.resize(n);
    while(fin >> a >> b >> c)
    {
        adj[a].push_back({b, c});
    }
    vector<int> dist = dijkstra(adj, n, s);
    afisare_dijkstra(dist, n);
}

vector<int> dijkstra(vector<vector<pair<int, int>>> adj, int n, int s)
{
    vector<int> dist(n, INT_MAX);
    vector<bool> visited(n, false);
    dist[s] = 0;
    for(int i = 0; i < n; i++)
    {
        int u = -1;
        for(int j = 0; j < n; j++)
        {
            if(!visited[j] && (u == -1 || dist[j] < dist[u]))
            {
                u = j;
            }
        }
        if(u == -1 || dist[u] == INT_MAX)
        {
            break;
        }
        visited[u] = true;
        for(int i = 0; i < adj[u].size(); i++)
        {
            int v = adj[u][i].first;
            int w = adj[u][i].second;
            if(dist[u] + w < dist[v])
            {
                dist[v] = dist[u] + w;
            }
        }
    }
    return dist;
}

void afisare_dijkstra(vector<int> dist, int n)
{
    for(int i = 0; i < n; i++)
    {
        if(dist[i] == INT_MAX)
        {
            fout << "INF ";
        }
        else
        {
            fout << dist[i] << " ";
        }
    }
    cout << endl;
}