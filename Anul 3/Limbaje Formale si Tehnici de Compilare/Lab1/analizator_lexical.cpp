#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <unordered_map>
#include <algorithm>
#include <cctype>
#include <iomanip>

using std::string;
using std::vector;
using std::unordered_map;
using std::ifstream;
using std::ofstream;
using std::cout;
using std::cerr;
using std::to_string;
using std::istreambuf_iterator;

const unordered_map<string, int> COD_ATOM = {
    {"ID", 0},
    {"CONST", 1},
    {"int", 2},
    {"float", 3},
    {"Punct", 4},
    {"citeste", 5},
    {"afiseaza", 6},
    {"if", 7},
    {"else", 8},
    {"while", 9},
    {"return", 10},
    {"=", 11},
    {"+", 12},
    {"-", 13},
    {"*", 14},
    {"/", 15},
    {"%", 16},
    {"<", 17},
    {">", 18},
    {"<=", 19},
    {">=", 20},
    {"==", 21},
    {"!=", 22},
    {"(", 23},
    {")", 24},
    {"{", 25},
    {"}", 26},
    {";", 27},
    {",", 28}
};

class SymbolTable {
private:
    vector<string> symbols;

public:
    int search(const string& lexeme) const {
        auto it = lower_bound(symbols.begin(), symbols.end(), lexeme);
        if (it != symbols.end() && *it == lexeme) {
            return static_cast<int>(distance(symbols.begin(), it));
        }
        return -1;
    }

    int insert(const string& lexeme) {
        auto it = lower_bound(symbols.begin(), symbols.end(), lexeme);
        if (it != symbols.end() && *it == lexeme) {
            return static_cast<int>(distance(symbols.begin(), it));
        }
        auto insertedIt = symbols.insert(it, lexeme);
        return static_cast<int>(distance(symbols.begin(), insertedIt));
    }

    const string& getAt(int index) const {
        return symbols.at(index);
    }

    size_t size() const {
        return symbols.size();
    }

    const vector<string>& getAll() const {
        return symbols;
    }
};

struct FIPEntry {
    int codAtom;
    int codTS; 
};

struct Token {
    string atomType;
    string lexeme;
    int line;
};

bool isIdentifierStart(char c) {
    return isalpha(static_cast<unsigned char>(c)) || c == '_';
}

bool isIdentifierPart(char c) {
    return isalnum(static_cast<unsigned char>(c)) || c == '_';
}

bool isNumberStart(const string& src, size_t pos, const string& prevAtom) {
    if (isdigit(static_cast<unsigned char>(src[pos]))) {
        return true;
    }
    if ((src[pos] == '+' || src[pos] == '-') && pos + 1 < src.size() &&
        isdigit(static_cast<unsigned char>(src[pos + 1]))) {
        return prevAtom.empty() || prevAtom == "=" || prevAtom == "(" ||
               prevAtom == "," || prevAtom == "+" || prevAtom == "-" ||
               prevAtom == "*" || prevAtom == "/" || prevAtom == "%" ||
               prevAtom == "<" || prevAtom == ">" || prevAtom == "<=" ||
               prevAtom == ">=" || prevAtom == "==" || prevAtom == "!=";
    }
    return false;
}

vector<Token> tokenize(const string& source, vector<string>& errors) {
    vector<Token> tokens;
    string previousAtom = "";
    size_t pos = 0;
    int line = 1;

    while (pos < source.size()) {
        char c = source[pos];

        if (c == '\n') {
            line++;
            pos++;
            continue;
        }

        if (isspace(static_cast<unsigned char>(c))) {
            pos++;
            continue;
        }

        if (c == '/' && pos + 1 < source.size() && source[pos + 1] == '/') {
            pos += 2;
            while (pos < source.size() && source[pos] != '\n') {
                pos++;
            }
            continue;
        }

        if (isIdentifierStart(c)) {
            size_t start = pos++;
            while (pos < source.size() && isIdentifierPart(source[pos])) {
                pos++;
            }
            string lexeme = source.substr(start, pos - start);

            if (COD_ATOM.find(lexeme) != COD_ATOM.end() && lexeme != "ID" && lexeme != "CONST") {
                tokens.push_back({lexeme, lexeme, line});
                previousAtom = lexeme;
            } else if (lexeme.size() > 250) {
                errors.push_back("Linia " + to_string(line) + ": identificatorul depaseste 250 caractere: '" + lexeme + "'");
            } else {
                tokens.push_back({"ID", lexeme, line});
                previousAtom = "ID";
            }
            continue;
        }

        if (isNumberStart(source, pos, previousAtom)) {
            size_t start = pos;
            if (source[pos] == '+' || source[pos] == '-') {
                pos++;
            }
            while (pos < source.size() && isdigit(static_cast<unsigned char>(source[pos]))) {
                pos++;
            }
            if (pos < source.size() && source[pos] == '.') {
                pos++;
                while (pos < source.size() && isdigit(static_cast<unsigned char>(source[pos]))) {
                    pos++;
                }
            }
            string lexeme = source.substr(start, pos - start);
            tokens.push_back({"CONST", lexeme, line});
            previousAtom = "CONST";
            continue;
        }

        if (pos + 1 < source.size()) {
            string op2 = source.substr(pos, 2);
            if (COD_ATOM.find(op2) != COD_ATOM.end()) {
                tokens.push_back({op2, op2, line});
                previousAtom = op2;
                pos += 2;
                continue;
            }
        }

        string op1(1, c);
        if (COD_ATOM.find(op1) != COD_ATOM.end()) {
            tokens.push_back({op1, op1, line});
            previousAtom = op1;
            pos++;
            continue;
        }

        errors.push_back("Linia " + to_string(line) + ": caracter necunoscut '" + string(1, c) + "'");
        pos++;
    }

    return tokens;
}

int main(int argc, char* argv[]) {
    if (argc != 2) {
        cerr << "Utilizare: " << argv[0] << " <fisier_sursa>\n";
        return 1;
    }

    ifstream inputFile(argv[1]);
    if (!inputFile) {
        cerr << "Eroare la deschiderea fisierului '" << argv[1] << "'\n";
        return 1;
    }

    string source((istreambuf_iterator<char>(inputFile)),
                       istreambuf_iterator<char>());
    inputFile.close();

    vector<string> errors;
    vector<Token> tokens = tokenize(source, errors);

    if (!errors.empty()) {
        cerr << "Au fost detectate erori lexicale:\n";
        for (const auto& err : errors) {
            cerr << "  " << err << "\n";
        }
        return 2;
    }

    SymbolTable ts;
    for (const auto& token : tokens) {
        if (token.atomType == "ID" || token.atomType == "CONST") {
            ts.insert(token.lexeme);
        }
    }

    vector<FIPEntry> fip;
    for (const auto& token : tokens) {
        int cod = COD_ATOM.at(token.atomType);
        int pozTS = -1;

        if (token.atomType == "ID" || token.atomType == "CONST") {
            pozTS = ts.search(token.lexeme); // indexul (0-based) din TS
        }

        fip.push_back({cod, pozTS});
    }

    ofstream tsFile("TS.txt");
    if (!tsFile) {
        cerr << "Nu s-a putut crea fisierul TS.txt\n";
        return 1;
    }
    tsFile << "Pozitie_TS | Simbol\n";
    tsFile << "--------------------\n";
    for (size_t i = 0; i < ts.size(); ++i) {
        tsFile << ts.getAt(i) << "\n";
    }
    tsFile.close();

    ofstream fipFile("FIP.txt");
    if (!fipFile) {
        cerr << "Nu s-a putut crea fisierul FIP.txt\n";
        return 1;
    }
    fipFile << "Cod_Atom | Pozitie_TS\n";
    fipFile << "---------------------\n";
    for (const auto& entry : fip) {
        if (entry.codTS != -1) {
            fipFile << entry.codAtom << " " << (entry.codTS + 1) << "\n";
        } else {
            fipFile << entry.codAtom << " -\n";
        }
    }
    fipFile.close();

    cout << "Analiza lexicala s-a realizat cu succes!\n";
    cout << "Fisiere generate:\n  - TS.txt (" << ts.size() << " simboluri)\n  - FIP.txt (" << fip.size() << " atomi)\n";

    return 0;
}