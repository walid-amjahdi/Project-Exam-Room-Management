import React, { useState, useEffect } from 'react';

const TIME_SLOTS = [
  { label: '8h-10h', startTime: '08:00', endTime: '10:00' },
  { label: '10h-12h', startTime: '10:00', endTime: '12:00' },
  { label: '14h-16h', startTime: '14:00', endTime: '16:00' },
  { label: '16h-18h', startTime: '16:00', endTime: '18:00' },
];

function ReservationForm({ userEmail, setActiveTab }) {
  const [rooms, setRooms] = useState([]);
  const [roomId, setRoomId] = useState('');
  const [reservationDate, setReservationDate] = useState('');
  const [timeSlot, setTimeSlot] = useState(TIME_SLOTS[0].label);
  const [moduleName, setModuleName] = useState('');
  const [studentGroup, setStudentGroup] = useState('');
  const [academicYear, setAcademicYear] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);

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

  function handleSubmit(e) {
    e.preventDefault();
    setError('');
    setSuccess('');

    async function submitReservation() {
      try {
        setSubmitting(true);

        // Get teacher
        const teacherResponse = await fetch(`http://localhost:8080/teachers/email/${userEmail}`);
        if (!teacherResponse.ok) {
          setError('Teacher not found');
          return;
        }
        const teacher = await teacherResponse.json();

        // Find the selected time slot
        const selectedSlot = TIME_SLOTS.find(function(slot) {
          return slot.label === timeSlot;
        });

        // Create reservation
        const reservation = {
          reservationDate: reservationDate,
          startTime: selectedSlot.startTime,
          endTime: selectedSlot.endTime,
          status: 'PENDING',
          reason: `${moduleName} - ${studentGroup}`,
          teacher: {
            id: teacher.id
          },
          room: {
            id: roomId
          },
          makeupSession: {
            moduleName: moduleName,
            studentGroup: studentGroup,
            academicYear: academicYear
          }
        };

        const response = await fetch('http://localhost:8080/reservations', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(reservation)
        });

        if (response.ok) {
          setSuccess('Reservation requested successfully! Check "My Reservations" for status.');
          setRoomId('');
          setReservationDate('');
          setTimeSlot(TIME_SLOTS[0].label);
          setModuleName('');
          setStudentGroup('');
          setAcademicYear('');
          setTimeout(() => setActiveTab('myreservations'), 2000);
        } else {
          const errorData = await response.json();
          setError(errorData.message || 'Failed to create reservation. Time slot may be conflicted.');
        }
      } catch (err) {
        setError('Error submitting reservation');
        console.error(err);
      } finally {
        setSubmitting(false);
      }
    }

    submitReservation();
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
      <h2 className="mb-4">Request a Room</h2>
      {error && <div className="alert alert-danger">{error}</div>}
      {success && <div className="alert alert-success">{success}</div>}

      <form onSubmit={handleSubmit} className="shadow p-4 rounded bg-light">
        <div className="mb-3">
          <label className="form-label">Select Room</label>
          <select
            className="form-select"
            value={roomId}
            onChange={(e) => setRoomId(e.target.value)}
            required
          >
            <option value="">-- Choose a room --</option>
            {rooms.map(function(room) {
              return (
                <option key={room.id} value={room.id}>
                  {room.name} (Capacity: {room.capacity})
                </option>
              );
            })}
          </select>
        </div>

        <div className="mb-3">
          <label className="form-label">Reservation Date</label>
          <input
            type="date"
            className="form-control"
            value={reservationDate}
            onChange={(e) => setReservationDate(e.target.value)}
            min={new Date().toISOString().split('T')[0]}
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Time Slot</label>
          <select
            className="form-select"
            value={timeSlot}
            onChange={(e) => setTimeSlot(e.target.value)}
          >
            {TIME_SLOTS.map(function(slot) {
              return (
                <option key={slot.label} value={slot.label}>
                  {slot.label}
                </option>
              );
            })}
          </select>
        </div>

        <div className="mb-3">
          <label className="form-label">Module Name</label>
          <input
            type="text"
            className="form-control"
            placeholder="e.g., Java Programming"
            value={moduleName}
            onChange={(e) => setModuleName(e.target.value)}
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Student Group</label>
          <input
            type="text"
            className="form-control"
            placeholder="e.g., Group A"
            value={studentGroup}
            onChange={(e) => setStudentGroup(e.target.value)}
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Academic Year</label>
          <input
            type="text"
            className="form-control"
            placeholder="e.g., 2023-2024"
            value={academicYear}
            onChange={(e) => setAcademicYear(e.target.value)}
            required
          />
        </div>

        <button 
          type="submit" 
          className="btn btn-primary w-100"
          disabled={submitting}
        >
          {submitting ? 'Submitting...' : 'Request Reservation'}
        </button>
      </form>
    </div>
  );
}

export default ReservationForm;
