#include <iostream>
#include <vector>
#include <fstream>
#include <set>

using namespace std;    

vector<int> PruferEncoding(vector<int>& parinte);

int main()
{
    ifstream fin("3-in.txt");
    ofstream fout("3-out.txt");
    int n;
    fin >> n;
    vector <int> parinti(n);
    for (int i = 0; i < n; i++)
        fin >> parinti[i];
    vector<int> cod = PruferEncoding(parinti);
    fout << cod.size() << endl;
    for (int i = 0; i < cod.size(); i++)
        fout << cod[i] << " ";
    fin.close();
    fout.close();
    return 0;
}

vector<int> PruferEncoding(vector<int>& parinte)
{
    int n = parinte.size();
    vector<int> grad(n, 0);
    vector<vector<int>> adj(n);
    for(int i = 0; i < n; i++)
    {
        if(parinte[i] == -1)
            continue;
        adj[i].push_back(parinte[i]);
        adj[parinte[i]].push_back(i);
        grad[i]++;
        grad[parinte[i]]++;
    }
    set<int> frunze;
    for(int i = 0; i < n; i++)
    {
        if(grad[i] == 1)
        {
            frunze.insert(i);
        }
    }
    vector<int> cod;
    for(int i = 0; i < n - 1; i++)
    {
        int frunza = *frunze.begin();
        frunze.erase(frunza);
        cout << endl;
        int parinte_curent = parinte[frunza];
        cod.push_back(parinte_curent);
        
        grad[frunza]--;
        grad[parinte_curent]--;
        if(grad[parinte_curent] == 1 && parinte[parinte_curent] != -1)
            frunze.insert(parinte_curent);
    }
    return cod;
}
