import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ManageRooms from '../components/ManageRooms';
import ManageTeachers from '../components/ManageTeachers';
import ManageAdmins from '../components/ManageAdmins';
import ValidationQueue from '../components/ValidationQueue';

function AdminDashboard({ userEmail, handleLogout }) {
  const [activeTab, setActiveTab] = useState('validations');
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
          <span className="navbar-brand mb-0 h1">Room Reservation - Admin</span>
          
          <div className="d-flex align-items-center gap-3">
            <ul className="nav">
              <li className="nav-item">
                <button 
                  className={`nav-link ${activeTab === 'validations' ? 'active' : ''}`}
                  onClick={() => setActiveTab('validations')}
                  style={{ background: 'none', border: 'none', cursor: 'pointer' }}
                >
                  Validation Queue
                </button>
              </li>
              <li className="nav-item">
                <button 
                  className={`nav-link ${activeTab === 'rooms' ? 'active' : ''}`}
                  onClick={() => setActiveTab('rooms')}
                  style={{ background: 'none', border: 'none', cursor: 'pointer' }}
                >
                  Manage Rooms
                </button>
              </li>
              <li className="nav-item">
                <button 
                  className={`nav-link ${activeTab === 'teachers' ? 'active' : ''}`}
                  onClick={() => setActiveTab('teachers')}
                  style={{ background: 'none', border: 'none', cursor: 'pointer' }}
                >
                  Manage Teachers
                </button>
              </li>
              <li className="nav-item">
                <button 
                  className={`nav-link ${activeTab === 'admins' ? 'active' : ''}`}
                  onClick={() => setActiveTab('admins')}
                  style={{ background: 'none', border: 'none', cursor: 'pointer' }}
                >
                  Manage Admins
                </button>
              </li>
            </ul>

            <span className="badge bg-danger me-2">Admin</span>
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
        {activeTab === 'validations' && <ValidationQueue />}
        {activeTab === 'rooms' && <ManageRooms />}
        {activeTab === 'teachers' && <ManageTeachers />}
        {activeTab === 'admins' && <ManageAdmins />}
      </div>
    </div>
  );
}

export default AdminDashboard;
