const MatchList = ({ matches, onDelete, onEdit }) => {
    if (!matches || matches.length === 0) {
        return <p>No matches found.</p>;
    }

    return (
        <div>
            <h2>Match Schedule</h2>
            <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
                <thead>
                    <tr style={{ backgroundColor: '#f2f2f2' }}>
                        <th style={{ padding: '12px', borderBottom: '1px solid #ddd' }}>Teams</th>
                        <th style={{ padding: '12px', borderBottom: '1px solid #ddd' }}>Type</th>
                        <th style={{ padding: '12px', borderBottom: '1px solid #ddd' }}>Price</th>
                        <th style={{ padding: '12px', borderBottom: '1px solid #ddd' }}>Available Seats</th>
                        <th style={{ padding: '12px', borderBottom: '1px solid #ddd' }}>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {matches.map((match) => (
                        <tr key={match.id}>
                            <td style={{ padding: '12px', borderBottom: '1px solid #ddd' }}>
                                <strong>{match.teamA}</strong> vs <strong>{match.teamB}</strong>
                            </td>
                            <td style={{ padding: '12px', borderBottom: '1px solid #ddd' }}>{match.matchType}</td>
                            <td style={{ padding: '12px', borderBottom: '1px solid #ddd' }}>${match.ticketPrice}</td>
                            <td style={{ padding: '12px', borderBottom: '1px solid #ddd' }}>
                                {match.availableSeats <= 0 ? (
                                    <span style={{ color: 'red', fontWeight: 'bold' }}>Sold Out</span>
                                ) : (
                                    `${match.availableSeats} / ${match.totalSeats}`
                                )}
                            </td>
                            <td style={{ padding: '12px', borderBottom: '1px solid #ddd' }}>
                                <button 
                                    onClick={() => onEdit(match)}
                                    style={{ padding: '5px 10px', marginRight: '5px', backgroundColor: '#2196F3', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
                                >
                                    Edit
                                </button>
                                <button 
                                    onClick={() => onDelete(match.id)}
                                    style={{ padding: '5px 10px', backgroundColor: '#ff4d4d', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
                                >
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default MatchList;