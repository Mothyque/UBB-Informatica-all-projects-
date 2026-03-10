#include <iostream>
#include <vector>
#include <fstream>
#include <queue>

using namespace std;

ifstream fin("graf.in");

void DFS_Rec(vector<vector<int>>& adj, vector<bool> &vizitat, int s)
{
    vizitat[s] = true;
    cout << s << " ";
    for(int vecin: adj[s])
    {
        if(!vizitat[vecin])
        {
            DFS_Rec(adj, vizitat, vecin);
        }
    }
}
void DFS(vector<vector<int>>& adj, int s)
{
    vector<bool> vizitat(adj.size(), false);
    DFS_Rec(adj, vizitat, s);
}
int main()
{
    int n, a, b, nod_sursa;
    fin >> n;
    cin >> nod_sursa;
    vector<vector<int>> adj(n + 1);
    while(fin >> a >> b)
    {
        adj[a].push_back(b);
        adj[b].push_back(a);
    }
    DFS(adj, nod_sursa);
}

