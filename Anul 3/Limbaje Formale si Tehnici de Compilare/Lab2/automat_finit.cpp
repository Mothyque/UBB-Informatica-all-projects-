#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <sstream>
#include <vector>
#include <map>
#include <set>

using std::string;
using std::vector;
using std::map;
using std::set;
using std::pair;
using std::cout;
using std::cin;
using std::getline;


class FiniteAutomaton {
private:
    set<string> states;
    set<string> alphabet;
    string initialState;
    set<string> finalStates;

    map<pair<string, char>, vector<string>> transitions;

    vector<string> split(const string& line, char delimiter) {
        vector<string> tokens;
        string token;
        std::istringstream tokenStream(line);

        while (getline(tokenStream, token, delimiter)) {
            size_t start = token.find_first_not_of(" \t\n\r");
            size_t end = token.find_last_not_of(" \t\n\r");
            if (start != string::npos && end != string::npos) {
                tokens.push_back(token.substr(start, end - start + 1));
            }
        }
        return tokens;
    }
public:
    void clear() {
        states.clear();
        alphabet.clear();
        initialState.clear();
        finalStates.clear();
        transitions.clear();
    }

    bool loadFromFile(const string& filename) {
        std::ifstream file(filename);
        if (!file.is_open()) {
            std::cerr << "Error opening file: " << filename << "\n";
            return false;
        }
        clear();
        string line;
        if (getline(file, line)) {
            vector<string> symbols = split(line, ',');
            for (const auto& symbol : symbols) {
                if (!symbol.empty()) {
                    alphabet.insert(symbol.substr(0, 1));
                }
            }
        }

        if (getline(file, line)) {
            vector<string> statesList = split(line, ',');
            for (const auto& state : statesList) {
                if (!state.empty()) {
                    states.insert(state);
                }
            }
        }

        if (getline(file, line)) {
            vector<string> init = split(line, ',');
            if (!init.empty()) {
                initialState = init[0];
            }
        }

        if (getline(file, line)) {
            vector<string> finals = split(line, ',');
            for (const auto& finalState : finals) {
                if (!finalState.empty()) {
                    finalStates.insert(finalState);
                }
            }
        }

        while (getline(file, line)) {
            vector<string> transitionParts = split(line, ',');
            if (transitionParts.size() == 3) {
                string fromState = transitionParts[0];
                string to = transitionParts[1];
                char symbol = transitionParts[2][0]; 
                transitions[{fromState, symbol}].push_back(to);
            }
        }
        file.close();
        return true;
    }

    void readFromKeyboard() {
        clear();
        string line; 

        cout << "Enter the alphabet symbols (comma-separated): ";
        getline(cin, line);
        for (const auto& symbol : split(line, ',')) {
            if (!symbol.empty()) {
                alphabet.insert(symbol.substr(0, 1));
            }
        }

        cout << "Enter the states (comma-separated): ";
        getline(cin, line);
        for (const auto& state : split(line, ',')) {
            if (!state.empty()) {
                states.insert(state);
            }
        }

        cout << "Enter the initial state: ";
        getline(cin, line);
        if (!line.empty()) {
            initialState = line;
        }

        cout << "Enter the final states (comma-separated): ";
        getline(cin, line);
        for (const auto& finalState : split(line, ',')) {
            if (!finalState.empty()) {
                finalStates.insert(finalState);
            }
        }

        int n; 
        cout << "Enter the number of transitions: ";
        cin >> n;
        cin.ignore();

        cout << "Enter the transitions in the format 'fromState,symbol,toState':" << "\n";
        for (int i = 0; i < n; ++i) {
            getline(cin, line);
            vector<string> transitionParts = split(line, ',');
            if (transitionParts.size() == 3) {
                string fromState = transitionParts[0];
                string to = transitionParts[1];
                char symbol = transitionParts[2][0]; 
                transitions[{fromState, symbol}].push_back(to);
            }
        }
    }

    bool isDeterministic() const {
        for (const auto& transition : transitions) {
            const auto& value = transition.second;
            if (value.size() > 1) {
                return false;
            }
        }
        return true;
    }

    void printStates() const {
        cout << "Q = { ";
        for (const auto& state : states) {
            cout << state << " ";
        }
        cout << "}" << "\n";
    }

    void printAlphabet() const {
        cout << "Σ = { ";
        for (const auto& symbol : alphabet) {
            cout << symbol << " ";
        }
        cout << "}" << "\n";
    }

    void printInitialState() const {
        cout << "q0 = " << initialState << "\n";
    }

    void printFinalStates() const {
        cout << "F = { ";
        for (const auto& finalState : finalStates) {
            cout << finalState << " ";
        }
        cout << "}" << "\n";
    }

    void printTransitions() const {
        cout << "Transitions:" << "\n";
        for (const auto& transition : transitions) {
            const auto& fromState = transition.first.first;
            char symbol = transition.first.second;
            const auto& toStates = transition.second;
            for (const auto& toState : toStates) {
                cout << "(" << fromState << ", " << symbol << ") -> " << toState << "\n";
            }
        }
    }

    bool accepts(const string& sequence) const {
        if (!isDeterministic()) {
            cout << "The automaton is non-deterministic. Acceptance check is not implemented for non-deterministic automata." << "\n";
            return false;
        }

        string currentState = initialState;

        for (char c : sequence) {
            auto it = transitions.find({currentState, c});
            if (it == transitions.end() || it->second.empty()) {
                return false;
            }
            currentState = it->second[0];
        }
        return finalStates.find(currentState) != finalStates.end();
    }

    string getLognestPrefix(const string& sequence) const {
        if (!isDeterministic()) {
            return "";
        }

        string currentState = initialState;
        string longestPrefix = "";
        string currentPrefix = "";

        if (finalStates.find(currentState) != finalStates.end()) {
            longestPrefix = "";
        }

        for (char c : sequence) {
            auto it = transitions.find({currentState, c});
            if (it == transitions.end() || it->second.empty()) {
                break;
            }

            currentState = it->second[0];
            currentPrefix += c;

            if (finalStates.find(currentState) != finalStates.end()) {
                longestPrefix = currentPrefix;
            }
        }
        return longestPrefix;
    }
};

void showMenu() {
    cout << "=======================" << "\n";
    cout << "1. Load finite automaton from file" << "\n";
    cout << "2. Read finite automaton from keyboard" << "\n";
    cout << "3. Print states" << "\n";
    cout << "4. Print alphabet" << "\n";
    cout << "5. Print initial state" << "\n";
    cout << "6. Print final states" << "\n";
    cout << "7. Print transitions" << "\n";
    cout << "8. Print automaton details" << "\n";
    cout << "9. Check if a sequence is accepted" << "\n";
    cout << "10. Get longest accepted prefix of a sequence" << "\n";
    cout << "0. Exit" << "\n";
    cout << ">>> ";
}

int main() {
    FiniteAutomaton fa;
    int option = -1;
    fa.loadFromFile("constante.txt"); 

    while(option != 0) {
        showMenu();
        std::cin >> option;
        std::cin.ignore(); 

        switch(option) {
            case 1: {
                string filename;
                cout << "Enter the filename: ";
                getline(cin, filename);
                if (fa.loadFromFile(filename)) {
                    cout << "Finite automaton loaded successfully from " << filename << "\n";
                } else {
                    cout << "Failed to load finite automaton from " << filename << "\n";
                }
                break;
            }
            case 2:
                fa.readFromKeyboard();
                break;
            case 3:
                fa.printStates();
                break;
            case 4:
                fa.printAlphabet();
                break;
            case 5:
                fa.printInitialState();
                break;
            case 6:
                fa.printFinalStates();
                break;
            case 7:
                fa.printTransitions();
                break;
            case 8:
                fa.printStates();
                fa.printAlphabet();
                fa.printInitialState();
                fa.printFinalStates();
                fa.printTransitions();
                if (fa.isDeterministic()) {
                    cout << "The automaton is deterministic." << "\n";
                } else {
                    cout << "The automaton is non-deterministic." << "\n";
                }
                break;
            case 9: {
                if (!fa.isDeterministic()) {
                    cout << "The automaton is non-deterministic. Acceptance check is not implemented for non-deterministic automata." << "\n";
                } else {
                    string sequence;
                    cout << "Enter the sequence to check: ";
                    getline(cin, sequence);
                    if (fa.accepts(sequence)) {
                        cout << "The sequence is accepted." << "\n";
                    } else {
                        cout << "The sequence is rejected." << "\n";
                    }
                }
                break;
            }
            case 10: {
                if (!fa.isDeterministic()) {
                    cout << "The automaton is non-deterministic. Longest prefix check is not implemented for non-deterministic automata." << "\n";
                } 
                string sequence;
                cout << "Enter the sequence to check for longest accepted prefix: ";
                getline(cin, sequence);
                string longestPrefix = fa.getLognestPrefix(sequence);
                if (!longestPrefix.empty()) {
                    cout << "The longest accepted prefix is: " << longestPrefix << "\n";
                } else {
                    cout << "No accepted prefix found." << "\n";
                }
                break;
            }
            case 0:
                cout << "Exiting..." << "\n";
                break;
            default:
                cout << "Invalid option. Please try again." << "\n";
        }
    }
    return 0;
}