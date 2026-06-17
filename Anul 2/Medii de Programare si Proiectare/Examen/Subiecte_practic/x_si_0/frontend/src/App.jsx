import { useState, useEffect } from 'react';

export default function App() {
  const [alias, setAlias] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [meciActiv, setMeciActiv] = useState(null); 
  const [clasament, setClasament] = useState([]);
  const [mesajEroare, setMesajEroare] = useState('');

  const API_BASE = 'http://localhost:8080/api';

  useEffect(() => {
    fetch(`${API_BASE}/clasament`)
      .then(res => res.json())
      .then(data => setClasament(data))
      .catch(err => console.error("Eroare la preluarea clasamentului: ", err));
  }, []);

  useEffect(() => {
    const eventSource = new EventSource(`${API_BASE}/clasament/stream`);

    eventSource.onmessage = (event) => {
      console.log("Update clasament primit prin SSE!");
      const clasamentNou = JSON.parse(event.data);
      setClasament(clasamentNou);
    };

    eventSource.onerror = (err) => {
      console.error("SSE Error: ", err);
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
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ alias: alias })
      });

      if (!response.ok) {
        const errData = await response.json();
        throw new Error(errData.error || "Eroare la pornirea jocului");
      }

      const meci = await response.json();
      setMeciActiv(meci);
      setIsLoggedIn(true);
      setMesajEroare('');
    } catch (err) {
      setMesajEroare(err.message);
    }
  };

  const handleMutare = async (linie, coloana) => {
    console.log("Trimitem:", linie, coloana);
    if (!meciActiv || meciActiv.status !== "IN_PROGRESS") return;
    if (meciActiv.tabla[linie * 3 + coloana] !== ' ') return; 

    try {
      const response = await fetch(`${API_BASE}/jocuri/${meciActiv.id}/mutare`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ linie: linie, coloana: coloana })
      });

      if (!response.ok) {
        const errData = await response.json();
        throw new Error(errData.error);
      }

      const meciActualizat = await response.json();
      setMeciActiv(meciActualizat);
    } catch (err) {
      alert(err.message);
    }
  };

  const handleJocNou = () => {
    setMeciActiv(null);
    setIsLoggedIn(false);
  };

  return (
    <div style={{ padding: '20px', fontFamily: 'sans-serif', maxWidth: '800px', margin: '0 auto' }}>
      <h1>X și 0 - Campionat MPP</h1>
      <hr />

      {mesajEroare && <p style={{ color: 'red' }}>{mesajEroare}</p>}

      {!isLoggedIn ? (
        <div>
          <h3>Introdu alias pentru a începe jocul</h3>
          <form onSubmit={handleStartJoc}>
            <input 
              type="text" 
              placeholder="Alias-ul tău..." 
              value={alias} 
              onChange={(e) => setAlias(e.target.value)}
              style={{ padding: '8px', marginRight: '10px' }}
            />
            <button type="submit" style={{ padding: '8px 15px' }}>Start Joc</button>
          </form>
        </div>
      ) : (
        <div>
          <h3>Jucător curent: {alias}</h3>
          <p>Status Meci: <strong>{meciActiv?.status}</strong></p>

          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 80px)', gap: '5px', margin: '20px 0' }}>
            {meciActiv?.tabla.map((valoare, index) => {
              const linie = Math.floor(index / 3);
              const coloana = index % 3;

              return (
                <button
                  key={index}
                  onClick={() => handleMutare(linie, coloana)}
                  disabled={meciActiv.status !== "IN_PROGRESS" || valoare !== ' '}
                  style={{
                    width: '80px',
                    height: '80px',
                    fontSize: '24px',
                    fontWeight: 'bold',
                    cursor: valoare === ' ' && meciActiv.status === "IN_PROGRESS" ? 'pointer' : 'default'
                  }}
                >
                  {valoare}
                </button>
              );
            })}
          </div>

          {meciActiv?.status !== "IN_PROGRESS" && (
            <div>
              <p style={{ color: 'blue', fontWeight: 'bold' }}>Jocul s-a terminat! Clasamentul s-a salvat.</p>
              <button onClick={handleJocNou} style={{ padding: '10px 20px' }}>Joacă din nou</button>
            </div>
          )}
        </div>
      )}

      <hr style={{ margin: '30px 0' }} />

      <h2>Clasament General </h2>
      <table border="1" cellPadding="8" style={{ width: '100%', borderCollapse: 'collapse', marginTop: '10px' }}>
        <thead>
          <tr style={{ backgroundColor: '#f2f2f2' }}>
            <th>Loc</th>
            <th>Jucător</th>
            <th>Puncte</th>
            <th>Durată (secunde)</th>
          </tr>
        </thead>
        <tbody>
          {clasament.length === 0 ? (
            <tr><td colSpan="4" style={{ textAlign: 'center' }}>Nu există jocuri finalizate încă.</td></tr>
          ) : (
            clasament.map((joc, index) => (
              <tr key={joc.id || index}>
                <td>{index + 1}</td>
                <td>{joc.jucator?.alias}</td>
                <td>{joc.puncte}</td>
                <td>{joc.durataSecunde}s</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}