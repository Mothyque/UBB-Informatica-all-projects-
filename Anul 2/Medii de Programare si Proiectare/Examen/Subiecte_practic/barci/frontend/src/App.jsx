import { useState, useEffect } from 'react'

function App() {
  const [alias, setAlias] = useState('')
  const [isLoggedIn, setIsLoggedIn] = useState(false)
  const [meciActiv, setMeciActiv] = useState(null)
  const [clasament, setClasament] = useState([])
  const [mesajEroare, setMesajEroare] = useState('')

  const [searchAlias, setSearchAlias] = useState('');
  const [istoricJucator, setIstoricJucator] = useState([]);
  const [searchPerformed, setSearchPerformed] = useState(false);

  const [newPozitie, setNewPozitie] = useState('');

  const API_BASE = 'http://localhost:8080/api'

  useEffect(() => {
    fetch(`${API_BASE}/clasament`)
      .then((response) => response.json())
      .then((data) => setClasament(data))
      .catch((error) => console.error('Eroare fetching clasament:', error))
  }, []);

  useEffect(() => {
    const eventSource = new EventSource(`${API_BASE}/clasament/stream`);
    eventSource.onmessage = (event) => {
      const clasamentUpdated = JSON.parse(event.data);
      setClasament(clasamentUpdated);
    };
    
    return () => {
      eventSource.close();
    };
  }, []);

  const handleStartJoc = async (e) => {
    e.preventDefault();
    if (!alias.trim()) return;
    
    try {
      const response = await fetch(`${API_BASE}/jocuri/start`, {
        method: 'POST',
        headers: {'Content-Type' : 'application/json'},
        body: JSON.stringify({ alias : alias })
      });
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Eroare la start joc');
      }
      const meci = await response.json();
      setMeciActiv(meci);
      setIsLoggedIn(true);
      setMesajEroare('');
    } catch (error) {
      console.error('Eroare la start joc:', error);
      setMesajEroare(error.message);
    }
    };

    const handleClick = async (index) => {
      if (!meciActiv) return;
      const linie = Math.floor(index / 5);
      const coloana = index % 5;
      try {
        const response = await fetch(`${API_BASE}/jocuri/${meciActiv.id}/alegere`, {
          method: 'POST',
          headers: {'Content-Type' : 'application/json'},
          body: JSON.stringify({ linie : linie, coloana : coloana })
        });
        if (!response.ok) {
          const errorData = await response.json();
          throw new Error(errorData.message || 'Eroare la alegere');
        }
        const meciUpdated = await response.json();
        setMeciActiv(meciUpdated);
      }
      catch (error) {
        setMesajEroare(error.message);
      }
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

    const handleJocNou = () => {
      setMeciActiv(null);
      setIsLoggedIn(false);
      setAlias('');
      setMesajEroare('');
    }

    const handleAddPozitie = async (e) => {
      e.preventDefault();
      if (!newPozitie.trim()) return;

      try {
        const response = await fetch(`${API_BASE}/barci`, {
          method: 'POST',
          headers: {'Content-Type' : 'application/json'},
          body: JSON.stringify({ pozitii: newPozitie.trim() })
        });
        if (!response.ok) {
          const errorData = await response.json();
          throw new Error(errorData.message || 'Eroare la adaugare pozitie');
        }
        setNewPozitie('');
      }
      catch (error) {
        setMesajEroare(error.message);
      }
    };

    const isWon = meciActiv?.ghicite === 3;
    const isLost = meciActiv?.ghicite < 3 && meciActiv?.nrIncercari >= 3;
    const isGameOver = isWon || isLost;

    const pozitiiBarca = meciActiv && meciActiv.pozitiiBarca ? meciActiv.pozitiiBarca.split(';').filter(Boolean).map(p => {
      const [l, c] = p.split(',').map(Number);
      return l * 5 + c;
    }) : [];
return (
      <div style={{ padding: '20px', fontFamily: 'sans-serif', maxWidth: '800px', margin: '0 auto' }}>
        <h1>Barca Joc</h1>
        <hr />

        {mesajEroare && <p style={{ color: 'red' }}>{mesajEroare}</p>}
        
        {!isLoggedIn ? (
          <div> 
            <h3> Introdu alias: </h3>
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
        ): (
          <div>
            <h3> Meci activ: </h3>
            <p>{meciActiv?.alias}</p>
            <p>Nr incercari: {meciActiv?.nrIncercari} / 3</p>
            <p>Ghicite: {meciActiv?.ghicite} / 3</p>
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(5, 120px)', gap: '10px', marginTop: '20px' }}>
              {meciActiv?.tabla.map((val, index) => {
                const isBoatPosition = pozitiiBarca.includes(index);
                return (
                  <button 
                    key={index}
                    onClick={() => handleClick(index)}
                    disabled={isGameOver}
                    style={{
                      width: '120px',
                      height: '120px',
                    }}
                    >
                      {(isBoatPosition && isGameOver) ? 'B' : (val === " " ? ' ' : val)}
                    </button>
                );
              })}
              {isGameOver && (
                <div style={{ gridColumn: 'span 5', textAlign: 'center', marginTop: '20px' }}>
                  {isWon ? (
                    <p style={{ color: 'green', fontWeight: 'bold' }}>Felicitari! Ai castigat!</p>
                  ) : (
                    <div>
                      <p style={{ color: 'red', fontWeight: 'bold' }}>Ai pierdut! Ai ghicit {meciActiv?.ghicite} din 3! Mai incearca!</p>
                    </div>
                  )}
                  <button onClick={handleJocNou}>Joc Nou</button>
                </div>
              )}
            </div>
          </div>
        )}
        <h2> Clasament </h2>
        <table border="1">
          <thead>
            <tr>
              <th>Loc</th>
              <th>Jucator</th>
              <th>Ora</th>
              <th>Punctaj</th>
              <th>Ghicite</th>
            </tr>
          </thead>
          <tbody>
            {clasament.length === 0 ? (
              <tr>
                <td colSpan="5">Nu exista jucatori in clasament</td>
              </tr>
            ) : (
              clasament.map((joc, index) => (
                <tr key = {joc.id || index}>
                  <td> <strong> {index + 1} </strong> </td>
                  <td> {joc.alias} </td>
                  <td> {joc.dataOra} </td>
                  <td> {joc.puncte} </td>
                  <td> {joc.ghicite} </td>
                </tr>
              ))
            )}
          </tbody>
        </table>

        <div>
        <h2> Cauta jucator cu minim 1 ghicit </h2>
        <form onSubmit={handleSearchHistory}>
          <input
            type="text"
            value={searchAlias}
            onChange={(e) => setSearchAlias(e.target.value)}
            placeholder="Alias"
            required
          />
          <button type="submit">Cauta</button>
        </form>
        {searchPerformed && (
          <div>
          {istoricJucator.length === 0 ? (
            <p>Nu exista jucatori cu aliasul {searchAlias} care au ghicit minim 1 data</p>
          ) : (
            <table border="1">
              <thead>
                <tr>
                  <th>Meci</th>
                  <th>Punctaj</th>
                  <th>Pozitii propuse</th>
                  <th>Pozitiile barcii</th>
                </tr>
              </thead>
              <tbody>
                {istoricJucator.map((joc, index) => (
                  <tr key={index}>
                    <td>{index + 1}</td>
                    <td>{joc.puncte}</td>
                    <td>{joc.pozitii}</td>
                    <td>{joc.pozitiiBarca}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
          </div>
        )}
        </div>

        <div>
          <h2> Adauga pozitie barca (format: (lini1,coloana1);(lini2,coloana2);(lini3,coloana3)) </h2>
          <form onSubmit={handleAddPozitie}>
            <input 
              type="text"
              value={newPozitie}
              onChange={(e) => setNewPozitie(e.target.value)}
              placeholder="l1,c1;l2,c2;l3,c3"
              required
            />
            <button type="submit">Adauga Pozitie</button>
          </form>
        </div>
      </div>

);
}
export default App
