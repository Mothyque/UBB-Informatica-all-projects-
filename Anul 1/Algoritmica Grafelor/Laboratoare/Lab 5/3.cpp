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
        if(a->frecventa == b->frecventa)
        {
            return a->c > b->c; 
        }
        return a->frecventa > b->frecventa;
    }
};

map<char, int> frec(string& text);
void codificare(Nod* radacina, string cod, map<char, string>& codificare);
int main()
{
    ifstream fin("5-in.txt");
    ofstream fout("5-out.txt");
    string text;
    getline(fin, text);
    map<char, int> frecventa = frec(text);
    fout << frecventa.size() << endl;
    for(auto& pair : frecventa)
    {
        fout << pair.first << " " << pair.second << endl;
    }
    priority_queue<Nod*, vector<Nod*>, Comparare> pq;
    for (auto& pair : frecventa)
    {
        pq.push(new Nod(pair.first, pair.second));
    }
    while(pq.size() > 1)
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
    map<char, string> coduri;
    codificare(radacina, "", coduri);
    for(char c: text)
    {
        fout << coduri[c];
    }
    fin.close();
    fout.close();
    return 0;
}

map<char, int> frec(string& text)
{
    map<char, int> frecventa;
    for (char c : text)
    {
        frecventa[c]++;
    }
    return frecventa;
}

void codificare(Nod* radacina, string cod, map<char, string>& coduri)
{
    if (radacina == nullptr)
    {
        return;
    }
    if (!radacina->stanga && !radacina->dreapta )
    {
        coduri[radacina->c] = cod;
        return;
    }
    codificare(radacina->stanga, cod + "0", coduri);
    codificare(radacina->dreapta, cod + "1", coduri);
}