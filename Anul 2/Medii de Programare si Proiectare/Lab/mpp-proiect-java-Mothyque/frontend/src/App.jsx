import {useState, useEffect} from 'react'
import {matchService} from './services/matchService'
import authService from './services/authService'
import MatchFilter from './components/MatchFilter'
import MatchList from './components/MatchList'
import MatchForm from './components/MatchForm'
import notificationService from './services/notificationService';
function App() {

    const [isAuthenticated, setIsAuthenticated] = useState(!!authService.getToken());
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [authError, setAuthError] = useState(null);

    const [matches, setMatches] = useState([]);
    const [error, setError] = useState(null);
    const [editingMatch, setEditingMatch] = useState(null);

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            await authService.login(username, password);
            setIsAuthenticated(true);
            setAuthError(null);
            loadMatches();
        } catch (err) {
            setAuthError('Invalid username or password');
            console.error('Login error:', err);
        }
    };

    const handleLogout = () => {
        authService.logout();
        setIsAuthenticated(false);
        setMatches([]);
    }

    const loadMatches = async (matchType = '') => {
      try {
        const data = await matchService.getAllMatches(matchType);
        setMatches(data);
        setError(null);
      }
      catch (err) {
        console.error('Error fetching matches:', err);
        setError('Failed to load matches. Please try again later.');
      }
    };

    useEffect(() => {
        loadMatches();
    }, []);

    useEffect(() => {
        let isMounted = true;
        
        const timer = setTimeout(() => {
            if (!isMounted) return;

            console.log("Initializing notification socket stream...");
            const handleNotification = () => {
                console.log("⚡ Live Signal Received: Re-fetching data grid...");
                loadMatches();
            };

            notificationService.connect(handleNotification);
        }, 100);

        return () => {
            isMounted = false;
            clearTimeout(timer);
            console.log("Cleaning up notification stream...");
            notificationService.disconnect();
        };
    }, [isAuthenticated]); 

    const handleAddMatch = async (newMatchData) => {
        try {
            if (editingMatch) {
                await matchService.updateMatch(editingMatch.id, newMatchData);
                setEditingMatch(null);
            }
            else {
                await matchService.createMatch(newMatchData);
            }
            loadMatches(); 
        } catch (err) {
            console.error("Error creating match:", err);
            alert("Failed to add match. Check the console.");
        }
    };

    const handleDeleteMatch = async (id) => {
        if (window.confirm("Are you sure you want to delete this match?")) {
            try {
                await matchService.deleteMatch(id);
                if (editingMatch && editingMatch.id === id) {
                    setEditingMatch(null);
                }
                loadMatches(); 
            } catch (err) {
                console.error("Error deleting match:", err);
                alert("Failed to delete match.");
            }
        }
    };
    

    return (
    <div style={{ maxWidth: '800px', margin: '0 auto', padding: '20px', fontFamily: 'sans-serif' }}>
        <h1> 🏀 Basketball Ticket Manager</h1>
        
        {!isAuthenticated ? (
            <div style={{ background: '#f4f4f4', padding: '15px', borderRadius: '5px', marginBottom: '20px' }}>
                <h3>Login</h3>
                {authError && <p style={{ color: 'red' }}>{authError}</p>}
                <form onSubmit={handleLogin} style={{ display: 'flex', gap: '10px' }}>
                    <input type="text" placeholder="Username" onChange={(e) => setUsername(e.target.value)} />
                    <input type="password" placeholder="Password" onChange={(e) => setPassword(e.target.value)} />
                    <button type="submit">Login</button>
                </form>
            </div>
        ) : (
            <div style={{ marginBottom: '20px', textAlign: 'right' }}>
                <span>Logged in </span>
                <button onClick={handleLogout}>Logout</button>
            </div>
        )}

        {error && <div style={{ color: 'red', marginBottom: '20px' }}>{error}</div>}

        {isAuthenticated && (
            <MatchForm onAdd={handleAddMatch} matchToEdit={editingMatch} onCancelEdit={() => setEditingMatch(null)} />
        )}

        <MatchFilter onFilter={loadMatches} />
        
        <MatchList 
            matches={matches} 
            onDelete={isAuthenticated ? handleDeleteMatch : null} 
            onEdit={isAuthenticated ? setEditingMatch : null} 
        />
    </div>
);
  
}

export default App
