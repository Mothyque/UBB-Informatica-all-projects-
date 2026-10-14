#include <cctype>
#include <fstream>
#include <iomanip>
#include <iostream>
#include <map>
#include <string>
#include <unordered_set>
#include <vector>

struct Token {
    std::string atom;
    std::string lexeme;
    int line;
};

const std::unordered_set<std::string> KEYWORDS = {
    "int", "float", "Punct", "citeste", "afiseaza",
    "if", "else", "while", "return"
};

const std::unordered_set<std::string> TWO_CHARACTER_OPERATORS = {
    "!=", "==", "<=", ">="
};

const std::unordered_set<char> ONE_CHARACTER_TOKENS = {
    '(', ')', '{', '}', ';', ',', '=', '+', '-', '*', '/', '%', '<', '>'
};

bool isIdentifierStart(char character) {
    return std::isalpha(static_cast<unsigned char>(character)) || character == '_';
}

bool isIdentifierPart(char character) {
    return std::isalnum(static_cast<unsigned char>(character)) || character == '_';
}

bool isNumberStart(const std::string& source, std::size_t position, const std::string& previousAtom) {
    if (std::isdigit(static_cast<unsigned char>(source[position]))) {
        return true;
    }

    if ((source[position] != '+' && source[position] != '-') ||
        position + 1 >= source.size() ||
        !std::isdigit(static_cast<unsigned char>(source[position + 1]))) {
        return false;
    }

    return previousAtom.empty() || previousAtom == "=" || previousAtom == "(" ||
           previousAtom == "," || previousAtom == "+" || previousAtom == "-" ||
           previousAtom == "*" || previousAtom == "/" || previousAtom == "%";
}

void addToken(std::vector<Token>& tokens, const std::string& atom,
              const std::string& lexeme, int line, std::string& previousAtom) {
    tokens.push_back({atom, lexeme, line});
    previousAtom = atom;
}

std::vector<Token> tokenize(const std::string& source, std::vector<std::string>& errors) {
    std::vector<Token> tokens;
    std::string previousAtom;
    std::size_t position = 0;
    int line = 1;

    while (position < source.size()) {
        char character = source[position];

        if (character == '\n') {
            ++line;
            ++position;
            continue;
        }

        if (std::isspace(static_cast<unsigned char>(character))) {
            ++position;
            continue;
        }

        if (character == '/' && position + 1 < source.size() && source[position + 1] == '/') {
            position += 2;
            while (position < source.size() && source[position] != '\n') {
                ++position;
            }
            continue;
        }

        if (isIdentifierStart(character)) {
            std::size_t start = position++;
            while (position < source.size() && isIdentifierPart(source[position])) {
                ++position;
            }

            std::string lexeme = source.substr(start, position - start);
            if (KEYWORDS.find(lexeme) != KEYWORDS.end()) {
                addToken(tokens, lexeme, lexeme, line, previousAtom);
            } else if (lexeme.size() > 250) {
                errors.push_back("Linia " + std::to_string(line) + ": identificatorul '" +
                                 lexeme + "' depaseste 250 de caractere.");
            } else {
                addToken(tokens, "ID", lexeme, line, previousAtom);
            }
            continue;
        }

        if (isNumberStart(source, position, previousAtom)) {
            std::size_t start = position;
            if (source[position] == '+' || source[position] == '-') {
                ++position;
            }
            while (position < source.size() &&
                   std::isdigit(static_cast<unsigned char>(source[position]))) {
                ++position;
            }

            if (position < source.size() && source[position] == '.') {
                ++position;
                while (position < source.size() &&
                       std::isdigit(static_cast<unsigned char>(source[position]))) {
                    ++position;
                }
            }

            std::string lexeme = source.substr(start, position - start);
            addToken(tokens, "CONST", lexeme, line, previousAtom);
            continue;
        }

        if (position + 1 < source.size()) {
            std::string twoCharacters = source.substr(position, 2);
            if (TWO_CHARACTER_OPERATORS.find(twoCharacters) != TWO_CHARACTER_OPERATORS.end()) {
                addToken(tokens, twoCharacters, twoCharacters, line, previousAtom);
                position += 2;
                continue;
            }
        }

        if (ONE_CHARACTER_TOKENS.find(character) != ONE_CHARACTER_TOKENS.end()) {
            std::string lexeme(1, character);
            addToken(tokens, lexeme, lexeme, line, previousAtom);
            ++position;
            continue;
        }

        errors.push_back("Linia " + std::to_string(line) + ": caracter necunoscut '" +
                         std::string(1, character) + "'.");
        ++position;
    }

    return tokens;
}

int main(int argc, char* argv[]) {
    if (argc != 2) {
        std::cerr << "Utilizare: analizator_lexical <fisier_sursa>\n";
        return 1;
    }

    std::ifstream input(argv[1]);
    if (!input) {
        std::cerr << "Eroare: fisierul '" << argv[1] << "' nu poate fi deschis.\n";
        return 1;
    }

    std::string source((std::istreambuf_iterator<char>(input)),
                       std::istreambuf_iterator<char>());
    std::vector<std::string> errors;
    std::vector<Token> tokens = tokenize(source, errors);

    std::cout << std::left << std::setw(8) << "Linia"
              << std::setw(16) << "Atom" << "Lexema\n";
    std::cout << std::string(42, '-') << '\n';
    for (const Token& token : tokens) {
        std::cout << std::left << std::setw(8) << token.line
                  << std::setw(16) << token.atom << token.lexeme << '\n';
    }

    if (errors.empty()) {
        std::cout << "\nAnaliza lexicala s-a incheiat fara erori.\n";
    } else {
        std::cout << "\nErori lexicale:\n";
        for (const std::string& error : errors) {
            std::cout << error << '\n';
        }
    }

    return errors.empty() ? 0 : 2;
}