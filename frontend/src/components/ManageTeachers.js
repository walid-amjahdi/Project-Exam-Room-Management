import React, { useState, useEffect } from 'react';

function ManageTeachers() {
  const [teachers, setTeachers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  const [teacherId, setTeacherId] = useState('');
  const [teacherName, setTeacherName] = useState('');
  const [teacherEmail, setTeacherEmail] = useState('');
  const [teacherPassword, setTeacherPassword] = useState('');
  const [teacherDepartment, setTeacherDepartment] = useState('');
  const [teacherGrade, setTeacherGrade] = useState('');
  const [teacherPhone, setTeacherPhone] = useState('');

  useEffect(function() {
    fetchTeachers();
  }, []);

  function fetchTeachers() {
    async function getTeachers() {
      try {
        const response = await fetch('http://localhost:8080/teachers');
        if (response.ok) {
          const data = await response.json();
          setTeachers(data);
        } else {
          setError('Failed to fetch teachers');
        }
      } catch (err) {
        setError('Error connecting to server');
        console.error(err);
      } finally {
        setLoading(false);
      }
    }

    getTeachers();
  }

  function handleShowModal(teacher) {
    if (teacher) {
      setTeacherId(teacher.id);
      setTeacherName(teacher.name || '');
      setTeacherEmail(teacher.email || '');
      setTeacherPassword('');
      setTeacherDepartment(teacher.department || '');
      setTeacherGrade(teacher.grade || '');
      setTeacherPhone(teacher.phoneNumber || '');
      setIsEditing(true);
    } else {
      setTeacherId('');
      setTeacherName('');
      setTeacherEmail('');
      setTeacherPassword('');
      setTeacherDepartment('');
      setTeacherGrade('');
      setTeacherPhone('');
      setIsEditing(false);
    }
    setShowModal(true);
  }

  function handleCloseModal() {
    setShowModal(false);
    setTeacherId('');
    setTeacherName('');
    setTeacherEmail('');
    setTeacherPassword('');
    setTeacherDepartment('');
    setTeacherGrade('');
    setTeacherPhone('');
  }

  function handleSave(e) {
    e.preventDefault();
    setSubmitting(true);
    setError('');
    setSuccess('');

    async function saveTeacher() {
      try {
        const method = isEditing ? 'PUT' : 'POST';
        const url = isEditing
          ? 'http://localhost:8080/teachers/update/' + teacherId
          : 'http://localhost:8080/teachers/add';

        const teacherData = {
          id: teacherId || undefined,
          name: teacherName,
          email: teacherEmail,
          password: teacherPassword || undefined,
          role: 'TEACHER',
          department: teacherDepartment,
          grade: teacherGrade,
          phoneNumber: teacherPhone
        };

        const response = await fetch(url, {
          method: method,
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(teacherData)
        });

        if (response.ok) {
          setSuccess(isEditing ? 'Teacher updated successfully!' : 'Teacher added successfully!');
          handleCloseModal();
          fetchTeachers();
          setTimeout(function() { setSuccess(''); }, 3000);
        } else {
          setError('Failed to save teacher');
        }
      } catch (err) {
        setError('Error saving teacher');
        console.error(err);
      } finally {
        setSubmitting(false);
      }
    }

    saveTeacher();
  }

  function handleDelete(id) {
    var confirmed = window.confirm('Are you sure you want to delete this teacher?');
    if (!confirmed) return;

    async function deleteTeacher() {
      try {
        const response = await fetch('http://localhost:8080/teachers/delete/' + id, {
          method: 'DELETE'
        });

        if (response.ok) {
          setSuccess('Teacher deleted successfully!');
          fetchTeachers();
          setTimeout(function() { setSuccess(''); }, 3000);
        } else {
          setError('Failed to delete teacher');
        }
      } catch (err) {
        setError('Error deleting teacher');
        console.error(err);
      }
    }

    deleteTeacher();
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
        <h2>Manage Teachers</h2>
        <button
          className="btn btn-primary"
          onClick={function() { handleShowModal(null); }}
        >
          + Add New Teacher
        </button>
      </div>

      {error && <div className="alert alert-danger">{error}</div>}
      {success && <div className="alert alert-success">{success}</div>}

      {teachers.length === 0 ? (
        <div className="alert alert-info">No teachers found.</div>
      ) : (
        <table className="table table-dark table-striped table-bordered table-hover">
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Department</th>
              <th>Grade</th>
              <th>Phone</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {teachers.map(function(t) {
              return (
                <tr key={t.id}>
                  <td>{t.name}</td>
                  <td>{t.email}</td>
                  <td>{t.department || 'N/A'}</td>
                  <td>{t.grade || 'N/A'}</td>
                  <td>{t.phoneNumber || 'N/A'}</td>
                  <td>
                    <button
                      className="btn btn-warning btn-sm me-2"
                      onClick={function() { handleShowModal(t); }}
                    >
                      Edit
                    </button>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={function() { handleDelete(t.id); }}
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
                  {isEditing ? 'Edit Teacher' : 'Add New Teacher'}
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
                      value={teacherName}
                      onChange={function(e) { setTeacherName(e.target.value); }}
                      required
                    />
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Email</label>
                    <input
                      type="email"
                      className="form-control"
                      value={teacherEmail}
                      onChange={function(e) { setTeacherEmail(e.target.value); }}
                      required
                    />
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Password</label>
                    <input
                      type="password"
                      className="form-control"
                      value={teacherPassword}
                      onChange={function(e) { setTeacherPassword(e.target.value); }}
                      required={!isEditing}
                      placeholder={isEditing ? 'Leave blank to keep current' : ''}
                    />
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Department</label>
                    <input
                      type="text"
                      className="form-control"
                      value={teacherDepartment}
                      onChange={function(e) { setTeacherDepartment(e.target.value); }}
                    />
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Grade</label>
                    <input
                      type="text"
                      className="form-control"
                      value={teacherGrade}
                      onChange={function(e) { setTeacherGrade(e.target.value); }}
                    />
                  </div>

                  <div className="mb-3">
                    <label className="form-label">Phone Number</label>
                    <input
                      type="text"
                      className="form-control"
                      value={teacherPhone}
                      onChange={function(e) { setTeacherPhone(e.target.value); }}
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

export default ManageTeachers;
