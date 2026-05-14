import React, { useState, useEffect } from 'react';

const TIME_SLOTS = ['08:00-10:00', '10:00-12:00', '14:00-16:00', '16:00-18:00'];

function MyReservations({ userEmail }) {
  const [reservations, setReservations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [editingId, setEditingId] = useState(null);
  const [editDate, setEditDate] = useState('');
  const [editSlot, setEditSlot] = useState('');
  const [editModule, setEditModule] = useState('');
  const [editGroup, setEditGroup] = useState('');
  const [editYear, setEditYear] = useState('');

  useEffect(() => {
    async function getReservations() {
      try {
        const response = await fetch(`http://localhost:8080/reservations/teacher?email=${userEmail}`);
        if (response.ok) {
          const data = await response.json();
          setReservations(data);
        } else if (response.status === 404) {
          setReservations([]);
        } else {
          setError('Failed to fetch reservations');
        }
      } catch (err) {
        setError('Error connecting to server');
        console.error(err);
      } finally {
        setLoading(false);
      }
    }

    getReservations();
  }, [userEmail]);

  function startEdit(res) {
    setEditingId(res.id);
    setEditDate(res.reservationDate);
    setEditSlot(res.startTime.substring(0, 5) + '-' + res.endTime.substring(0, 5));
    setEditModule(res.makeupSession?.moduleName || '');
    setEditGroup(res.makeupSession?.studentGroup || '');
    setEditYear(res.makeupSession?.academicYear || '');
  }

  function cancelEdit() {
    setEditingId(null);
  }

  async function handleUpdate(id) {
    try {
      const parts = editSlot.split('-');
      const startTime = parts[0] + ':00';
      const endTime = parts[1] + ':00';
      const current = reservations.find(function(r) { return r.id === id; });
      const body = {
        reservationDate: editDate,
        startTime: startTime,
        endTime: endTime,
        teacher: current.teacher,
        room: current.room,
        status: current.status,
        reason: current.reason || '',
        makeupSession: {
          moduleName: editModule,
          studentGroup: editGroup,
          academicYear: editYear
        }
      };
      const response = await fetch('http://localhost:8080/reservations/' + id, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
      });
      if (response.ok) {
        const updated = await response.json();
        setReservations(reservations.map(function(r) {
          return r.id === id ? updated : r;
        }));
        setEditingId(null);
      } else {
        setError('Failed to update reservation');
      }
    } catch (err) {
      setError('Error updating reservation');
      console.error(err);
    }
  }

  async function handleDelete(id) {
    if (!window.confirm('Are you sure you want to delete this reservation?')) {
      return;
    }
    try {
      const response = await fetch('http://localhost:8080/reservations/' + id, {
        method: 'DELETE'
      });
      if (response.ok) {
        setReservations(reservations.filter(function(r) {
          return r.id !== id;
        }));
      } else {
        setError('Failed to delete reservation');
      }
    } catch (err) {
      setError('Error deleting reservation');
      console.error(err);
    }
  }

  function getStatusBadge(status) {
    if (status === 'PENDING') {
      return <span className="badge bg-warning">Pending</span>;
    } else if (status === 'CONFIRMED') {
      return <span className="badge bg-success">Confirmed</span>;
    } else if (status === 'REJECTED') {
      return <span className="badge bg-danger">Rejected</span>;
    } else {
      return <span className="badge bg-secondary">{status}</span>;
    }
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
      <h2 className="mb-4">My Reservations</h2>
      {error && <div className="alert alert-danger">{error}</div>}
      
      {reservations.length === 0 ? (
        <div className="alert alert-info">You have no reservations yet</div>
      ) : (
        <table className="table table-striped table-bordered table-hover">
          <thead>
            <tr>
              <th>Room</th>
              <th>Date</th>
              <th>Time Slot</th>
              <th>Module</th>
              <th>Group</th>
              <th>Academic Year</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {reservations.map(function(res) {
              const isEditing = editingId === res.id;
              if (isEditing) {
                return (
                  <tr key={res.id}>
                    <td>{res.room?.name || 'N/A'}</td>
                    <td>
                      <input type="date" className="form-control form-control-sm"
                        value={editDate}
                        onChange={function(e) { setEditDate(e.target.value); }} />
                    </td>
                    <td>
                      <select className="form-select form-select-sm"
                        value={editSlot}
                        onChange={function(e) { setEditSlot(e.target.value); }}>
                        {TIME_SLOTS.map(function(slot) {
                          return <option key={slot} value={slot}>{slot}</option>;
                        })}
                      </select>
                    </td>
                    <td>
                      <input type="text" className="form-control form-control-sm"
                        value={editModule}
                        onChange={function(e) { setEditModule(e.target.value); }} />
                    </td>
                    <td>
                      <input type="text" className="form-control form-control-sm"
                        value={editGroup}
                        onChange={function(e) { setEditGroup(e.target.value); }} />
                    </td>
                    <td>
                      <input type="text" className="form-control form-control-sm"
                        value={editYear}
                        onChange={function(e) { setEditYear(e.target.value); }} />
                    </td>
                    <td>{getStatusBadge(res.status)}</td>
                    <td>
                      <button className="btn btn-success btn-sm me-1"
                        onClick={function() { handleUpdate(res.id); }}>Save</button>
                      <button className="btn btn-secondary btn-sm"
                        onClick={cancelEdit}>Cancel</button>
                    </td>
                  </tr>
                );
              }
              return (
                <tr key={res.id}>
                  <td>{res.room?.name || 'N/A'}</td>
                  <td>{res.reservationDate}</td>
                  <td>{res.startTime}-{res.endTime}</td>
                  <td>{res.makeupSession?.moduleName || 'N/A'}</td>
                  <td>{res.makeupSession?.studentGroup || 'N/A'}</td>
                  <td>{res.makeupSession?.academicYear || 'N/A'}</td>
                  <td>{getStatusBadge(res.status)}</td>
                  <td>
                    {res.status === 'PENDING' && (
                      <>
                        <button className="btn btn-primary btn-sm me-1"
                          onClick={function() { startEdit(res); }}>Modifier</button>
                        <button className="btn btn-danger btn-sm"
                          onClick={function() { handleDelete(res.id); }}>Supprimer</button>
                      </>
                    )}
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default MyReservations;
