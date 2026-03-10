#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

ifstream fin("in.txt");

vector<int> NoduriIzolate(bool g[][101],int n);
bool EsteGrafRegular(bool g[][101],int n);
void CreeazaMatriceaDistantelor(int d[][101], bool g[][101], int n);
bool EsteGrafConex(int d[][101], int n);

const int inf = 1e9;

int main()
{
    int n, a, b, d[101][101];
    bool g[101][101];
    fin >> n;
    while(fin >> a >> b)
    {
        g[a][b] = 1;
        g[b][a] = 1;
    }
    vector<int> noduri = NoduriIzolate(g,n);
    if(noduri.size() == 0)
    {
        cout << "Nu exista noduri izolate" << "\n";
    }   
    else
    {
        cout << "Nodurile izolate sunt: ";
        for(int nod: noduri)
        {
            cout << nod << " ";
        }
        cout << endl;
    }

    if(EsteGrafRegular(g,n))
    {
        cout << "Graful este regulat" << "\n";
    }
    else
    {
        cout << "Graful nu este regulat" << "\n";
    }

    CreeazaMatriceaDistantelor(d,g,n);

    for(int i = 1; i <= n; i++)
    {
        for(int j = 1; j <= n; j++)
        {
            if(d[i][j] == inf)
            {
                cout << 0 << ' ';
            }
            else
            {
                cout << d[i][j] << " ";
            }
        }
        cout << endl;
    }

    if(EsteGrafConex(d, n))
    {
        cout << "Graful este conex";
    }
    else
    {
        cout << "Graful nu este conex";
    }
    
    return 0;

}

vector<int> NoduriIzolate(bool g[][101],int n)
{
    vector<int> noduri;
    for(int i = 1; i <= n; i++)
        {
            bool izolat = true;
            for(int j = 1; j <= n; j++)
            {
                if(g[i][j] == 1)
                {
                    izolat = false;
                    break;
                }
            }
            if(izolat == true)
            {
                noduri.push_back(i);
            } 
        }
    return noduri;
}

bool EsteGrafRegular(bool g[][101], int n)
{
    int grad_nod_anterior = -1;
    for(int i = 1; i <= n; i++)
    {
        int grad = 0;
        for(int j = 1; j <= n; j++)
        {
            if(g[i][j] == 1)
            {
                grad++;
            }
        }
        if (grad_nod_anterior == -1)
        {
            grad_nod_anterior = grad;
        }
        else if(grad != grad_nod_anterior)
        {
            return false;
        }
    }
    return true;
}

void CreeazaMatriceaDistantelor(int d[][101], bool g[][101], int n)
{
    for(int i = 1; i <= n; i++)
    {
        for(int j = 1; j <= n; j++)
        {
            if(i == j)
            {
                d[i][j] = 0;
            }
            else if(g[i][j] == 1)
            {
                d[i][j] = 1;
            }
            else
            {
                d[i][j] = inf;
            }
        }
    }
    for(int k = 1; k <= n; k++)
    {
        for(int i = 1; i <= n; i++)
        {
            for(int j = 1; j <= n; j++)
            {
                if(d[i][k] < inf && d[k][j] < inf)
                {
                    d[i][j] = min(d[i][j], d[i][k] + d[k][j]);
                }
            }
        }
    }
}


bool EsteGrafConex(int d[][101], int n)
{
    for(int i = 1; i <= n; i++)
    {
        for(int j = 1; j <= n; j++)
        {
            if(d[i][j] == inf)
            {
                return false;
            }
        }
    }
    return true;
}