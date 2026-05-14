import React, { useState, useEffect } from 'react';

function ValidationQueue() {
  const [reservations, setReservations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [filterDate, setFilterDate] = useState('');
  const [filterRoom, setFilterRoom] = useState('');

  useEffect(() => {
    fetchPendingReservations();
  }, []);

  function fetchPendingReservations() {
    async function getPending() {
      try {
        const response = await fetch('http://localhost:8080/reservations/pending');
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

    getPending();
  }

  function handleApprove(reservationId) {
    async function approveReservation() {
      try {
        const response = await fetch(`http://localhost:8080/reservations/${reservationId}/approve`, {
          method: 'PATCH',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ status: 'CONFIRMED' })
        });

        if (response.ok) {
          setSuccess('Reservation approved!');
          fetchPendingReservations();
          setTimeout(() => setSuccess(''), 3000);
        } else {
          setError('Failed to approve reservation');
        }
      } catch (err) {
        setError('Error approving reservation');
        console.error(err);
      }
    }

    approveReservation();
  }

  function handleReject(reservationId) {
    async function rejectReservation() {
      try {
        const response = await fetch(`http://localhost:8080/reservations/${reservationId}/reject`, {
          method: 'PATCH',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ status: 'REJECTED' })
        });

        if (response.ok) {
          setSuccess('Reservation rejected!');
          fetchPendingReservations();
          setTimeout(() => setSuccess(''), 3000);
        } else {
          setError('Failed to reject reservation');
        }
      } catch (err) {
        setError('Error rejecting reservation');
        console.error(err);
      }
    }

    rejectReservation();
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
      <h2 className="mb-4">Validation Queue - Pending Reservations</h2>
      {error && <div className="alert alert-danger">{error}</div>}
      {success && <div className="alert alert-success">{success}</div>}
      
      <div className="row mb-3">
        <div className="col-md-4">
          <input type="date" className="form-control"
            value={filterDate}
            onChange={function(e) { setFilterDate(e.target.value); }}
            placeholder="Filter by date" />
        </div>
        <div className="col-md-4">
          <input type="text" className="form-control"
            value={filterRoom}
            onChange={function(e) { setFilterRoom(e.target.value); }}
            placeholder="Filter by room name" />
        </div>
      </div>

      {reservations.length === 0 ? (
        <div className="alert alert-info">No pending reservations</div>
      ) : (
        <table className="table table-striped table-bordered table-hover">
          <thead>
            <tr>
              <th>Teacher Email</th>
              <th>Room</th>
              <th>Date</th>
              <th>Time</th>
              <th>Module</th>
              <th>Group</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {reservations.filter(function(res) {
              if (filterDate && res.reservationDate !== filterDate) return false;
              if (filterRoom && res.room && !res.room.name.toLowerCase().includes(filterRoom.toLowerCase())) return false;
              return true;
            }).map(function(res) {
              return (
                <tr key={res.id}>
                  <td>{res.teacher?.email || 'N/A'}</td>
                  <td>{res.room?.name || 'N/A'}</td>
                  <td>{res.reservationDate}</td>
                  <td>{res.startTime}-{res.endTime}</td>
                  <td>{res.makeupSession?.moduleName || 'N/A'}</td>
                  <td>{res.makeupSession?.studentGroup || 'N/A'}</td>
                  <td>
                    <button 
                      className="btn btn-success btn-sm me-2"
                      onClick={() => handleApprove(res.id)}
                    >
                      Approve
                    </button>
                    <button 
                      className="btn btn-danger btn-sm"
                      onClick={() => handleReject(res.id)}
                    >
                      Reject
                    </button>
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

export default ValidationQueue;
