#include <iostream>
#include <fstream>
#include <map>
#include <vector>
#include <queue>
#include <string>

using namespace std;

struct Nod
{
    char c;
    int frecventa;
    Nod* stanga;
    Nod* dreapta;

    Nod(char c, int frecventa) : c(c), frecventa(frecventa), stanga(nullptr), dreapta(nullptr) {}
};

struct Comparare
{
    bool operator()(Nod* a, Nod* b)
    {
        if (a->frecventa == b->frecventa)
        {
            return a->c > b->c; 
        }
        return a->frecventa > b->frecventa;
    }
};
int main()
{
    ifstream fin("1-in.txt");   
    ofstream fout("1-out.txt");
    int n;
    map<char, int> frecventa;
    fin >> n;
    fin.ignore();
    for(int i = 0; i < n; i++)
    {
        char c;
        int f;
        fin.get(c);
        fin >> f;
        fin.ignore();
        frecventa[c] = f;
    }
    string text;
    getline(fin, text);
    priority_queue<Nod*, vector<Nod*>, Comparare> pq;
    for (auto& pair : frecventa)
    {
        pq.push(new Nod(pair.first, pair.second));
    }
    while (pq.size() > 1)
    {
        Nod* stanga = pq.top();
        pq.pop();
        Nod* dreapta = pq.top();
        pq.pop();
        char litera_mica = min(stanga->c, dreapta->c);
        Nod* parinte = new Nod(litera_mica, stanga->frecventa + dreapta->frecventa);
        parinte->stanga = stanga;
        parinte->dreapta = dreapta;
        pq.push(parinte);
    }
    Nod* radacina = pq.top();
    pq.pop();
    Nod* curent = radacina;
    for(char c : text)
    {
        if(c == '0')
        {
            curent = curent->stanga;
        }
        else
        {
            curent = curent->dreapta;
        }
        if(curent->stanga == nullptr && curent->dreapta == nullptr)
        {
            fout << curent->c;
            curent = radacina;
        }
    }
    fin.close();
    fout.close();
    return 0;
}