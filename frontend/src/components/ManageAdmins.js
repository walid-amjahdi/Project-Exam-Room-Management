import React, { useState, useEffect } from 'react';

function ManageAdmins() {
  const [admins, setAdmins] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  const [adminId, setAdminId] = useState('');
  const [adminName, setAdminName] = useState('');
  const [adminEmail, setAdminEmail] = useState('');
  const [adminPassword, setAdminPassword] = useState('');
  const [adminIsSudo, setAdminIsSudo] = useState(false);

  useEffect(function() {
    fetchAdmins();
  }, []);

  function fetchAdmins() {
    async function getAdmins() {
      try {
        const response = await fetch('http://localhost:8080/admins');
        if (response.ok) {
          const data = await response.json();
          setAdmins(data);
        } else {
          setError('Failed to fetch admins');
        }
      } catch (err) {
        setError('Error connecting to server');
        console.error(err);
      } finally {
        setLoading(false);
      }
    }

    getAdmins();
  }

  function handleShowModal(admin) {
    if (admin) {
      setAdminId(admin.id);
      setAdminName(admin.name || '');
      setAdminEmail(admin.email || '');
      setAdminPassword('');
      setAdminIsSudo(admin.isSudo || false);
      setIsEditing(true);
    } else {
      setAdminId('');
      setAdminName('');
      setAdminEmail('');
      setAdminPassword('');
      setAdminIsSudo(false);
      setIsEditing(false);
    }
    setShowModal(true);
  }

  function handleCloseModal() {
    setShowModal(false);
    setAdminId('');
    setAdminName('');
    setAdminEmail('');
    setAdminPassword('');
    setAdminIsSudo(false);
  }

  function handleSave(e) {
    e.preventDefault();
    setSubmitting(true);
    setError('');
    setSuccess('');

    async function saveAdmin() {
      try {
        const method = isEditing ? 'PUT' : 'POST';
        const url = isEditing
          ? 'http://localhost:8080/admins/update/' + adminId
          : 'http://localhost:8080/admins/add';

        const adminData = {
          id: adminId || undefined,
          name: adminName,
          email: adminEmail,
          password: adminPassword || undefined,
          role: 'ADMIN',
          isSudo: adminIsSudo
        };

        const response = await fetch(url, {
          method: method,
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(adminData)
        });

        if (response.ok) {
          setSuccess(isEditing ? 'Admin updated successfully!' : 'Admin added successfully!');
          handleCloseModal();
          fetchAdmins();
          setTimeout(function() { setSuccess(''); }, 3000);
        } else {
          setError('Failed to save admin');
        }
      } catch (err) {
        setError('Error saving admin');
        console.error(err);
      } finally {
        setSubmitting(false);
      }
    }

    saveAdmin();
  }

  function handleDelete(id) {
    var confirmed = window.confirm('Are you sure you want to delete this admin?');
    if (!confirmed) return;

    async function deleteAdmin() {
      try {
        const response = await fetch('http://localhost:8080/admins/delete/' + id, {
          method: 'DELETE'
        });

        if (response.ok) {
          setSuccess('Admin deleted successfully!');
          fetchAdmins();
          setTimeout(function() { setSuccess(''); }, 3000);
        } else {
          setError('Failed to delete admin');
        }
      } catch (err) {
        setError('Error deleting admin');
        console.error(err);
      }
    }

    deleteAdmin();
  }

  if (loading) {
    return (
      <div className="mt-4">
        <div className="spinner-border text-light" role="status">
          <span className="visually-hidden">Loading...</span>
        </div>
      </div>
    );
  }

  return (
    <div className="mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Manage Admins</h2>
        <button
          className="btn btn-primary"
          onClick={function() { handleShowModal(null); }}
        >
          + Add New Admin
        </button>
      </div>

      {error && <div className="alert alert-danger">{error}</div>}
      {success && <div className="alert alert-success">{success}</div>}

      {admins.length === 0 ? (
        <div className="alert alert-info">No admins found.</div>
      ) : (
        <table className="table table-dark table-striped table-bordered table-hover">
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Super Admin</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {admins.map(function(a) {
              return (
                <tr key={a.id}>
                  <td>{a.name}</td>
                  <td>{a.email}</td>
                  <td>{a.isSudo ? 'Yes' : 'No'}</td>
                  <td>
                    <button
                      className="btn btn-warning btn-sm me-2"
                      onClick={function() { handleShowModal(a); }}
                    >
                      Edit
                    </button>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={function() { handleDelete(a.id); }}
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

      {showModal && (
        <div className="modal d-block" style={{ backgroundColor: 'rgba(0,0,0,0.6)' }}>
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">
                  {isEditing ? 'Edit Admin' : 'Add New Admin'}
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
                    <label className="form-label">Name</label>
                    <input
                      type="text"
                      className="form-control"
                      value={adminName}
                      onChange={function(e) { setAdminName(e.target.value); }}
                      required
                    />
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Email</label>
                    <input
                      type="email"
                      className="form-control"
                      value={adminEmail}
                      onChange={function(e) { setAdminEmail(e.target.value); }}
                      required
                    />
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Password</label>
                    <input
                      type="password"
                      className="form-control"
                      value={adminPassword}
                      onChange={function(e) { setAdminPassword(e.target.value); }}
                      required={!isEditing}
                      placeholder={isEditing ? 'Leave blank to keep current' : ''}
                    />
                  </div>

                  <div className="mb-3 form-check">
                    <input
                      type="checkbox"
                      className="form-check-input"
                      id="isSudo"
                      checked={adminIsSudo}
                      onChange={function(e) { setAdminIsSudo(e.target.checked); }}
                    />
                    <label className="form-check-label" htmlFor="isSudo">Super Admin</label>
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

export default ManageAdmins;
