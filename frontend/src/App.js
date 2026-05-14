import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

import Login from './pages/Login';
import TeacherDashboard from './pages/TeacherDashboard';
import AdminDashboard from './pages/AdminDashboard';

function App() {
  const [userRole, setUserRole] = useState(localStorage.getItem('userRole'));
  const [userEmail, setUserEmail] = useState(localStorage.getItem('userEmail'));

  function handleLogout() {
    localStorage.removeItem('userRole');
    localStorage.removeItem('userEmail');
    localStorage.removeItem('userId');
    setUserRole(null);
    setUserEmail(null);
  }

  return (
    <Router>
      <Routes>
        <Route 
          path="/login" 
          element={<Login setUserRole={setUserRole} setUserEmail={setUserEmail} />} 
        />
        
        <Route 
          path="/teacher/*" 
          element={
            userRole === 'TEACHER' ? (
              <TeacherDashboard userEmail={userEmail} handleLogout={handleLogout} />
            ) : (
              <Navigate to="/login" />
            )
          } 
        />
        
        <Route 
          path="/admin/*" 
          element={
            userRole === 'ADMIN' ? (
              <AdminDashboard userEmail={userEmail} handleLogout={handleLogout} />
            ) : (
              <Navigate to="/login" />
            )
          } 
        />
        
        <Route 
          path="/" 
          element={
            userRole ? (
              userRole === 'TEACHER' ? <Navigate to="/teacher" /> : <Navigate to="/admin" />
            ) : (
              <Navigate to="/login" />
            )
          } 
        />
      </Routes>
    </Router>
  );
}

export default App;
