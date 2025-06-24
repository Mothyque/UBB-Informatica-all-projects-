#include <iostream>
#include <fstream>
#include <queue>
#include <vector>
#include <limits.h>

using namespace std;

ifstream fin("9-in.txt");
ofstream fout("9-out.txt");

bool bfs(vector<vector<int>>& graph, int s, int t, vector<int>& parent);
int fordFulkerson(vector<vector<int>>& graph, int s, int t);

int main()
{
    int n, m;
    fin >> n >> m;
    vector<vector<int>> a(n, vector<int>(n, 0));
    int x, y, z;
    while (fin >> x >> y >> z)
    {
        a[x][y] = z;
    }
    fout << fordFulkerson(a, 0, n - 1);
    return 0;
}

bool bfs(vector<vector<int>>& graph, int s, int t, vector<int>& parent)
{
    int V = graph.size();
    vector<bool> visited(V, false);
    queue<int> q;
    q.push(s);
    visited[s] = true;
    parent[s] = -1;
    while (!q.empty())
    {
        int u = q.front();
        q.pop();
        for (int v = 0; v < V; v++)
        {
            if (!visited[v] && graph[u][v] > 0)
            {
                parent[v] = u;
                if (v == t)
                    return true;
                q.push(v);
                visited[v] = true;
            }
        }
    }
    return false;
}

int fordFulkerson(vector<vector<int>>& graph, int s, int t)
{
    int V = graph.size();
    vector<vector<int>> rGraph = graph;
    int flow = 0;
    vector<int> parent(V);
    while (bfs(rGraph, s, t, parent))
    {
        int path_flow = INT_MAX;
        for (int v = t; v != s; v = parent[v])
        {
            int u = parent[v];
            path_flow = min(path_flow, rGraph[u][v]);
        }
        for (int v = t; v != s; v = parent[v])
        {
            int u = parent[v];
            rGraph[u][v] -= path_flow;
            rGraph[v][u] += path_flow;
        }
        flow += path_flow;
    }
    return flow;
}
