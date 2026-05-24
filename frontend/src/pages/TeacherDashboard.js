import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import RoomList from '../components/RoomList';
import ReservationForm from '../components/ReservationForm';
import MyReservations from '../components/MyReservations';

function TeacherDashboard({ userEmail, handleLogout }) {
  const [activeTab, setActiveTab] = useState('rooms');
  const navigate = useNavigate();

  function handleLogoutClick() {
    handleLogout();
    navigate('/login');
  }

  return (
    <div>
      {/* Navigation Navbar */}
      <nav className="navbar navbar-dark bg-dark mb-4">
        <div className="container-fluid">
          <span className="navbar-brand mb-0 h1">Room Reservation - Teacher</span>
          
          <div className="d-flex align-items-center gap-3">
            <ul className="nav">
              <li className="nav-item">
                <button 
                  className={`nav-link ${activeTab === 'rooms' ? 'active' : ''}`}
                  onClick={() => setActiveTab('rooms')}
                  style={{ background: 'none', border: 'none', cursor: 'pointer' }}
                >
                  Available Rooms
                </button>
              </li>
              <li className="nav-item">
                <button 
                  className={`nav-link ${activeTab === 'request' ? 'active' : ''}`}
                  onClick={() => setActiveTab('request')}
                  style={{ background: 'none', border: 'none', cursor: 'pointer' }}
                >
                  Request Room
                </button>
              </li>
              <li className="nav-item">
                <button 
                  className={`nav-link ${activeTab === 'myreservations' ? 'active' : ''}`}
                  onClick={() => setActiveTab('myreservations')}
                  style={{ background: 'none', border: 'none', cursor: 'pointer' }}
                >
                  My Reservations
                </button>
              </li>
            </ul>

            <span className="badge bg-primary me-2">Teacher</span>
            <span className="text-light me-3">{userEmail}</span>
            <button 
              className="btn btn-outline-light"
              onClick={handleLogoutClick}
            >
              Logout
            </button>
          </div>
        </div>
      </nav>

      {/* Main Content */}
      <div className="container">
        {activeTab === 'rooms' && <RoomList />}
        {activeTab === 'request' && <ReservationForm userEmail={userEmail} setActiveTab={setActiveTab} />}
        {activeTab === 'myreservations' && <MyReservations userEmail={userEmail} />}
      </div>
    </div>
  );
}

export default TeacherDashboard;
