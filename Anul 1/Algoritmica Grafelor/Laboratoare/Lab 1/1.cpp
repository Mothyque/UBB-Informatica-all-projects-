#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

ifstream fin("in.txt");

void TransformaMatrice_ListaAdiacenta(bool g[][101], int n, vector<vector<int>>& lista_adiacenta);
void TransformaLista_MatriceIncidenta(vector<vector<int>>& lista_adiacenta, bool incidenta[][101], int n, int m);
void TransformaMatriceIncidenta_MatriceAdiacenta(bool incidenta[][101], bool g[][101], int n, int m);

void Afiseaza_MatriceaAdiacenta(bool g[][101], int n);
void Afiseaza_ListaAdiacenta(vector<vector<int>>& lista_adiacenta, int n);
void Afiseaza_MatriceIncidenta(bool incidenta[][101], int n, int m);

int main()
{
    int a, b, n, m = 0;
    bool g[101][101], incidenta[101][101];
    vector<vector<int>> lista_adiacenta(101);
    fin >> n;
    while(fin >> a >> b)
    {
        g[a][b] = 1;
        g[b][a] = 1;
        m++;
    }
    
    TransformaMatrice_ListaAdiacenta(g,n,lista_adiacenta);
    TransformaLista_MatriceIncidenta(lista_adiacenta,incidenta,n,m);
    TransformaMatriceIncidenta_MatriceAdiacenta(incidenta,g,n,m);

    Afiseaza_MatriceaAdiacenta(g,n);
    Afiseaza_ListaAdiacenta(lista_adiacenta,n);
    Afiseaza_MatriceIncidenta(incidenta,n,m);

    return 0;
}

void TransformaMatrice_ListaAdiacenta(bool g[][101], int n, vector<vector<int>>& lista_adiacenta)
{
    for(int i = 1; i <= n; i++)
    {
        for(int j = 1; j <= n; j++)
        {
            if(g[i][j] == 1)
            {
                lista_adiacenta[i].push_back(j);
            }
        }
    }
}

void TransformaLista_MatriceIncidenta(vector<vector<int>>& lista_adiacenta, bool incidenta[][101], int n, int m)
{
    int muchie = 1;
    for(int i = 1; i <= n; i++)
    {
        for(int j: lista_adiacenta[i])
        {
            if(j > i)
            {
                incidenta[i][muchie] = 1;
                incidenta[j][muchie] = 1;
                muchie++;
            }
        }
    }
}

void TransformaMatriceIncidenta_MatriceAdiacenta(bool incidenta[][101], bool g[][101], int n, int m)
{
    for(int j = 1; j <= m; j++)
    {
        int unu = 0, doi = 0;
        for(int i = 1; i <= n; i++)
        {
            if(incidenta[i][j] == 1 && unu == 0)
            {
                unu = i;
            }
            else if(incidenta[i][j] == 1)
            {
                doi = i;
            }
        }
        g[unu][doi] = 1;
        g[doi][unu] = 1;
    }
}

void Afiseaza_MatriceaAdiacenta(bool g[][101], int n)
{
    for(int i = 1; i <= n; i++)
    {
        for(int j = 1; j <= n; j++)
        {
            cout << g[i][j] << " ";
        }
        cout << endl;
    }
}

void Afiseaza_ListaAdiacenta(vector<vector<int>>& lista_adiacenta, int n)
{
    for(int i = 1; i <= n; i++)
    {
        cout << "Nodul " << i << " este legat de nodurile: "; 
        for(int j: lista_adiacenta[i])
        {
            cout << j << " ";
        }
        cout << endl;
    }
}

void Afiseaza_MatriceIncidenta(bool incidenta[][101], int n, int m)
{
    for(int i = 1; i <= n; i++)
    {
        for(int j = 1; j <= m; j++)
        {
            cout << incidenta[i][j] << " ";
        }
        cout << endl;
    }
}