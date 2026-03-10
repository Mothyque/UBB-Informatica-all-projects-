#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;

bool verifica_euler(vector<int>& grad);
void gaseste_euler(int n, vector<vector<int>>& adj, vector<int>& path);

int main()
{
    ifstream fin("2-in.txt");
    ofstream fout("2-out.txt");
    int n, m;
    fin >> n >> m;
    vector<vector<int>> adj(n + 1);
    vector<int> grad(n + 1, 0);
    for (int i = 0; i < m; ++i)
    {
        int u, v;
        fin >> u >> v;
        adj[u].push_back(v);
        grad[v]++;
        adj[v].push_back(u);
        grad[u]++;
    }
    if(!verifica_euler(grad))
    {
        fout << "Graful nu este eulerian" << endl;
    }
    else
    {
        vector<int> path;
        gaseste_euler(0, adj, path);
        for (int i = 0; i < path.size(); ++i)
        {
            fout << path[i] << " ";
        }
        fout << endl;
    }
}
bool verifica_euler(vector<int>& grad)
{
    for(auto g : grad)
    {
        if (g % 2 != 0)
            return false;
    }
    return true;
}

void gaseste_euler(int curent, vector<vector<int>>& adj, vector<int>& path)
{
    while(!adj[curent].empty())
    {
        int vecin = adj[curent].back();
        adj[curent].pop_back();
        auto it = find(adj[vecin].begin(), adj[vecin].end(), curent);
        if (it != adj[vecin].end())
        {
            adj[vecin].erase(it);
        }
        gaseste_euler(vecin, adj, path);
    }
    path.push_back(curent);
}
