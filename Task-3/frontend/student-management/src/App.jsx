import { useState, useEffect } from 'react';
import axios from 'axios';
import Sidebar from './components/Sidebar';
import StudentTable from './components/StudentTable';
import StudentModal from './components/StudentModal';
import Toast from './components/Toast';

function ErrorBoundary({ children }) {
  return children;  // Simple fallback for now
}

function App() {
  const [students, setStudents] = useState([]);
  const [selectedStudent, setSelectedStudent] = useState(null);
  const [formData, setFormData] = useState({ name: '', department: '' });
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isEditMode, setIsEditMode] = useState(false);
  const [toast, setToast] = useState({ message: '', type: '' });
  const [loading, setLoading] = useState(false);

  // Fetch all students
  useEffect(() => {
    fetchStudents();
  }, []);

  const fetchStudents = async () => {
    setLoading(true);
    try {
      const response = await axios.get('http://localhost:8080/students');
      const studentList = response.data._embedded?.studentList || [];
      setStudents(studentList);
      setToast({ message: '', type: '' });
    } catch (err) {
      console.error('Fetch error:', err);  // Log for debugging
      setToast({ message: 'Failed to fetch students', type: 'error' });
    }
    setLoading(false);
  };

  // Fetch single student
  const fetchStudent = async (id) => {
    setLoading(true);
    try {
      const response = await axios.get(`http://localhost:8080/students/${id}`);
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
      setFormData({ name: student.name, department: student.department });
      setSelectedStudent(student);
      setIsEditMode(true);
    } else {
      setFormData({ name: '', department: '' });
      setIsEditMode(false);
    }
    setIsModalOpen(true);
    setToast({ message: '', type: '' });
  };

  // Close modal
  const closeModal = () => {
    setIsModalOpen(false);
    setSelectedStudent(null);
    setFormData({ name: '', department: '' });
  };

  // Create or update student
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      if (isEditMode) {
        await axios.put(`http://localhost:8080/students/${selectedStudent.id}`, formData);
        setToast({ message: 'Student updated successfully!', type: 'success' });
      } else {
        await axios.post('http://localhost:8080/students', formData);
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
        await axios.delete(`http://localhost:8080/students/${id}`);
        setToast({ message: 'Student deleted successfully!', type: 'success' });
        fetchStudents();
      } catch (err) {
        console.error('Delete error:', err);
        setToast({ message: `Failed to delete student: ${err.response?.data || err.message}`, type: 'error' });
      }
      setLoading(false);
    }
  };

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
                <p><strong>Name:</strong> {selectedStudent.name}</p>
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