import {useState, useEffect} from 'react';

const MatchForm = ({ onAdd, matchToEdit, onCancelEdit }) => {
    const initialFormState = {
        teamA: '',
        teamB: '',
        matchType: '',
        ticketPrice: '',
        totalSeats: '',
        availableSeats: ''
    };

    const [formData, setFormData] = useState(initialFormState);

    useEffect(() => {
        if (matchToEdit) {
            setFormData(matchToEdit);
        }
        else {
            setFormData(initialFormState);
        }
    }, [matchToEdit]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };
    
    const handleSubmit = (e) => {
        e.preventDefault();

        const newMatch = {
            ...formData,
            ticketPrice: parseFloat(formData.ticketPrice),
            totalSeats: parseInt(formData.totalSeats, 10),
            availableSeats: parseInt(formData.availableSeats, 10)
        };

        onAdd(newMatch);
        if (!matchToEdit) {
            setFormData(initialFormState);
        }
    };

    return (
        <div style={{ marginBottom: '20px', padding: '15px', border: `1px solid ${matchToEdit ? '#ff9800' : '#4CAF50'}`, borderRadius: '8px', backgroundColor: matchToEdit ? '#fff8e1' : '#f9fff9' }}>
            <h3>{matchToEdit ? "Edit Match" : "Add New Match"}</h3>
            <form onSubmit={handleSubmit} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
                <input required type="text" name="teamA" placeholder="Team A" value={formData.teamA} onChange={handleChange} style={{ padding: '8px' }}/>
                <input required type="text" name="teamB" placeholder="Team B" value={formData.teamB} onChange={handleChange} style={{ padding: '8px' }}/>
                <input required type="text" name="matchType" placeholder="Match Type" value={formData.matchType} onChange={handleChange} style={{ padding: '8px' }}/>
                <input required type="number" step="0.01" name="ticketPrice" placeholder="Ticket Price" value={formData.ticketPrice} onChange={handleChange} style={{ padding: '8px' }}/>
                <input required type="number" name="totalSeats" placeholder="Total Seats" value={formData.totalSeats} onChange={handleChange} style={{ padding: '8px' }}/>
                <input required type="number" name="availableSeats" placeholder="Available Seats" value={formData.availableSeats} onChange={handleChange} style={{ padding: '8px' }}/>
                
                <div style={{ gridColumn: 'span 2', display: 'flex', gap: '10px' }}>
                    <button type="submit" style={{ flex: 1, padding: '10px', backgroundColor: matchToEdit ? '#ff9800' : '#4CAF50', color: 'white', border: 'none', cursor: 'pointer', fontWeight: 'bold' }}>
                        {matchToEdit ? "Update Match" : "Add Match"}
                    </button>
                    {matchToEdit && (
                        <button type="button" onClick={onCancelEdit} style={{ flex: 1, padding: '10px', backgroundColor: '#9e9e9e', color: 'white', border: 'none', cursor: 'pointer', fontWeight: 'bold' }}>
                            Cancel
                        </button>
                    )}
                </div>
            </form>
        </div>
    );
};

export default MatchForm;