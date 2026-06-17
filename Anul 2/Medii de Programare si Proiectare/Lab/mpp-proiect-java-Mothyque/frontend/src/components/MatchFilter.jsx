import {useState} from 'react';

const MatchFilter = ({onFilter}) => {
    const [filterType, setFilterType] = useState('');

    const handleSearch = (e) => {
        e.preventDefault();
        onFilter(filterType);
    };

    const handleClear = () => {
        setFilterType('');
        onFilter('');
    };

    return (
        <div style={{ marginBottom: '20px', padding: '15px', border: '1px solid #ccc', borderRadius: '8px' }}>
            <h3>Filter Matches</h3>
            <form onSubmit={handleSearch} style={{ display: 'flex', gap: '10px' }}>
                <input 
                    type="text" 
                    placeholder="Enter match type (e.g., Final, Group Stage)" 
                    value={filterType}
                    onChange={(e) => setFilterType(e.target.value)}
                    style={{ padding: '8px', width: '250px' }}
                />
                <button type="submit" style={{ padding: '8px 16px', cursor: 'pointer' }}>Search</button>
                <button type="button" onClick={handleClear} style={{ padding: '8px 16px', cursor: 'pointer' }}>Clear Filter</button>
            </form>
        </div>
    );
};

export default MatchFilter;