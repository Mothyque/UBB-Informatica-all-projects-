#include <iostream>
#include <vector>
#include <fstream>
#include <set>

using namespace std;

vector<int> PuferDecoding(vector<int>& cod);
int main()
{
    ifstream fin("3-in.txt");
    ofstream fout("3-out.txt");
    int n;
    vector<int> cod;
    fin >> n;
    for (int i = 0; i < n; i++)
    {
        int x;
        fin >> x;
        cod.push_back(x);
    }
    vector<int> rezultat = PuferDecoding(cod);
    fout << rezultat.size() << endl;
    for (int i = 0; i < rezultat.size(); i++)
    {
        fout << rezultat[i] << " ";
    }
}

vector<int> PuferDecoding(vector<int>& cod)
{
    int n = cod.size();
    vector<int> arbore(n + 1);
    vector<int> frec(n + 1, 0);
    for(int i = 0; i < n; i++)
    {
        frec[cod[i]]++;
    }
    set<int> frunze;
    for(int i = 0; i <= n; i++)
    {
        if(frec[i] == 0)
        {
            frunze.insert(i);
        }
    }
    for(int i = 0; i < n; i++)
    {
        int frunza = *frunze.begin();
        frunze.erase(frunza);
        arbore[frunza] = cod[i];
        frec[cod[i]]--;
        if(frec[cod[i]] == 0)
        {
            frunze.insert(cod[i]);
        }
    }
    arbore[*frunze.begin()] = -1;
    return arbore;
}