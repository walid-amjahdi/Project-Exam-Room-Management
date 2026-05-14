import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Login.css';

function Login({ setUserRole, setUserEmail }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  function handleLogin(e) {
    e.preventDefault();
    setLoading(true);
    setError('');

    async function loginUser() {
      try {
        // Try teacher login with email + password
        let response = await fetch('http://localhost:8080/teachers/login', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ email, password })
        });
        if (response.ok) {
          const teacher = await response.json();
          localStorage.setItem('userRole', 'TEACHER');
          localStorage.setItem('userEmail', email);
          localStorage.setItem('userId', teacher.id);
          setUserRole('TEACHER');
          setUserEmail(email);
          navigate('/teacher');
          return;
        }

        // Try admin login with email + password
        response = await fetch('http://localhost:8080/admins/login', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ email, password })
        });
        if (response.ok) {
          const admin = await response.json();
          localStorage.setItem('userRole', 'ADMIN');
          localStorage.setItem('userEmail', email);
          localStorage.setItem('userId', admin.id);
          setUserRole('ADMIN');
          setUserEmail(email);
          navigate('/admin');
          return;
        }

        setError('Invalid email or password. Please try again.');
      } catch (err) {
        setError('Error connecting to server. Please try again.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    }

    loginUser();
  }

  return (
    <div className="login-container d-flex justify-content-center align-items-center">
      <div className="login-box">
        <h1 className="text-center mb-4">University Room Reservation System</h1>
        <form onSubmit={handleLogin}>
          {error && <div className="alert alert-danger">{error}</div>}
          
          <div className="mb-3">
            <label className="form-label">Email Address</label>
            <input
              type="email"
              className="form-control"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Password</label>
            <input
              type="password"
              className="form-control"
              placeholder="Enter your password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <button 
            type="submit" 
            className="btn btn-primary w-100"
            disabled={loading}
          >
            {loading ? 'Logging in...' : 'Login'}
          </button>
        </form>
      </div>
    </div>
  );
}

export default Login;
