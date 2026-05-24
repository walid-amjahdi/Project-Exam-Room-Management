import React, { useState, useEffect } from 'react';

function RoomList() {
  const [rooms, setRooms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [searchText, setSearchText] = useState('');

  useEffect(() => {
    fetchRooms();
  }, []);

  function fetchRooms() {
    async function getRooms() {
      try {
        const response = await fetch('http://localhost:8080/rooms');
        if (response.ok) {
          const data = await response.json();
          setRooms(data);
        } else {
          setError('Failed to fetch rooms');
        }
      } catch (err) {
        setError('Error connecting to server');
        console.error(err);
      } finally {
        setLoading(false);
      }
    }

    getRooms();
  }

  if (loading) {
    return (
      <div className="mt-4">
        <div className="spinner-border" role="status">
          <span className="visually-hidden">Loading...</span>
        </div>
      </div>
    );
  }

  return (
    <div className="mt-4">
      <h2 className="mb-4">Available Rooms</h2>
      {error && <div className="alert alert-danger">{error}</div>}
      
      <div className="row mb-3">
        <div className="col-md-4">
          <input type="text" className="form-control"
            value={searchText}
            onChange={function(e) { setSearchText(e.target.value); }}
            placeholder="Search rooms by name or building" />
        </div>
      </div>

      {rooms.length === 0 ? (
        <div className="alert alert-info">No rooms available</div>
      ) : (
        <table className="table table-dark table-striped table-bordered table-hover">
          <thead>
            <tr>
              <th>Room Name</th>
              <th>Building</th>
              <th>Capacity</th>
              <th>Equipment</th>
            </tr>
          </thead>
          <tbody>
            {rooms.filter(function(room) {
              if (!searchText) return true;
              const q = searchText.toLowerCase();
              return (room.name && room.name.toLowerCase().includes(q)) ||
                     (room.building && room.building.toLowerCase().includes(q));
            }).map(function(room) {
              return (
                <tr key={room.id}>
                  <td>{room.name}</td>
                  <td>{room.building || 'N/A'}</td>
                  <td>{room.capacity || 'N/A'}</td>
                  <td>{room.equipment || 'None'}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default RoomList;
