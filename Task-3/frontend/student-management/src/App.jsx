import { useState, useEffect } from 'react';
import axios from 'axios';
import Sidebar from './components/Sidebar';
import StudentTable from './components/StudentTable';
import StudentModal from './components/StudentModal';
import Toast from './components/Toast';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'https://flexisaf-student-backend.onrender.com';

function ErrorBoundary({ children }) {
  return children; // Simple fallback for now
}

function App() {
  const [students, setStudents] = useState([]);
  const [selectedStudent, setSelectedStudent] = useState(null);
  const [formData, setFormData] = useState({ firstName: '', lastName: '', department: '' });
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isEditMode, setIsEditMode] = useState(false);
  const [toast, setToast] = useState({ message: '', type: '' });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchStudents();
  }, []);

  const fetchStudents = async () => {
    setLoading(true);
    try {
      const response = await axios.get(`${API_BASE_URL}/students`);
      const studentList = response.data._embedded?.studentList || [];
      setStudents(studentList);
      setToast({ message: '', type: '' });
      setError(null);
    } catch (err) {
      console.error('Fetch error:', err);
      setError('Failed to load students. Check backend connection or CORS.');
      setToast({ message: 'Failed to fetch students', type: 'error' });
    }
    setLoading(false);
  };

  const fetchStudent = async (id) => {
    setLoading(true);
    try {
      const response = await axios.get(`${API_BASE_URL}/students/${id}`);
      setSelectedStudent(response.data);
      setToast({ message: '', type: '' });
    } catch (err) {
      console.error('Fetch student error:', err);
      setToast({ message: `Failed to fetch student: ${err.response?.data || err.message}`, type: 'error' });
    }
    setLoading(false);
  };

  // Handle form input changes
  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // Open modal for creating or editing
  const openModal = (student = null) => {
    if (student) {
      setFormData({ firstName: student.firstName, lastName: student.lastName, department: student.department });
      setSelectedStudent(student);
      setIsEditMode(true);
    } else {
      setFormData({ firstName: '', lastName: '', department: '' });
      setIsEditMode(false);
    }
    setIsModalOpen(true);
    setToast({ message: '', type: '' });
  };

  // Close modal
  const closeModal = () => {
    setIsModalOpen(false);
    setSelectedStudent(null);
    setFormData({ firstName: '', lastName: '', department: '' });
  };

  // Create or update student
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      if (isEditMode) {
        await axios.put(`${API_BASE_URL}/students/${selectedStudent.id}`, formData);
        setToast({ message: 'Student updated successfully!', type: 'success' });
      } else {
        await axios.post(`${API_BASE_URL}/students`, formData);
        setToast({ message: 'Student created successfully!', type: 'success' });
      }
      fetchStudents();
      closeModal();
    } catch (err) {
      console.error('Submit error:', err);
      setToast({ message: `Failed to ${isEditMode ? 'update' : 'create'} student: ${err.response?.data || err.message}`, type: 'error' });
    }
    setLoading(false);
  };

  // Delete student
  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this student?')) {
      setLoading(true);
      try {
        await axios.delete(`${API_BASE_URL}/students/${id}`);
        setToast({ message: 'Student deleted successfully!', type: 'success' });
        fetchStudents();
      } catch (err) {
        console.error('Delete error:', err);
        setToast({ message: `Failed to delete student: ${err.response?.data || err.message}`, type: 'error' });
      }
      setLoading(false);
    }
  };

  if (error) {
    return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center">
        <div className="text-red-500 p-4 text-center">
          <h1 className="text-2xl font-bold mb-2">Error Loading Application</h1>
          <p>{error}</p>
          <button
            onClick={fetchStudents}
            className="mt-4 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          >
            Retry
          </button>
        </div>
      </div>
    );
  }

  return (
    <ErrorBoundary>
      <div className="flex min-h-screen">
        <Sidebar fetchStudents={fetchStudents} setSelectedStudent={setSelectedStudent} />
        <div className="flex-1 p-8">
          <div className="max-w-5xl mx-auto">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-3xl font-bold text-gray-800">Students</h2>
              <button
                onClick={() => openModal()}
                className="flex items-center bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition duration-300"
              >
                <i className="fas fa-plus mr-2"></i> Add Student
              </button>
            </div>

            {toast.message && (
              <Toast
                message={toast.message}
                type={toast.type}
                onClose={() => setToast({ message: '', type: '' })}
              />
            )}

            <StudentTable
              students={students}
              loading={loading}
              onView={fetchStudent}
              onEdit={openModal}
              onDelete={handleDelete}
            />

            {selectedStudent && (
              <div className="mt-8 bg-white p-6 rounded-lg shadow-md">
                <h3 className="text-xl font-semibold text-gray-800 mb-4">Student Details</h3>
                <p><strong>ID:</strong> {selectedStudent.id}</p>
                <p><strong>First Name:</strong> {selectedStudent.firstName}</p>
                <p><strong>Last Name:</strong> {selectedStudent.lastName}</p>
                <p><strong>Department:</strong> {selectedStudent.department}</p>
                <button
                  onClick={() => setSelectedStudent(null)}
                  className="mt-4 bg-gray-600 text-white px-4 py-2 rounded hover:bg-gray-700 transition duration-300"
                >
                  Close
                </button>
              </div>
            )}

            <StudentModal
              isOpen={isModalOpen}
              isEditMode={isEditMode}
              formData={formData}
              onChange={handleInputChange}
              onSubmit={handleSubmit}
              onClose={closeModal}
              loading={loading}
            />
          </div>
        </div>
      </div>
    </ErrorBoundary>
  );
}

export default App;