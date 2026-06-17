import { useState, useEffect} from 'react'

function App() {
  const [alias, setAlias] = useState('')
  const [isLoggedIn, setIsLoggedIn] = useState(false)
  const [meciActiv, setMeciActiv] = useState(null)
  const [clasament, setClasament] = useState([])
  const [mesajEroare, setMesajEroare] = useState('')

  const [primaPozitie, setPrimaPozitie] = useState(null)
  const [cartiIntoarseTemporar, setCartiIntoarseTemporar] = useState([])
  const [blocat, setBlocat] = useState(false)

  const API_BASE = 'http://localhost:8080/api';

  useEffect(() => {
    fetch(`${API_BASE}/clasament`)
      .then(res => res.json())
      .then(data => setClasament(data))
      .catch(err => console.error('Error fetching clasament:', err));
  }, []);

  useEffect (() => {
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
    }
    catch (err) {
      setMesajEroare(err.message);
    }
  };

  const handleCardClick = async (index) => {
    if (blocat || !meciActiv) return;

    if (meciActiv.descoperite[index] == 'yes' || primaPozitie === index) return;

    if (primaPozitie === null) {
      setPrimaPozitie(index);
      setCartiIntoarseTemporar([index]);
    }
    else {
      const pozitie1 = primaPozitie;
      const pozitie2 = index;

      setCartiIntoarseTemporar([pozitie1, pozitie2]);
      setBlocat(true);

      try {
        const response = await fetch(`${API_BASE}/jocuri/${meciActiv.idJoc}/mutare`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ pozitie1 : pozitie1, pozitie2 : pozitie2 })
        });
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to make move');
      }

      const updatedMeci = await response.json();

      const estePerecheCorecta = updatedMeci.configuratie[pozitie1] === updatedMeci.configuratie[pozitie2];

      if (estePerecheCorecta) {
        setMeciActiv(updatedMeci);
        setPrimaPozitie(null);
        setCartiIntoarseTemporar([]);
        setBlocat(false);
      }
      else {
        setTimeout (() => {
          setMeciActiv(updatedMeci);
          setPrimaPozitie(null);
          setCartiIntoarseTemporar([]);
          setBlocat(false);
        }, 1500);
      }
    }
    catch (err) {
      setMesajEroare(err.message);
      setBlocat(false);
      setPrimaPozitie(null);
      setCartiIntoarseTemporar([]);
    }
  }
};

  const handleJocNou = () => {
    setMeciActiv(null);
    setMesajEroare('');
    setIsLoggedIn(false);
  };


  return (
        <div style={{ padding: '20px', fontFamily: 'sans-serif', maxWidth: '800px', margin: '0 auto' }}>
          <h1>Joc de Memorie</h1>
          <hr />

          {mesajEroare && <p style={{ color: 'red' }}>{mesajEroare}</p>}

          {!isLoggedIn ? (
            <div>
              <h3> Introdu alias pentru a incepe jocul: </h3>
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
              <div style = {{display: 'flex', justifyContent: 'space-between', aligntItems: 'center' }}>
                <h3> Jucator curent : {alias} </h3>
                <h4> Scor: {meciActiv?.puncte || 0} | Incercari: {meciActiv?.incercari || 10}</h4>
              </div>
              <div style={{ display: 'grid', gridTemplateColumns: 'repeat(4, 120px)', gap: '10px', marginTop: '20px' }}>
                {meciActiv?.configuratie.map((cuvant, index) => {
                  const esteVizibil = meciActiv.descoperite[index] === 'yes' || cartiIntoarseTemporar.includes(index);
                  return (
                    <button
                    key={index}
                    onClick={() => handleCardClick(index)}
                    style={{
                      height: '80px',
                      fontSize: '16px',
                      fontWeight: 'bold',
                      backgroundColor: esteVizibil ? '#e9ecef' : '#007bff',
                      color: esteVizibil ? '#333' : '#fff',
                      border: '2px solid #ccc',
                      borderRadius: '5px',
                      cursor: esteVizibil ? 'default' : 'pointer',
                      transition: 'background-color 0.3s'
                    }}>
                      {esteVizibil ? cuvant : '?'}
                    </button>
                  );
                })}
                </div>
                {(meciActiv && (meciActiv.incercari === 10 || !meciActiv.descoperite.includes('no'))) && (
                  <div style={{ backgroundColor: '#d4edda', color: '#155724', padding: '15px', borderRadius: '5px', margin: '20px 0' }}>
                    <h2>Joc Finalizat!</h2>
                    <p>Ai obținut un total de <strong>{meciActiv.puncte}</strong> puncte.</p>
                    <button onClick={handleJocNou} style={{ padding: '10px 20px', fontWeight: 'bold', cursor: 'pointer' }}>
                      Înapoi la Ecranul Principal
                    </button>
                  </div>
                 )}
                  </div>
                )}
<div style={{ marginTop: '40px' }}>
        <h2>Clasament General</h2>
        <table border="1" cellPadding="10" style={{ width: '100%', textAlign: 'left', borderCollapse: 'collapse', marginTop: '10px' }}>
          <thead>
            <tr style={{ backgroundColor: '#f2f2f2' }}>
              <th>Loc</th>
              <th>Jucator</th>
              <th>Puncte</th>
              <th>Durata (Secunde)</th>
            </tr>
          </thead>
          <tbody>
            {clasament.length === 0 ? (
              <tr><td colSpan="4" style={{ textAlign: 'center' }}>Nu exista jocuri in clasament</td></tr>
            ) : (
              clasament.map((joc, index) => (
                <tr key={joc.id || index}>
                  <td><strong>{index + 1}</strong></td>
                  <td>{joc.alias}</td>
                  <td>{joc.puncte}</td>
                  <td>{joc.durataSecunde || joc.durata}s</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  )
}
export default App
