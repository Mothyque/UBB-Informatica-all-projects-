#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <queue>
#include <unordered_map>

using namespace std;

struct Nod{
    int x,y;
    int g, h, f;
    Nod* parinte;

    Nod(int x, int y, int g, int h, Nod* parinte = nullptr)
    {
        this->x = x;
        this->y = y;
        this->g = g;
        this->h = h;
        this->f = g + h;
        this->parinte = parinte;
    }
    bool operator>(const Nod& alt) const
    {
        return f > alt.f;
    }
};

struct Comparator{
    bool operator()(const Nod* a, const Nod* b)
    {
        return a->f > b->f;
    }
};

const int dx[] = {-1, 1, 0, 0};
const int dy[] = {0, 0, -1, 1};
vector<vector<char>> CitesteLabirint(const string& fisier);
void AfiseazaLabirint(const vector<vector<char>>& labirint, const string& fisier);
pair<int, int> Gaseste_Pozitie(const vector<vector<char>>& labirint, char c);
int CalculeazaH(int x1, int y1, int x2, int y2);
bool EsteValid(int x, int y, const vector<vector<char>>& labirint, const vector<vector<bool>>& vizitat);
vector<vector<char>> AStar(vector<vector<char>> labirint, int startX, int startY, int finalX, int finalY);

int main()
{
    vector<vector<char>> labirint =CitesteLabirint("labirint_cuvinte.txt");
    pair<int, int> start = Gaseste_Pozitie(labirint, 'S');
    pair<int, int> finish = Gaseste_Pozitie(labirint, 'F');
    vector<vector<char>> labirint_rezolvat = AStar(labirint, start.first, start.second, finish.first, finish.second);
    AfiseazaLabirint(labirint_rezolvat, "labirint_rezolvat.txt");
    return 0;
}

vector<vector<char>> CitesteLabirint(const string& fisier)
{
    ifstream fin(fisier);
    vector<vector<char>> labirint;
    string linie;
    while(getline(fin, linie))
    {
        labirint.push_back(vector<char>(linie.begin(), linie.end()));
    }
    fin.close();
    return labirint;
}

void AfiseazaLabirint(const vector<vector<char>>& labirint, const string& fisier)
{
    ofstream fout(fisier);
    for(const auto& linie : labirint)
    {
        for(char c : linie)
        {
            fout << c;
        }
        fout << endl;
    }
    fout.close();
}

pair<int, int> Gaseste_Pozitie(const vector<vector<char>>& labirint, char c)
{
    for(int i = 0; i < labirint.size(); i++)
    {
        for(int j = 0; j < labirint[i].size(); j++)
        {
            if(labirint[i][j] == c)
            {
                return {i, j};
            }
        }
    }
    return {-1, -1};
}

int CalculeazaH(int x1, int y1, int x2, int y2)
{
    return abs(x1 - x2) + abs(y1 - y2);
}

bool EsteValid(int x, int y, const vector<vector<char>>& labirint, const vector<vector<bool>>& vizitat)
{
    return x >= 0 && x < labirint.size() && y >= 0 && y < labirint[0].size() && labirint[x][y] != '1' && !vizitat[x][y];
}

vector<vector<char>> AStar(vector<vector<char>> labirint, int startX, int startY, int finalX, int finalY)
{
    priority_queue<Nod*, vector<Nod*>, Comparator> q;
    vector<vector<bool>> vizitat(labirint.size(), vector<bool>(labirint[0].size(), false));

    Nod* start = new Nod(startX, startY, 0, CalculeazaH(startX, startY, finalX, finalY));
    q.push(start);

    unordered_map<int, Nod*> parinte;
    parinte[startX * labirint[0].size() + startY] = nullptr;

    while(!q.empty())
    {
        Nod* curent = q.top();
        q.pop();
        int x = curent->x, y = curent->y;
        if(vizitat[x][y])
        {
            delete curent;
            continue;
        }
        vizitat[x][y] = true;

        if(x == finalX && y == finalY)
        {
            while(parinte[x * labirint[0].size() + y] != nullptr)
            {
                if(labirint[x][y] == ' ')
                {
                    labirint[x][y] = '0';
                }
                Nod* temp = parinte[x * labirint[0].size() + y];
                x = temp->x;
                y = temp->y;
            }
            break;
        }
        for(int i = 0; i < 4; i++)
        {
            int newX = x + dx[i];
            int newY = y + dy[i];
            if(EsteValid(newX, newY, labirint, vizitat))
            {
                int gNou = curent->g + 1;
                int hNou = CalculeazaH(newX, newY, finalX, finalY);
                Nod* nodNou = new Nod(newX, newY, gNou, hNou, curent);
                q.push(nodNou);
                parinte[newX * labirint[0].size() + newY] = curent;
            }
        }
    }
    
    return labirint;
}
