#include <iostream>
#include <vector>
#include <fstream>
#include <queue>

using namespace std;

ifstream fin("graf.in");

vector<int> BFS_dist(vector<vector<int>>& adj, int s);
vector<int> BFS(vector<vector<int>>& adj, int s);

int main()
{   
    int nod_sursa, a, b, n;
    cin >> nod_sursa;
    fin >> n;
    vector<vector<int>> adj(n + 1);
    while(fin >> a >> b)
    {
        adj[a].push_back(b);
        adj[b].push_back(a);
    }

    vector<int> ordine = BFS(adj, nod_sursa);
    for(int i = 0; i < ordine.size(); i++)
    {
        cout << ordine[i] << " ";
    }
    cout << endl;
    vector<int> dist = BFS_dist(adj, nod_sursa);
    for(int i = 1; i <= n; i++)
    {
        cout << "Distanta de la nodul " << nod_sursa << " la nodul " << i << " este: " << dist[i] << endl;
    }
}

vector<int> BFS_dist(vector<vector<int>>& adj, int s)
{
    vector<int> dist(adj.size(), -1);
    queue<int> q;

    dist[s] = 0;
    q.push(s);

    while(!q.empty())
    {
        int nod = q.front();
        q.pop();
        for(int vecin: adj[nod])
        {
            if(dist[vecin] == -1)
            {
                dist[vecin] = dist[nod] + 1;
                q.push(vecin);
            }
        }
    }
    return dist;
}

vector<int> BFS(vector<vector<int>>& adj, int s)
{
    vector<int> rez;
    queue<int> q;
    vector<bool> vizitat (adj.size(), false);

    vizitat[s] = true;
    q.push(s);

    while(!q.empty())
    {
        int nod = q.front();
        q.pop();
        rez.push_back(nod);
        for(int vecin: adj[nod])
        {
            if(!vizitat[vecin])
            {
                vizitat[vecin] = true;
                q.push(vecin);
            }
        }
    }
    return rez;
}