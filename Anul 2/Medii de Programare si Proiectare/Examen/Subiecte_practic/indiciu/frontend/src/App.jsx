import { useState, useEffect } from "react";

function App() {
  const [alias, setAlias] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [meciActiv, setMeciActiv] = useState(null);
  const [clasament, setClasament] = useState([]);
  const [mesajEroare, setMesajEroare] = useState('');

  const [searchAlias, setSearchAlias] = useState('');
  const [istoricJucator, setIstoricJucator] = useState([]);
  const [searchPerformed, setSearchPerformed] = useState(false);

  const [newLinie, setNewLinie] = useState('');
  const [newColoana, setNewColoana] = useState('');
  const [newText, setNewText] = useState('');
  const [mesajAdaugare, setMesajAdaugare] = useState('');

  const [blocat, setBlocat] = useState(false);

  const API_BASE = 'http://localhost:8080/api';

  useEffect(() => {
    fetch(`${API_BASE}/clasament`)
      .then(res => res.json())
      .then(data => setClasament(data))
      .catch(err => console.error('Error fetching clasament:', err));
  }, []);

  useEffect(() => {
    const eventSource = new EventSource(`${API_BASE}/clasament/stream`);

    eventSource.onmessage = (event) => {
      const clasamentUpdate = JSON.parse(event.data);
      setClasament(clasamentUpdate);
    };

    eventSource.onerror = (err) => {
      console.error('EventSource failed:', err);
      eventSource.close();
    };

    return () => eventSource.close();
  }, []);

  const handleStartJoc = async (e) => {
    e.preventDefault();
    if (!alias.trim()) return;
    
    try {
      const response = await fetch(`${API_BASE}/jocuri/start`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ alias : alias })
      });
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to start game');
      }
      const meci = await response.json();
      setMeciActiv(meci);
      setIsLoggedIn(true);
      setMesajEroare('');
    } catch (err) {
      setMesajEroare(err.message);
    }
  };

  const handleCardClick = async (index) => {
    if (blocat || !meciActiv) return;
    const linie = Math.floor(index / 4);
    const coloana = index % 4;

    setBlocat(true);

    try {
      const response = await fetch(`${API_BASE}/jocuri/${meciActiv.idJoc}/alegere`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ linie : linie, coloana : coloana })
      });
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to process card click');
      }
      const updatedMeci = await response.json();
      setMeciActiv(updatedMeci);
    } 
    catch (err) {
      setMesajEroare(err.message);
    }
    finally {
      setBlocat(false);
    }
  };
  const handleJocNou = () => {
    setMeciActiv(null);
    setIsLoggedIn(false);
    setMesajEroare('');
  };

  const handleSearchHistory = async (e) => {
    e.preventDefault();
    if (!searchAlias.trim()) return;

    try {
      const response = await fetch(`${API_BASE}/jocuri/${searchAlias.trim()}`);
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to fetch player history');
      }
      const data = await response.json();
      setIstoricJucator(data);
      setSearchPerformed(true);
      setMesajEroare('');
    }
    catch (err) {
      setMesajEroare(err.message);
    }
  };


  const handleAddConfig = async (e) => {
    e.preventDefault();
    const l = parseInt(newLinie);
    const c = parseInt(newColoana);
    if (isNaN(l) || isNaN(c) || l < 0 || l > 3 || c < 0 || c > 3 || !newText.trim()) {
      setMesajAdaugare('Date invalide. Linia și coloana trebuie să fie între 0 și 3, iar textul nu poate fi vid.');
      return;
    }
    if (!newText.trim()) {
      setMesajAdaugare('Textul indiciului nu poate fi vid.');
      return;
    }

    try {
      const response = await fetch(`${API_BASE}/configuratii`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ linie: l, coloana: c, text: newText.trim() })
      });
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to add configuration');
      }
      setMesajAdaugare('Configurație adăugată cu succes!');
      setNewLinie('');
      setNewColoana('');
      setNewText('');

      setTimeout(() => {
        setMesajAdaugare('');
      }, 3000);
    }
    catch (err) {
      setMesajAdaugare(err.message);
    }
  };


  const targetIndex = meciActiv ? (meciActiv.configuratie.linie * 4 + meciActiv.configuratie.coloana) : -1;

  const mutari = meciActiv && meciActiv.pozitiiPropuse ? meciActiv.pozitiiPropuse.split(';').filter(Boolean) : [];
  const ultimaMutare = mutari.length > 0 ? mutari[mutari.length - 1].split(',') : null;

  const isWon = meciActiv && ultimaMutare && 
    parseInt(ultimaMutare[0]) === meciActiv.configuratie.linie && 
    parseInt(ultimaMutare[1]) === meciActiv.configuratie.coloana;

  const isLost = meciActiv && meciActiv.nrIncercari >= 4 && !isWon;
  const isGameOver = isWon || isLost;

  return (
    <div style={{ padding: '20px', fontFamily: 'sans-serif', maxWidth: '800px', margin: '0 auto' }}>
      <h1>Indiciu</h1>
      <hr />

      {mesajEroare && <p style={{ color: 'red' }}>{mesajEroare}</p>}

      {!isLoggedIn ? (
        <div>
          <h3>Introdu alias pentru a începe jocul:</h3>
          <form onSubmit={handleStartJoc}>
            <input
              type="text"
              value={alias}
              onChange={(e) => setAlias(e.target.value)}
              placeholder="Alias"
              required
            />
            <button type="submit">Start Joc</button>
          </form>
        </div>
      ) : (
        <div>
          <h3>Jucător curent: {alias}</h3>
          <h3> Incercari: {4 - meciActiv?.nrIncercari} / 4</h3>
          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(4, 120px)', gap: '10px', marginTop: '20px' }}>
            {meciActiv?.tabla.map((valoareCelula, index) => {
              const isTargetCell = index === targetIndex;
              
              return (
                <button
                  key={index}
                  onClick={() => handleCardClick(index)}
                  disabled={blocat || isGameOver}
                  style={{
                    width: '120px',
                    height: '80px',
                    fontSize: '16px',
                    cursor: isGameOver ? 'default' : 'pointer',
                    backgroundColor: isWon && isTargetCell ? '#d4edda' : (valoareCelula !== " " ? '#fff3cd' : '#e2e3e5'),
                    border: '1px solid #ccc',
                    fontWeight: 'bold'
                  }}
                >
                  {isWon && isTargetCell ? "🎯 Găsit!" : (valoareCelula === " " ? "?" : valoareCelula)}
                </button>
              );
            })}
          </div>
          {isGameOver && (
            <div style={{ marginTop: '20px', padding: '15px', backgroundColor: isWon ? '#d4edda' : '#f8d7da', borderRadius: '5px' }}>
              {isWon ? (
                <div>
                  <h2 style={{ color: 'green' }}>🎉 Felicitări! Ai câștigat!</h2>
                  <p><strong>Indiciu descoperit:</strong> {meciActiv.configuratie.text}</p>
                </div>
              ) : (
                <div>
                  <h2 style={{ color: 'red' }}>💥 Game Over! Ai pierdut!</h2>
                  <p>Ai epuizat toate cele 4 încercări. Poziția corectă era linia {meciActiv.configuratie.linie}, coloana {meciActiv.configuratie.coloana}.</p>
                </div>
              )}
              <button onClick={handleJocNou} style={{ padding: '10px 20px', fontSize: '16px', marginTop: '10px', cursor: 'pointer' }}>
                Joacă din Nou
              </button>
            </div>
          )}
        </div>
      )}

      <h2> Clasament </h2>
      <table border="1"> 
        <thead>
          <tr>
            <th>Loc</th>
            <th>Jucator</th>
            <th>Data si ora</th>
            <th>Incercari</th>
            <th>Indiciul</th>
          </tr>
        </thead>
        <tbody>
          {clasament.length === 0 ? (
            <tr>
              <td colSpan="5">Nu exista inregistrari in clasament.</td>
            </tr>
          ) : (
            clasament.map((joc, index) => (
              <tr key={joc.id || index}>
                <td> <strong> {index + 1} </strong></td>
                <td>{joc.alias}</td>
                <td> {joc.dataOra}</td>
                <td> {joc.nrIncercari}</td>
                <td> {joc.nrIncercari === 10 ? '' : joc.textIndiciu}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>

      <div style={{ marginTop: '40px', padding: '15px', border: '1px solid #ddd', borderRadius: '5px', backgroundColor: '#f9f9f9' }}>
  <h2>🔍 Caută Istoric Jucător (Cerința d)</h2>
  
  <form onSubmit={handleSearchHistory} style={{ marginBottom: '15px' }}>
    <input
      type="text"
      value={searchAlias}
      onChange={(e) => setSearchAlias(e.target.value)}
      placeholder="Introdu alias jucător..."
      style={{ padding: '6px', marginRight: '10px', width: '200px' }}
    />
    <button type="submit" style={{ padding: '6px 12px', cursor: 'pointer' }}>Caută</button>
  </form>

  {searchPerformed && (
    <div>
          {istoricJucator.length === 0 ? (
            <p style={{ color: '#666', fontStyle: 'italic' }}>Niciun meci existent pentru acest alias.</p>
          ) : (
            <table border="1" cellPadding="6" style={{ width: '100%', borderCollapse: 'collapse', backgroundColor: '#fff' }}>
              <thead>
                <tr style={{ backgroundColor: '#eaeaea' }}>
                  <th>Meci #</th>
                  <th>Încercări</th>
                  <th>Poziții Propuse</th>
                  <th>Text Indiciu</th>
                </tr>
              </thead>
              <tbody>
                {istoricJucator.map((joc, index) => (
                  <tr key={joc.id || index}>
                    <td style={{ textAlign: 'center' }}>{index + 1}</td>
                    <td style={{ textAlign: 'center' }}>{joc.nrIncercari}</td>
                    <td>{joc.pozitiiPropuse}</td>
                    <td>
                      {joc.nrIncercari === 10 ? <em>(Nu a fost ghicit)</em> : joc.textIndiciu}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      )}
    </div>

    <div>
      <h2> Adauga Configuratie </h2>
      {mesajAdaugare && <p style={{ color: mesajAdaugare.includes('succes') ? 'green' : 'red' }}>{mesajAdaugare}</p>}
      <form onSubmit={handleAddConfig}>
        <div>
        <label> Linia (0-3): </label>   
            <input
              type="number"
              value={newLinie}
              onChange={(e) => setNewLinie(e.target.value)}
              min="0"
              max="3"
              required
              />  
        </div>  
        <div>
        <label> Coloana (0-3): </label>   
            <input
            type="number"
              value={newColoana}
              onChange={(e) => setNewColoana(e.target.value)}
              min="0"
              max="3"
              required
              />  
        </div>
        <div>
        <label> Text Indiciu: </label>   
            <input
              type="text"
              value={newText}
              onChange={(e) => setNewText(e.target.value)}
              required
              />  
        </div>
        <button type="submit" style={{ marginTop: '10px', padding: '6px 12px', cursor: 'pointer' }}>Adaugă Configurație</button>
      </form>  
    </div>
    </div>
  );
}

export default App;