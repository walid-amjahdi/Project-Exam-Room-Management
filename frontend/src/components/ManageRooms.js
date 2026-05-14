import React, { useState, useEffect } from 'react';

function ManageRooms() {
  const [rooms, setRooms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  // Form state - individual variables
  const [roomId, setRoomId] = useState('');
  const [roomName, setRoomName] = useState('');
  const [roomBuilding, setRoomBuilding] = useState('');
  const [roomCapacity, setRoomCapacity] = useState('');
  const [roomEquipment, setRoomEquipment] = useState('');

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

  function handleShowModal(room) {
    if (room) {
      setRoomId(room.id);
      setRoomName(room.name);
      setRoomBuilding(room.building || '');
      setRoomCapacity(room.capacity || '');
      setRoomEquipment(room.equipment || '');
      setIsEditing(true);
    } else {
      setRoomId('');
      setRoomName('');
      setRoomBuilding('');
      setRoomCapacity('');
      setRoomEquipment('');
      setIsEditing(false);
    }
    setShowModal(true);
  }

  function handleCloseModal() {
    setShowModal(false);
    setRoomId('');
    setRoomName('');
    setRoomBuilding('');
    setRoomCapacity('');
    setRoomEquipment('');
  }

  function handleSave(e) {
    e.preventDefault();
    setSubmitting(true);
    setError('');
    setSuccess('');

    async function saveRoom() {
      try {
        const method = isEditing ? 'PUT' : 'POST';
        const url = isEditing 
          ? `http://localhost:8080/rooms/${roomId}` 
          : 'http://localhost:8080/rooms';

        const roomData = {
          id: roomId || undefined,
          name: roomName,
          building: roomBuilding,
          capacity: roomCapacity || undefined,
          equipment: roomEquipment
        };

        const response = await fetch(url, {
          method: method,
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(roomData)
        });

        if (response.ok) {
          setSuccess(isEditing ? 'Room updated successfully!' : 'Room added successfully!');
          handleCloseModal();
          fetchRooms();
          setTimeout(() => setSuccess(''), 3000);
        } else {
          setError('Failed to save room');
        }
      } catch (err) {
        setError('Error saving room');
        console.error(err);
      } finally {
        setSubmitting(false);
      }
    }

    saveRoom();
  }

  function handleDelete(roomIdToDelete) {
    const confirmed = window.confirm('Are you sure you want to delete this room?');
    if (!confirmed) return;

    async function deleteRoom() {
      try {
        const response = await fetch(`http://localhost:8080/rooms/${roomIdToDelete}`, {
          method: 'DELETE'
        });

        if (response.ok) {
          setSuccess('Room deleted successfully!');
          fetchRooms();
          setTimeout(() => setSuccess(''), 3000);
        } else {
          setError('Failed to delete room');
        }
      } catch (err) {
        setError('Error deleting room');
        console.error(err);
      }
    }

    deleteRoom();
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
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Manage Rooms</h2>
        <button 
          className="btn btn-primary"
          onClick={() => handleShowModal(null)}
        >
          + Add New Room
        </button>
      </div>

      {error && <div className="alert alert-danger">{error}</div>}
      {success && <div className="alert alert-success">{success}</div>}
      
      {rooms.length === 0 ? (
        <div className="alert alert-info">No rooms available. Add one to get started.</div>
      ) : (
        <table className="table table-dark table-striped table-bordered table-hover">
          <thead>
            <tr>
              <th>Name</th>
              <th>Building</th>
              <th>Capacity</th>
              <th>Equipment</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {rooms.map(function(room) {
              return (
                <tr key={room.id}>
                  <td>{room.name}</td>
                  <td>{room.building || 'N/A'}</td>
                  <td>{room.capacity || 'N/A'}</td>
                  <td>{room.equipment || 'None'}</td>
                  <td>
                    <button 
                      className="btn btn-warning btn-sm me-2"
                      onClick={() => handleShowModal(room)}
                    >
                      Edit
                    </button>
                    <button 
                      className="btn btn-danger btn-sm"
                      onClick={() => handleDelete(room.id)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      )}

      {/* Modal */}
      {showModal && (
        <div className="modal d-block" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}>
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">
                  {isEditing ? 'Edit Room' : 'Add New Room'}
                </h5>
                <button 
                  type="button" 
                  className="btn-close"
                  onClick={handleCloseModal}
                ></button>
              </div>
              <div className="modal-body">
                <form onSubmit={handleSave}>
                  <div className="mb-3">
                    <label className="form-label">Room Name</label>
                    <input
                      type="text"
                      className="form-control"
                      value={roomName}
                      onChange={(e) => setRoomName(e.target.value)}
                      required
                    />
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Building</label>
                    <input
                      type="text"
                      className="form-control"
                      value={roomBuilding}
                      onChange={(e) => setRoomBuilding(e.target.value)}
                    />
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Capacity</label>
                    <input
                      type="number"
                      className="form-control"
                      value={roomCapacity}
                      onChange={(e) => setRoomCapacity(e.target.value)}
                    />
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Equipment</label>
                    <input
                      type="text"
                      className="form-control"
                      placeholder="e.g., Projector, Whiteboard"
                      value={roomEquipment}
                      onChange={(e) => setRoomEquipment(e.target.value)}
                    />
                  </div>

                  <button 
                    type="submit" 
                    className="btn btn-primary w-100"
                    disabled={submitting}
                  >
                    {submitting ? 'Saving...' : 'Save'}
                  </button>
                </form>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default ManageRooms;
