#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

using namespace std;

ifstream fin("6-in.txt");
ofstream fout("6-out.txt");

struct Edge
{
    int from, to;
    int capacity;
    int flow;
    Edge(int f, int t, int c) : from(f), to(t), capacity(c), flow(0) {}
};

class PushRelabel
{
private:
    int n;
    vector<vector<int>> graph;
    vector<int> excess;
    vector<int> height;
    vector<int> seen;
    vector<Edge> edges;

public:
    PushRelabel(int n)
    {
        this->n = n;
        graph.resize(n);
        excess.resize(n, 0);
        height.resize(n, 0);
        seen.resize(n, 0);
    }

    void addEdge(int from, int to, int capacity)
    {
        graph[from].push_back(edges.size());
        edges.push_back(Edge(from, to, capacity));
        graph[to].push_back(edges.size());
        edges.push_back(Edge(to, from, 0));
    }

    void push(int u, int i, queue<int>& q, int s, int t)
    {
        if (excess[u] == 0)
            return;
        Edge& e = edges[i];
        int v = e.to;
        int send = min(excess[u], e.capacity - e.flow);
        if (height[u] <= height[v] || send == 0)
            return;
        e.flow += send;
        edges[i ^ 1].flow -= send;
        excess[u] -= send;
        excess[v] += send;

        if (v != s && v != t && excess[v] == send)
        {
            q.push(v);
        }
    }

    void relabel(int u)
    {
        int minHeight = INT_MAX;
        for (int i : graph[u])
        {
            Edge& e = edges[i];
            if (e.capacity - e.flow > 0)
                minHeight = min(minHeight, height[e.to]);
        }
        height[u] = minHeight + 1;
    }

    void discharge(int u, queue<int>& q, int s, int t)
    {
        while (excess[u] > 0)
        {
            if(seen[u] == graph[u].size())
            {
                relabel(u);
                seen[u] = 0;
            }
            else
            {
                push(u, graph[u][seen[u]], q, s, t);
                seen[u]++;
            }
        }
    }

    int getMaxFlow(int s, int t)
    {
        height[s] = n;
        excess[s] = INT_MAX;

        queue<int> q;
        for(int i : graph[s])
        {
            int cap = edges[i].capacity;
            edges[i].flow = cap;
            edges[i ^ 1].flow = -cap;
            excess[s] -= cap;
            excess[edges[i].to] += cap;

            if(edges[i].to != s && edges[i].to != t)
            {
                q.push(edges[i].to);
            }
        }
    
        while(!q.empty())
        {
            int u = q.front();
            q.pop();
            discharge(u, q, s, t);
        }
        int maxFlow = 0;
        for(int i : graph[s])
        {
            maxFlow += edges[i].flow;
        }
        return maxFlow;
    }
};

int main()
{
    int n, m;
    fin >> n >> m;
    PushRelabel pr(n);
    int x, y, z;
    for(int i = 0; i < m; i++)
    {
        fin >> x >> y >> z;
        pr.addEdge(x, y, z);
    }
    fout << pr.getMaxFlow(0, n - 1);
    return 0;
}