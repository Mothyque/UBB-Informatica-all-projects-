#include <iostream>
#include <fstream>
#include <vector>
#include <limits.h>

using namespace std;

ifstream fin("2.in");
ofstream fout("2.out");

void johnson(vector<vector<int>>& adj, int n);
vector<int> bellman_ford(vector<vector<int>> adj, int n, int s);
vector<int> dijkstra(vector<vector<int>> adj, int n, int s);
void afisare(vector<vector<int>> adj);
void afisare_dijkstra(vector<vector<int>> adj, int n);
void afisare_bellmanford(vector<vector<int>> adj, int n);

int main()
{
    int n, m;
    fin >> n >> m;
    vector<vector<int>> adj(n, vector<int>(n, INT_MAX)); 
    vector<vector<int>> adj_aux(n, vector<int>(n, INT_MAX));
    for(int i = 0; i < m; i++)
    {
        int a, b, c;
        fin >> a >> b >> c;
        adj[a][b] = c;
        adj_aux[a][b] = c;
    }
    johnson(adj, n);
    afisare(adj);
    afisare_dijkstra(adj, n);
    afisare_bellmanford(adj_aux, n);
    return 0;
}

void johnson(vector<vector<int>>& adj, int n)
{
    vector<vector<int>> adj_aux(n + 1, vector<int>(n + 1, INT_MAX));
    for(int i = 0; i < n; i++)
    {
        for(int j = 0; j < n; j++)
        {
            adj_aux[i][j] = adj[i][j];
        }
    }
    for(int i = 0; i < n; i++)
    {
        adj_aux[n][i]= 0;
    }
    vector<int> h = bellman_ford(adj_aux, n + 1, n);
    if(h.empty())
    {
        cout << "Ciclu negativ\n";
        return;
    }
    for(int i = 0; i < n; i++)
    {
        for(int j = 0; j < n; j++)
        {
            if(adj[i][j] != INT_MAX)
            {
                adj[i][j] = adj[i][j] + h[i] - h[j];
            }
        }
    }
    
}

vector<int> bellman_ford(vector<vector<int>> adj, int n, int s)
{
    vector<int> d(n, INT_MAX);
    d[s] = 0;
    for(int i = 0; i < n - 1; i++)
    {
        for(int u = 0; u < n; u++)
        {
            for(int v = 0; v < n; v++)
            {
                if(adj[u][v] != INT_MAX)
                {
                    if (adj[u][v] != INT_MAX && d[u] != INT_MAX && d[u] + adj[u][v] < d[v])
                        {
                            d[v] = d[u] + adj[u][v];
                        }
                }
            }
        }
    }
    for(int i = 0; i < n; i++)
    {
        for(int j = 0; j < n; j++)
        {
                if (adj[i][j] != INT_MAX && d[i] != INT_MAX && d[i] + adj[i][j] < d[j])
                    {
                        return {};
                    }
        }
    }
    return d;
}

void afisare(vector<vector<int>> adj)
{
    fout << "Muchiile reponderate sunt:\n";
    for(int i = 0; i < adj.size(); i++)
    {
        for(int j = 0; j < adj[i].size(); j++)
        {
            if(adj[i][j] != INT_MAX)
            {
                fout << i << " " << j << " " << adj[i][j] << "\n";
            }
        }
    }
    fout << "\n";
}

vector<int> dijkstra(vector<vector<int>> adj, int n, int s)
{
    vector<int> d(n, INT_MAX);
    vector<bool> viz(n, false);
    d[s] = 0;
    for(int i = 0; i < n; i++)
    {
        int u = -1;
        for(int j = 0; j < n; j++)
        {
            if(!viz[j] && (u == -1 || d[j] < d[u]))
            {
                u = j;
            }
        }
        if(u == -1 || d[u] == INT_MAX)
        {
            break;
        }
        viz[u] = true;
        for(int v = 0; v < n; v++)
        {
            if(adj[u][v] != INT_MAX)
            {
                d[v] = min(d[v], d[u] + adj[u][v]);
            }
        }
    }
    return d;
}

void afisare_dijkstra(vector<vector<int>> adj, int n)
{
    fout << "Matricea drumurilor minime cu ponderi pozitive este:\n";
    for(int i = 0; i < n; i++)
    {
        vector<int> d = dijkstra(adj, n, i);
        for(int j = 0; j < n; j++)
        {
            if(d[j] == INT_MAX)
            {
                fout << "INF ";
            }
            else
            {
                fout << d[j] << " ";
            }
        }
        fout << "\n";
    }
    fout << "\n";
}

void afisare_bellmanford(vector<vector<int>> adj, int n)
{
    fout << "Distantele minime de la nodul sursa la celelalte noduri sunt:\n";
    for(int i = 0; i < n; i++)
    {
        vector<int> dist = bellman_ford(adj, n, i);
        for(int j = 0; j < n; j++)
        {
            if(dist[j] == INT_MAX)
            {
                fout << "INF ";
            }
            else
            {
                fout << dist[j] << " ";
            }
        }
        fout << "\n";
    }
}
